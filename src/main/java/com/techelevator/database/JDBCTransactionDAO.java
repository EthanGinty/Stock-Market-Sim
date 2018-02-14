package com.techelevator.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.Stock;
import com.techelevator.pojo.Transaction;

@Component
public class JDBCTransactionDAO implements TransactionDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCTransactionDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public BigDecimal getAvailableBalanceByGameAndUser(long gameId, long userId) {
		BigDecimal availableBalance = new BigDecimal(100000);
		String sqlGetTotalBalanceByGameAndUser = "SELECT * FROM transaction WHERE game_id = ? AND user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTotalBalanceByGameAndUser, gameId, userId);
		while (results.next()) {
			Transaction transaction = mapRowToTransaction(results);
			if (transaction.getBuySell().equals("buy")) {
				BigDecimal numberOfShares = new BigDecimal(transaction.getNumberOfShares());
				availableBalance = availableBalance.subtract((transaction.getPrice().multiply(numberOfShares)));
			} else if (transaction.getBuySell().equals("sell")) {
				BigDecimal numberOfShares = new BigDecimal(transaction.getNumberOfShares());
				availableBalance = availableBalance.add(((transaction.getPrice().multiply(numberOfShares))));
			}

		}
		return availableBalance;
	}

	@Override
	public Stock getAllNumberOfOwnedStocksByGameAndUser(long gameID, long userId) {
		return null;
	}

	@Override
	public List<Transaction> getStocksOwnedbyUsers(List<Long> userIds, long gameId) {
		List<Transaction> stocksOwned = new ArrayList<>();
		String sqlGetStocksOwnedByUser = "SELECT transaction.stock_id, stock.symbol, transaction.number_of_shares, transaction.user_id, transaction.game_id "
				+ "FROM transaction INNER JOIN stock on stock.stock_id = transaction.stock_id "
				+ "WHERE game_id = ?  AND user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetStocksOwnedByUser, gameId, userIds);
		while(results.next()){
			Transaction transaction = mapRowToTransaction(results);
			Stock stock = mapRowToStock(results);
			transaction.setStock(stock);
			stocksOwned.add(transaction);
			
		}
		return stocksOwned;
	}
	

	@Override
	public void transaction(long userId, long gameId, long stockId, 
								   BigDecimal price, String buySell,
								   long numberOfShares, BigDecimal availableBalance) {
		if(buySell.equals("sell")){
			BigDecimal bigShares = new BigDecimal(numberOfShares);
			BigDecimal transactionAmount = price.multiply(bigShares);
			BigDecimal newAmount = availableBalance.add(transactionAmount);
			jdbcTemplate.update("UPDATE game_user SET available_balance = ? WHERE game_id = ? and user_id = ?", newAmount, gameId, userId );
			numberOfShares = numberOfShares * -1;
		} else if(buySell.equals("buy")){
			BigDecimal bigShares = new BigDecimal(numberOfShares);
			BigDecimal transactionAmount = price.multiply(bigShares);
			BigDecimal newAmount = availableBalance.subtract(transactionAmount);
			jdbcTemplate.update("UPDATE game_user SET available_balance = ? WHERE game_id = ? and user_id = ?", newAmount, gameId, userId );
		}
		jdbcTemplate.update("INSERT INTO transaction (user_id, game_id, "
						  + "stock_id, price, buy_sell, number_of_shares) "
						  + "VALUES (?,?,?,?,?,?)", userId, gameId, stockId,
						  price, buySell, numberOfShares);
	}

	@Override
	public List<Transaction> getTransactionsbySingleUser(long userIds, long gameId, long stockId) {
		
		List<Transaction> stocksOwned = new ArrayList<>();
		String sqlGetStocksOwnedByUser = "SELECT transaction.stock_id, stock.symbol, transaction.number_of_shares, "
									   + "transaction.price, transaction.buy_sell, transaction.user_id, transaction.game_id FROM transaction INNER JOIN stock on "
									   + "stock.stock_id = transaction.stock_id WHERE game_id = ?  AND user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetStocksOwnedByUser, gameId, userIds);
		while(results.next()){
			Transaction transaction = mapRowToTransaction(results);
			Stock stock = mapRowToStock(results);
			transaction.setStock(stock);
			stocksOwned.add(transaction);
			
		}
		return stocksOwned;
	}

	@Override
	public List<Transaction> getStocksOwnedbySingleUser(long userIds, long gameId) {

		List<Transaction> stocksOwned = new ArrayList<>();
		String sqlGetStocksOwnedByUser = "SELECT transaction.user_id, transaction.game_id, stock.stock_id, stock.symbol, SUM(transaction.number_of_shares) as total_number_of_shares "
									   + "FROM transaction INNER JOIN stock on stock.stock_id = transaction.stock_id  "
									   + "WHERE game_id = ?  AND user_id = ? "
									   + "Group By stock.symbol, stock.stock_id, transaction.game_id, transaction.user_id";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetStocksOwnedByUser, gameId, userIds);
		while(results.next()){
			long totalNumberOfShares = results.getLong("total_number_of_shares");
			if(totalNumberOfShares > 0){
			Transaction transaction = mapGetStocksOwnedBySingleUser(results);
			Stock stock = mapRowToStock(results);
			transaction.setStock(stock);
			stocksOwned.add(transaction);
			} else{
				//skip
			}
			
		}
		return stocksOwned;
	}

	@Override
	public long getTotalNumberOfSharesByStock(long userId, long gameId, long stockId) {
		String sqlGetTotalNumberOfSharesByStock = "SELECT sum(number_of_shares) as total_number_of_shares from transaction "
				+ "WHERE game_id = ? AND user_id = ? AND stock_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTotalNumberOfSharesByStock, gameId, userId, stockId);
		results.next();
		long totalNumberOfShares = results.getInt("total_number_of_shares");
		return totalNumberOfShares;
	}
	private Transaction mapGetStocksOwnedBySingleUser(SqlRowSet results) {
		Transaction t = new Transaction();
		t.setStockId(results.getLong("stock_id"));
		t.setGameId(results.getLong("game_id"));
		t.setUserId(results.getLong("user_id"));
		t.setNumberOfShares(results.getLong("total_number_of_shares"));
		return t;

	}

	private Transaction mapRowToTransaction(SqlRowSet results) {
		Transaction t = new Transaction();
		t.setStockId(results.getLong("stock_id"));
		t.setGameId(results.getLong("game_id"));
		t.setUserId(results.getLong("user_id"));
		t.setPrice(results.getBigDecimal("price"));
		t.setNumberOfShares(results.getLong("number_of_shares"));
		return t;
		
	}
	
	private Stock mapRowToStock(SqlRowSet results){
		Stock s = new Stock();
		s.setSymbol(results.getString("symbol"));
		s.setStockId(results.getLong("stock_id"));
		return s;
	}

}
