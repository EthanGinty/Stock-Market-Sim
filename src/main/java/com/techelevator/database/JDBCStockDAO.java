package com.techelevator.database;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.Stock;

@Component
public class JDBCStockDAO implements StockDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCStockDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Stock> get50Stocks() {
		List<Stock> stocks = new ArrayList<>();
		String sqlSelect50Stocks = "SELECT * FROM stock";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect50Stocks);
		while(results.next()) {
			Stock stock = mapRowToStock(results);
			stocks.add(stock);
		}
		return stocks;
	}
	
	@Override
	public Stock getSpecificStock(String symbol) {
		Stock stock = new Stock();
		String sqlSelectStock = "SELECT * FROM stock WHERE symbol = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectStock, symbol.toUpperCase());
		if(results.next()) {
			stock = mapRowToStock(results);
		}
		return stock;
	}
	
	private Stock mapRowToStock(SqlRowSet results) {
		Stock stock = new Stock();
		stock.setStockId(results.getLong("stock_id"));
		stock.setSymbol(results.getString("symbol"));
		return stock;
	}

}
