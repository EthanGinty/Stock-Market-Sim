package com.techelevator.api;

import java.io.IOException;
import java.util.List;

import com.techelevator.pojo.Stock;
import com.techelevator.pojo.Transaction;

public interface StocksAPIInterface {
	
//	public StockDetail getStock(String symbol) throws IOException;
	
	public List<Stock> getSpecificStocks(List<Stock> stockList, boolean isRealGame) throws IOException;
	
	public Stock getSingleStock(String symbol, boolean isRealGame) throws IOException;
	
	public Stock getStockDetails(String symbol) throws IOException;
	
	public List<Transaction> getAskPricePerStock(List<Transaction> transactionList, boolean isRealGame) throws IOException;
	
	public List<Transaction> getBidPricePerStock(List<Transaction> transactionList, boolean isRealStock) throws IOException;
}
