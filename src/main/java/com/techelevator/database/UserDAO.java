package com.techelevator.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.techelevator.pojo.TotalBalance;
import com.techelevator.pojo.User;

public interface UserDAO {

	public void saveUser(String userName, String password, String firstName, String lastName);
	
	public User getUserByUsername(String userName);

	public boolean searchForUsernameAndPassword(String userName, String password);

	public void updatePassword(String userName, String password);
	
	public List<User> getAllUsers();
	
	public User getUserByUserId(long userId);
	
	public Map<Long, BigDecimal> getAvailableBalancesByUserId(long userId);
	
	public void setTotalBalance (long userId, long gameId, BigDecimal totalBalance);
	
	public List<TotalBalance> getTotalBalancesForGame(long gameId);
	
	public BigDecimal getTotalBalanceByUser(long userId, long gameId);
	

}
