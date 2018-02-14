package com.techelevator.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.api.StocksAPI;
import com.techelevator.database.GameDAO;
import com.techelevator.database.StockDAO;
import com.techelevator.database.TransactionDAO;
import com.techelevator.database.UserDAO;
import com.techelevator.pojo.Game;
import com.techelevator.pojo.Stock;
import com.techelevator.pojo.Transaction;
import com.techelevator.pojo.User;

@Controller
@SessionAttributes({ "selectedGame", "detailedStock" })
public class StockController {

	private StocksAPI stocksAPI;
	private TransactionDAO transactionDAO;
	private GameDAO gameDAO;
	private StockDAO stockDAO;
	private UserDAO userDAO;

	@Autowired
	public StockController(StocksAPI stocksAPI, TransactionDAO transactionDAO, GameDAO gameDAO, StockDAO stockDAO, UserDAO userDAO) {
		this.stocksAPI = stocksAPI;
		this.transactionDAO = transactionDAO;
		this.gameDAO = gameDAO;
		this.stockDAO = stockDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(path = "/userGameDashboard", method = RequestMethod.GET)
	public String getGameDashboard(ModelMap model, HttpSession session, @RequestParam(required = false) Long gameId)
			throws IOException {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			Game game = gameDAO.getGameByGameId(gameId);
			model.put("selectedGame", game);
			BigDecimal availableBalance = currentUser.getAvailableBalanceForGames().get(game.getGameId());
			model.put("availableBalance", availableBalance);
			List<Stock> all50Stocks = stockDAO.get50Stocks();
			boolean isRealGame = gameDAO.isRealGame(game.getGameId());
			all50Stocks = stocksAPI.getSpecificStocks(all50Stocks, isRealGame);
			model.addAttribute("allStocks", all50Stocks);
			List<Transaction> allTransactions = transactionDAO.getStocksOwnedbySingleUser(currentUser.getUserId(),
					game.getGameId());
			List<Transaction> newAllTransactionList = new ArrayList<>();
			if(!allTransactions.isEmpty()) {
				 newAllTransactionList = stocksAPI.getBidPricePerStock(allTransactions, isRealGame);				
			}

			model.addAttribute("allTransactions", newAllTransactionList);
			
			setTotalBalance(gameId, currentUser, game);
			
			BigDecimal showTotalBalance = userDAO.getTotalBalanceByUser(currentUser.getUserId(), game.getGameId());
			model.addAttribute("totalBalance", showTotalBalance);
			
			return "userGameDashboard";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/transaction", method = RequestMethod.GET)
	public String getTransactionPage(ModelMap model, HttpSession session, @RequestParam(required = false) String symbol,
			@RequestParam(required = false) String transaction) throws IOException {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			session.removeAttribute("detailedStock");
			Stock finalStock = stockDAO.getSpecificStock(symbol);

			Stock detailedStock = stocksAPI.getStockDetails(symbol);

			Game game = (Game) session.getAttribute("selectedGame");
			boolean isRealGame = gameDAO.isRealGame(game.getGameId());
			Stock priceStock = stocksAPI.getSingleStock(symbol, isRealGame);

			finalStock = mapFinalStock(finalStock, detailedStock, priceStock);
			BigDecimal availableBalance = currentUser.getAvailableBalanceForGames().get(game.getGameId());
			
			setTotalBalance(game.getGameId(), currentUser, game);
			
			BigDecimal totalBalance = userDAO.getTotalBalanceByUser(currentUser.getUserId(), game.getGameId());
			
			model.addAttribute("totalBalance", totalBalance);
			model.addAttribute("availableBalance", availableBalance);
			model.addAttribute("detailedStock", finalStock);
			model.addAttribute("transactionType", transaction);

			return "transaction";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/transaction", method = RequestMethod.POST)
	public String postTransactionPage(HttpSession session, RedirectAttributes redirect, ModelMap model,
			@RequestParam(required = false) String numberOfShares, @RequestParam(required = false) String buySell) {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			long amountOfShares = Long.parseLong(numberOfShares);
			Stock detailedStock = (Stock) session.getAttribute("detailedStock");
			Game game = (Game) session.getAttribute("selectedGame");
			BigDecimal availableBalance = currentUser.getAvailableBalanceForGames().get(game.getGameId());
			if(buySell.equals("sell")) {
				long totalNumberOfSharesOwned = transactionDAO.getTotalNumberOfSharesByStock(currentUser.getUserId(), game.getGameId(), detailedStock.getStockId());
				if(amountOfShares <= totalNumberOfSharesOwned) {
					transactionDAO.transaction(currentUser.getUserId(), game.getGameId(), detailedStock.getStockId(),
							detailedStock.getAskPrice(), buySell, amountOfShares, availableBalance);
					Map<Long, BigDecimal> userAvailableBalances = userDAO.getAvailableBalancesByUserId(currentUser.getUserId());
					currentUser.setAvailableBalanceForGames(userAvailableBalances);
				} else {
					//do nothing
				}
			} else if(buySell.equals("buy")) {
				BigDecimal costOfTransaction = detailedStock.getBidPrice().multiply(new BigDecimal(amountOfShares));
				if(costOfTransaction.compareTo(availableBalance) <= 0) {
					transactionDAO.transaction(currentUser.getUserId(), game.getGameId(), detailedStock.getStockId(),
							detailedStock.getAskPrice(), buySell, amountOfShares, availableBalance);
					Map<Long, BigDecimal> userAvailableBalances = userDAO.getAvailableBalancesByUserId(currentUser.getUserId());
					currentUser.setAvailableBalanceForGames(userAvailableBalances);
				} else {
					//do nothing
				}
			}
			
			model.addAttribute("currentUser", currentUser);
			
			redirect.addAttribute("gameId", game.getGameId());
			return "redirect:/userGameDashboard";
		} else {
			return "redirect:/login";
		}
	}

	private void setTotalBalance(Long gameId, User currentUser, Game game) throws IOException {
		List<Transaction> userTransactionsForStocksAPI = transactionDAO
				.getStocksOwnedbySingleUser(currentUser.getUserId(), gameId);
		List<Transaction> userTransactionsForTotalBalance = new ArrayList<>();
		if (!userTransactionsForStocksAPI.isEmpty()) {
			userTransactionsForTotalBalance = stocksAPI.getAskPricePerStock(userTransactionsForStocksAPI,
					game.isRealGame());

		}
		BigDecimal totalBalance = userDAO.getAvailableBalancesByUserId(currentUser.getUserId())
				.get(game.getGameId());
		for (Transaction transaction : userTransactionsForTotalBalance) {
			BigDecimal numberOfShares = new BigDecimal(transaction.getNumberOfShares());
			BigDecimal stockPrice = transaction.getStock().getAskPrice();
			totalBalance = totalBalance.add(numberOfShares.multiply(stockPrice));
		}
		userDAO.setTotalBalance(currentUser.getUserId(), game.getGameId(), totalBalance);
	}

	private Stock mapFinalStock(Stock finalStock, Stock detailedStock, Stock priceStock) {
		finalStock.setCompanyName(detailedStock.getCompanyName());
		finalStock.setExchange(detailedStock.getExchange());
		finalStock.setIndustry(detailedStock.getIndustry());
		finalStock.setDescription(detailedStock.getDescription());
		finalStock.setCEO(detailedStock.getCEO());
		finalStock.setAskPrice(priceStock.getAskPrice());
		finalStock.setBidPrice(priceStock.getBidPrice());
		return finalStock;
	}

}
