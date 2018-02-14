package com.techelevator.api;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.Stock;
import com.techelevator.pojo.Transaction;

@Component
public class StocksAPI implements StocksAPIInterface {

	// https://api.iextrading.com/1.0/stock/AAPL,INTC/quote
	/*
	 * @Autowired public StocksAPI() {
	 * 
	 * }
	 * 
	 * @Override public StockDetail getStock(String symbol) throws IOException {
	 * List<String> symbols = new ArrayList<String>(); symbols.add(symbol);
	 * List<StockDetail> stocks = getSpecificStocks(symbols); return
	 * stocks.get(0); }
	 * 
	 */
	@Override
	public Stock getStockDetails(String symbol) throws IOException {

		String url = "https://api.iextrading.com/1.0/stock/" + symbol + "/company";

		String charset = "UTF-8"; // Or in Java 7 and later, use the
									// constant:
									// java.nio.charset.StandardCharsets.UTF_8.name()

		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		String text = new String();
		Boolean isLineEnd = false;
		Boolean isLineStart = false;
		ArrayList<String> myList = new ArrayList<String>();
		int value = 0;
		while (value != -1) {
			value = response.read();
			if ((char) value == '{') {
				isLineStart = true;
				isLineEnd = false;
			} else if ((char) value == '}') {
				isLineEnd = true;
				isLineStart = false;
			}

			if (isLineStart) {
				text += (char) value;
			} else if (isLineEnd) {
				text += (char) value;
				myList.add(text);
				text = "";
				isLineEnd = false;
			}
		}
		Stock stock = new Stock();
		for (int i = 0; i < myList.size(); i++) {
			JSONObject obj = new JSONObject(myList.get(i));
			stock.setCompanyName(obj.getString("companyName"));
			stock.setExchange(obj.getString("exchange"));
			stock.setIndustry(obj.getString("industry"));
			stock.setDescription(obj.getString("description"));
			stock.setCEO(obj.getString("CEO"));
		}

		return stock;
	}

	@Override
	public List<Transaction> getBidPricePerStock(List<Transaction> transactionList, boolean isRealGame)
			throws IOException {
		String symbols = "";
		int count = 0;
		for (Transaction transaction : transactionList) {
			Stock transactionStock = transaction.getStock();
			if (count == (transactionList.size() - 1)) {
				symbols += transactionStock.getSymbol();
			} else {
				symbols += transactionStock.getSymbol() + ",";
				count++;
			}
		}
		String url = "https://api.iextrading.com/1.0/tops?symbols=" + symbols;

		String charset = "UTF-8"; // Or in Java 7 and later, use the
									// constant:
									// java.nio.charset.StandardCharsets.UTF_8.name()

		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		String text = new String();
		Boolean isLineEnd = false;
		Boolean isLineStart = false;
		ArrayList<String> myList = new ArrayList<String>();
		int value = 0;
		while (value != -1) {
			value = response.read();
			if ((char) value == '{') {
				isLineStart = true;
				isLineEnd = false;
			} else if ((char) value == '}') {
				isLineEnd = true;
				isLineStart = false;
			}

			if (isLineStart) {
				text += (char) value;
			} else if (isLineEnd) {
				text += (char) value;
				myList.add(text);
				text = "";
				isLineEnd = false;
			}
		}
		for (int i = 0; i < myList.size(); i++) {
			Stock theStock = transactionList.get(i).getStock();
			JSONObject obj = new JSONObject(myList.get(i));
			if (isRealGame) {
				theStock.setBidPrice(obj.getBigDecimal("bidPrice"));
			} else {
				theStock.setBidPrice(
						BigDecimal.valueOf(randomWithRange(-0.15, 0.15)).add(obj.getBigDecimal("lastSalePrice")));
			}
		}
		return transactionList;
	}

	@Override
	public List<Transaction> getAskPricePerStock(List<Transaction> transactionList, boolean isRealGame)
			throws IOException {
		String symbols = "";
		int count = 0;
		for (Transaction transaction : transactionList) {
			Stock transactionStock = transaction.getStock();
			if (count == (transactionList.size() - 1)) {
				symbols += transactionStock.getSymbol();
			} else {
				symbols += transactionStock.getSymbol() + ",";
				count++;
			}
		}
		String url = "https://api.iextrading.com/1.0/tops?symbols=" + symbols;

		String charset = "UTF-8"; // Or in Java 7 and later, use the
									// constant:
									// java.nio.charset.StandardCharsets.UTF_8.name()

		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		String text = new String();
		Boolean isLineEnd = false;
		Boolean isLineStart = false;
		ArrayList<String> myList = new ArrayList<String>();
		int value = 0;
		while (value != -1) {
			value = response.read();
			if ((char) value == '{') {
				isLineStart = true;
				isLineEnd = false;
			} else if ((char) value == '}') {
				isLineEnd = true;
				isLineStart = false;
			}

			if (isLineStart) {
				text += (char) value;
			} else if (isLineEnd) {
				text += (char) value;
				myList.add(text);
				text = "";
				isLineEnd = false;
			}
		}
		for (int i = 0; i < myList.size(); i++) {
			Stock theStock = transactionList.get(i).getStock();
			JSONObject obj = new JSONObject(myList.get(i));
			if (isRealGame) {
				theStock.setAskPrice(obj.getBigDecimal("bidPrice"));
			} else {
				theStock.setAskPrice(
						BigDecimal.valueOf(randomWithRange(-0.15, 0.15)).add(obj.getBigDecimal("lastSalePrice")));
			}
		}
		return transactionList;
	}

	@Override
	public List<Stock> getSpecificStocks(List<Stock> stockList, boolean isRealGame) throws IOException {

		String symbols = "";
		for (Stock stockObj : stockList) {
			if (stockObj.equals(stockList.get(stockList.size() - 1))) {
				symbols += stockObj.getSymbol();
			} else {
				symbols += stockObj.getSymbol() + ",";
			}
		}

		String url = "https://api.iextrading.com/1.0/tops?symbols=" + symbols;

		String charset = "UTF-8"; // Or in Java 7 and later, use the
									// constant:
									// java.nio.charset.StandardCharsets.UTF_8.name()

		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		String text = new String();
		Boolean isLineEnd = false;
		Boolean isLineStart = false;
		ArrayList<String> myList = new ArrayList<String>();
		int value = 0;
		while (value != -1) {
			value = response.read();
			if ((char) value == '{') {
				isLineStart = true;
				isLineEnd = false;
			} else if ((char) value == '}') {
				isLineEnd = true;
				isLineStart = false;
			}

			if (isLineStart) {
				text += (char) value;
			} else if (isLineEnd) {
				text += (char) value;
				myList.add(text);
				text = "";
				isLineEnd = false;
			}
		}
		for (int i = 0; i < myList.size(); i++) {
			Stock stock = stockList.get(i);
			JSONObject obj = new JSONObject(myList.get(i));
			if (isRealGame) {
				stock.setAskPrice(obj.getBigDecimal("askPrice"));
				stock.setBidPrice(obj.getBigDecimal("bidPrice"));
			} else {
				stock.setAskPrice(
						BigDecimal.valueOf(randomWithRange(-0.15, 0.15)).add(obj.getBigDecimal("lastSalePrice")));
				stock.setBidPrice(
						BigDecimal.valueOf(randomWithRange(-0.15, 0.15)).add(obj.getBigDecimal("lastSalePrice")));
			}

		}

		return stockList;
	}

	@Override
	public Stock getSingleStock(String symbol, boolean isRealGame) throws IOException {

		String url = "https://api.iextrading.com/1.0/tops?symbols=" + symbol;

		String charset = "UTF-8"; // Or in Java 7 and later, use the
									// constant:
									// java.nio.charset.StandardCharsets.UTF_8.name()

		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		String text = new String();
		Boolean isLineEnd = false;
		Boolean isLineStart = false;
		ArrayList<String> myList = new ArrayList<String>();
		int value = 0;
		while (value != -1) {
			value = response.read();
			if ((char) value == '{') {
				isLineStart = true;
				isLineEnd = false;
			} else if ((char) value == '}') {
				isLineEnd = true;
				isLineStart = false;
			}

			if (isLineStart) {
				text += (char) value;
			} else if (isLineEnd) {
				text += (char) value;
				myList.add(text);
				text = "";
				isLineEnd = false;
			}
		}
		Stock stock = new Stock();
		for (int i = 0; i < myList.size(); i++) {
			JSONObject obj = new JSONObject(myList.get(i));
			if (isRealGame) {
				stock.setAskPrice(obj.getBigDecimal("askPrice"));
				stock.setBidPrice(obj.getBigDecimal("bidPrice"));
			} else {
				stock.setAskPrice(
						BigDecimal.valueOf(randomWithRange(-0.15, 0.15)).add(obj.getBigDecimal("lastSalePrice")));
				stock.setBidPrice(
						BigDecimal.valueOf(randomWithRange(-0.15, 0.15)).add(obj.getBigDecimal("lastSalePrice")));
			}
		}

		return stock;
	}

	private double randomWithRange(double min, double max) {
		double range = (max - min) + 0.1;
		return (double) (Math.random() * range) + min;
	}

}
