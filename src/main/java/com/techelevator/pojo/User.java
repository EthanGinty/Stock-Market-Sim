package com.techelevator.pojo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

	private long userId;
	private String userName;

	@Size(min = 10, message = "Password too short")
	@Pattern.List({ @Pattern(regexp = ".*[A-Z].*", message = "Password needs a capital letter"),
			@Pattern(regexp = ".*[a-z].*", message = "Password needs a lowercase letter"),
			@Pattern(regexp = ".*[0-9].*", message = "Password needs a digit"),
			@Pattern(regexp = ".*[!@#$%^&*()].*", message = "Password must contain one of: !@#$%^&*()"),

	})
	private String password;
	private String salt;
	private String firstName;
	private String lastName;
	private List<Stock> ownedStocks;
	private List<Game> games;
	private Map<Long, BigDecimal> availableBalanceForGames;
	private Map<Long, BigDecimal> totalBalanceForGames;
	private List<Transaction> userTransactionsForTotalBalance;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Stock> getOwnedStocks() {
		return ownedStocks;
	}

	public List<Game> getGames() {
		return games;
	}

	public void addStock(Stock stock) {
		this.ownedStocks.add(stock);
	}

	public void addGame(Game game) {
		this.games.add(game);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Map<Long, BigDecimal> getAvailableBalanceForGames() {
		return availableBalanceForGames;
	}

	public void setAvailableBalanceForGames(Map<Long, BigDecimal> availableBalanceForGames) {
		this.availableBalanceForGames = availableBalanceForGames;
	}

	public List<Transaction> getUserTransactionsForTotalBalance() {
		return userTransactionsForTotalBalance;
	}

	public void setUserTransactionsForTotalBalance(List<Transaction> userTransactionsForTotalBalance) {
		this.userTransactionsForTotalBalance = userTransactionsForTotalBalance;
	}

	public Map<Long, BigDecimal> getTotalBalanceForGames() {
		return totalBalanceForGames;
	}

	public void setTotalBalanceForGames(Map<Long, BigDecimal> totalBalanceForGames) {
		this.totalBalanceForGames = totalBalanceForGames;
	}



}
