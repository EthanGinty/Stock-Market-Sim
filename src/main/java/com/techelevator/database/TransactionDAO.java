package com.techelevator.database;

import java.math.BigDecimal;
import java.util.List;


import com.techelevator.pojo.Stock;
import com.techelevator.pojo.Transaction;

public interface TransactionDAO {

	public BigDecimal getAvailableBalanceByGameAndUser(long gameId, long userId);
	
	public Stock getAllNumberOfOwnedStocksByGameAndUser(long gameID, long userId);
	
	public List<Transaction> getStocksOwnedbyUsers(List<Long> userIds, long gameId);
	
	public List<Transaction> getTransactionsbySingleUser(long userId, long gameId, long stockId);
	
	public void transaction(long userId, long gameId, long stockId, BigDecimal price, String buySell, long numberOfShares, BigDecimal availableBalance);
	
	public List<Transaction> getStocksOwnedbySingleUser(long userIds, long gameId);
	
	public long getTotalNumberOfSharesByStock(long userId, long gameId, long stockId);
	
	
}
