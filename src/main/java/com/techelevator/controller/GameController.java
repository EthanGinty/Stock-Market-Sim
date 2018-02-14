package com.techelevator.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.techelevator.database.TransactionDAO;
import com.techelevator.database.UserDAO;
import com.techelevator.pojo.Game;
import com.techelevator.pojo.TotalBalance;
import com.techelevator.pojo.Transaction;
import com.techelevator.pojo.User;

@Controller
@SessionAttributes("selectedGame")
public class GameController {

	private GameDAO gameDAO;
	private UserDAO userDAO;
	private TransactionDAO transactionDAO;
	private StocksAPI stocksAPI;

	@Autowired
	public GameController(GameDAO gameDAO, UserDAO userDAO, TransactionDAO transactionDAO, StocksAPI stocksAPI) {
		this.gameDAO = gameDAO;
		this.userDAO = userDAO;
		this.transactionDAO = transactionDAO;
		this.stocksAPI = stocksAPI;
	}

	@RequestMapping(path = "/createGame", method = RequestMethod.GET)
	public String getCreateGamePage(ModelMap model, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			model.put("users", userDAO.getAllUsers());
			return "createGame";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/createGame", method = RequestMethod.POST)
	public String postCreateGamePage(HttpSession session, HttpServletRequest request, ModelMap model) {
		User currentUser = new User();
		currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			String[] userIds = request.getParameterValues("userIds");

			/*
			 * Only use if we need to add in a list of Users instead of only
			 * their user names List<User> users = new ArrayList<>(); for(String
			 * userName : userNames) { User createUser = new User(); createUser
			 * = userDAO.getUserByUsername(userName); users.add(createUser); }
			 */
			String nameOfGame = request.getParameter("nameOfGame");
			LocalDate startDate = LocalDate.now();
			int duration = Integer.parseInt(request.getParameter("duration"));
			LocalDate endDate = startDate.plusDays(duration);
			boolean isRealGame = isRealGame(request);
			gameDAO.createGame(nameOfGame, startDate, endDate, currentUser.getUserName(), isRealGame, userIds);
			Map<Long, BigDecimal> userAvailableBalances = userDAO.getAvailableBalancesByUserId(currentUser.getUserId());
			currentUser.setAvailableBalanceForGames(userAvailableBalances);
			model.put("currentUser", currentUser);
			return "redirect:/users/dashboard";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/gameLeaderboard", method = RequestMethod.GET)
	public String getGameLeaderboard(@RequestParam(required = false) Long gameId, ModelMap model, HttpSession session)
			throws IOException {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			Game selectedGame = gameDAO.getGameByGameId(gameId);
			model.put("selectedGame", selectedGame);

			List<User> usersInGame = gameDAO.getAllPlayersbyGameId(gameId);

			for (User user : usersInGame) {
				List<Transaction> userTransactionsForStocksAPI = transactionDAO
						.getStocksOwnedbySingleUser(user.getUserId(), gameId);
				List<Transaction> userTransactionsForTotalBalance = new ArrayList<>();
				if (!userTransactionsForStocksAPI.isEmpty()) {
					userTransactionsForTotalBalance = stocksAPI.getAskPricePerStock(userTransactionsForStocksAPI,
							selectedGame.isRealGame());

				}
				BigDecimal totalBalance = userDAO.getAvailableBalancesByUserId(user.getUserId())
						.get(selectedGame.getGameId());
				for (Transaction transaction : userTransactionsForTotalBalance) {
					BigDecimal numberOfShares = new BigDecimal(transaction.getNumberOfShares());
					BigDecimal stockPrice = transaction.getStock().getAskPrice();
					totalBalance = totalBalance.add(numberOfShares.multiply(stockPrice));
				}
				userDAO.setTotalBalance(user.getUserId(), selectedGame.getGameId(), totalBalance);
			}

			List<TotalBalance> totalBalances = userDAO.getTotalBalancesForGame(selectedGame.getGameId());
			model.put("totalBalances", totalBalances);

			if (selectedGame.getNameOfGame() != null) {
				return "gameLeaderboard";
			} else {
				return "redirect:/users/dashboard";
			}
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/completedGameLeaderboard", method = RequestMethod.GET)
	public String getCompletedGameLeaderboard(@RequestParam(required = false) Long gameId, ModelMap model, HttpSession session) throws IOException {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			Game selectedGame = gameDAO.getGameByGameId(gameId);
			model.put("selectedGame", selectedGame);

			List<User> usersInGame = gameDAO.getAllPlayersbyGameId(gameId);

			for (User user : usersInGame) {
				List<Transaction> userTransactionsForStocksAPI = transactionDAO
						.getStocksOwnedbySingleUser(user.getUserId(), gameId);
				List<Transaction> userTransactionsForTotalBalance = new ArrayList<>();
				if (!userTransactionsForStocksAPI.isEmpty()) {
					userTransactionsForTotalBalance = stocksAPI.getAskPricePerStock(userTransactionsForStocksAPI,
							selectedGame.isRealGame());

				}
				BigDecimal totalBalance = userDAO.getAvailableBalancesByUserId(user.getUserId())
						.get(selectedGame.getGameId());
				for (Transaction transaction : userTransactionsForTotalBalance) {
					BigDecimal numberOfShares = new BigDecimal(transaction.getNumberOfShares());
					BigDecimal stockPrice = transaction.getStock().getAskPrice();
					totalBalance = totalBalance.add(numberOfShares.multiply(stockPrice));
				}
				userDAO.setTotalBalance(user.getUserId(), selectedGame.getGameId(), totalBalance);
			}

			List<TotalBalance> totalBalances = userDAO.getTotalBalancesForGame(selectedGame.getGameId());
			model.put("totalBalances", totalBalances);

			if (selectedGame.getNameOfGame() != null) {
				return "completedGameLeaderboard";
			} else {
				return "redirect:/users/dashboard";
			}
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/addPlayer", method = RequestMethod.GET)
	public String getAddPlayer(@RequestParam(required = false) Long gameId, ModelMap model, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			List<User> allUsersInSystem = userDAO.getAllUsers();
			List<Long> userIdsForAllUsers = new ArrayList<>();
			for (User user : allUsersInSystem) {
				Long userId = user.getUserId();
				userIdsForAllUsers.add(userId);
			}

			List<User> allPlayersInGame = gameDAO.getAllPlayersbyGameId(gameId);
			List<Long> userIdsForPlayersInGame = new ArrayList<>();
			for (User user : allPlayersInGame) {
				Long userId = user.getUserId();
				userIdsForPlayersInGame.add(userId);
			}

			List<Long> userIdsNotInGame = new ArrayList<>();
			for (Long userId : userIdsForAllUsers) {
				if (userIdsForPlayersInGame.contains(userId)) {
				} else {
					userIdsNotInGame.add(userId);
				}
			}

			List<User> usersNotInGame = new ArrayList<>();
			for (Long userId : userIdsNotInGame) {
				User user = userDAO.getUserByUserId(userId);
				usersNotInGame.add(user);
			}
			model.put("usersNotInGame", usersNotInGame);

			Game game = gameDAO.getGameByGameId(gameId);
			model.put("selectedGame", game);

			return "addPlayer";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path = "/addPlayer", method = RequestMethod.POST)
	public String postAddPlayer(HttpSession session, HttpServletRequest request, RedirectAttributes redirect) {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			String[] userIds = request.getParameterValues("userIds");
			Game game = (Game) session.getAttribute("selectedGame");
			gameDAO.addPlayerToGame(userIds, game.getGameId());
			redirect.addAttribute("gameId", game.getGameId());
			return "redirect:/gameLeaderboard";
		} else {
			return "redirect:/login";
		}
	}

	private boolean isRealGame(HttpServletRequest request) {
		boolean isRealGame = true;
		if (request.getParameter("isFakeGame") != null) {
			boolean isFakeGame = false;
			if (request.getParameter("isFakeGame").toLowerCase().equals("on")) {
				isFakeGame = true;
			}
			if (isFakeGame) {
				isRealGame = false;
			}
		}
		return isRealGame;
	}

}
