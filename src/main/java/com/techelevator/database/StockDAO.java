package com.techelevator.database;

import java.util.List;

import com.techelevator.pojo.Stock;

public interface StockDAO {
	
	public List<Stock> get50Stocks();
	
	public Stock getSpecificStock(String symbol);

}
