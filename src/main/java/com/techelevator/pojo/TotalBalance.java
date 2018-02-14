package com.techelevator.pojo;

import java.math.BigDecimal;

public class TotalBalance {
	
	private String userName;
	private BigDecimal totalBalance;

	

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
