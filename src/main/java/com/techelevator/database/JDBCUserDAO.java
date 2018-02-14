package com.techelevator.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.TotalBalance;
import com.techelevator.pojo.User;
import com.techelevator.security.PasswordHasher;

@Component
public class JDBCUserDAO implements UserDAO {

	private JdbcTemplate jdbcTemplate;
	private PasswordHasher passwordHasher;

	@Autowired
	public JDBCUserDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.passwordHasher = new PasswordHasher();
	}

	@Override
	public void saveUser(String userName, String password, String firstName, String lastName) {
		byte[] salt = passwordHasher.generateRandomSalt();
		String hashedPassword = passwordHasher.computeHash(password, salt);
		String saltString = new String(Base64.encode(salt));

		jdbcTemplate.update(
				"INSERT INTO app_user(user_name, password, salt, first_name, last_name) " + "VALUES (?,?,?,?,?)",
				userName, hashedPassword, saltString, firstName, lastName);
	}

	@Override
	public User getUserByUsername(String userName) {
		User user = new User();
		String sqlSearchForUser = "SELECT * " + "FROM app_user " + "WHERE UPPER(user_name) = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForUser, userName.toUpperCase());
		if (results.next()) {
			user = mapRowToUser(results);
		}
		return user;
	}

	@Override
	public boolean searchForUsernameAndPassword(String userName, String password) {
		String sqlSearchForUser = "SELECT * " + "FROM app_user " + "WHERE UPPER(user_name) = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForUser, userName.toUpperCase());
		if (results.next()) {
			String dbSalt = results.getString("salt");
			String dbHashedPassword = results.getString("password");
			String passwordHash = passwordHasher.computeHash(password, Base64.decode(dbSalt));

			return passwordHash.equals(dbHashedPassword);
		} else {
			return false;
		}

	}

	@Override
	public void updatePassword(String userName, String password) {
		byte[] salt = passwordHasher.generateRandomSalt();
		String hashedPassword = passwordHasher.computeHash(password, salt);
		String saltString = new String(Base64.encode(salt));
		jdbcTemplate.update("UPDATE app_user SET password = ?, salt = ? WHERE user_name = ?", hashedPassword,
				saltString, userName);
	}

	@Override
	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList<>();
		String sqlAllUsers = "SELECT *" + "FROM app_user";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllUsers);
		while (results.next()) {
			User user = mapRowToUser(results);
			allUsers.add(user);

		}
		return allUsers;
	}

	private User mapRowToUser(SqlRowSet results) {
		User u = new User();
		u.setUserId(results.getLong("user_id"));
		u.setUserName(results.getString("user_name"));
		u.setPassword(results.getString("password"));
		u.setFirstName(results.getString("first_name"));
		u.setLastName(results.getString("last_name"));
		u.setSalt(results.getString("salt"));

		return u;
	}

	@Override
	public User getUserByUserId(long userId) {
		User user = new User();
		String sqlSearchForUser = "SELECT * " + "FROM app_user " + "WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForUser, userId);
		while (results.next()) {
			user = mapRowToUser(results);
		}
		return user;
	}

	@Override
	public Map<Long, BigDecimal> getAvailableBalancesByUserId(long userId) {
		String sqlGetAvailableBalancesByUserId = "select * from game_user where user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableBalancesByUserId, userId);
		Map<Long, BigDecimal> map = new HashMap<>();
		while (results.next()) {
			map.put(results.getLong("game_id"), results.getBigDecimal("available_balance"));
		}

		return map;
	}

	@Override
	public void setTotalBalance(long userId, long gameId, BigDecimal totalBalance) {
		jdbcTemplate.update("UPDATE game_user SET total_balance = ? WHERE game_id = ? AND user_id= ?", totalBalance, gameId, userId);
		
	}

	@Override
	public List<TotalBalance> getTotalBalancesForGame(long gameId) {
		List<TotalBalance> totalBalances = new ArrayList<>();
		String sqlGetTotalBalances = "select app_user.user_name as username, game_user.total_balance from game_user "
							       + "Inner join app_user on app_user.user_id = game_user.user_id "
							       + "where game_id = ? order by total_balance DESC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTotalBalances, gameId);
		while(results.next()){
			TotalBalance tb = mapRowToTotalBalance(results);
			totalBalances.add(tb);
		}
		return totalBalances;
	}

	private TotalBalance mapRowToTotalBalance(SqlRowSet results){
		TotalBalance tb = new TotalBalance();
		tb.setTotalBalance(results.getBigDecimal("total_balance"));
		tb.setUserName(results.getString("username"));
		
		return tb;
		
	}

	@Override
	public BigDecimal getTotalBalanceByUser(long userId, long gameId) {
		String sqlTotalBalanceByUser= "Select total_balance from game_user where user_id = ? and game_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlTotalBalanceByUser, userId, gameId);
		result.next();
		BigDecimal totalBalance = result.getBigDecimal("total_balance");
		return totalBalance;
	}
}
