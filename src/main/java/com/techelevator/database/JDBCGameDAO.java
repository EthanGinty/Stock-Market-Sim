package com.techelevator.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.Game;
import com.techelevator.pojo.User;

@Component
public class JDBCGameDAO implements GameDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCGameDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createGame(String nameOfGame, LocalDate startDate, LocalDate endDate, String ownerName, boolean isRealGame,
			String[] userIds) {
		String nextValue = ("Select nextval('game_game_id_seq')");
		SqlRowSet results = jdbcTemplate.queryForRowSet(nextValue);
		results.next();
		int gameId = results.getInt(1);
		jdbcTemplate.update(
				"INSERT INTO game(game_id, name_of_game, start_date, end_date, owner_name, is_real_game) " + "VALUES (?, ?, ?, ?, ?, ?)",
				gameId, nameOfGame, startDate, endDate, ownerName, isRealGame);
		for (String i : userIds) {
			long userId = Long.parseLong(i);
			jdbcTemplate.update("INSERT INTO game_user( game_id, user_id, available_balance) VALUES (?, ?, '100000.00')", gameId, userId);
		}
	}

	@Override
	public void addPlayerToGame(String[] userIds, long gameId) {
		
		for(String id: userIds){
			Long userId = Long.parseLong(id);
			
			jdbcTemplate.update("INSERT INTO game_user (user_id, game_id, available_balance) VALUES (?,?, '100000')", userId, gameId);
		}

	}

	@Override
	public List<Game> getAllGamesForUser(long userId) {
		List<Game> allGamesForUser = new ArrayList<>();
		String sqlGetAllForUser = "SELECT * from game where game_id in"
				+ "(Select game_id from game_user where user_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllForUser, userId);
		while (results.next()) {
			Game game = mapRowToGame(results);
			allGamesForUser.add(game);
		}

		return allGamesForUser;

	}

	@Override
	public List<User> getAllPlayersbyGameId(long gameId) {
		List<User> allPlayersByGame = new ArrayList<>();
		String sqlGetAllPlayersbyGameId = "SELECT * from app_user where user_id in"
				+ "(Select user_id from game_user where game_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllPlayersbyGameId, gameId);
		while (results.next()) {
			User user = mapRowToUser(results);
			allPlayersByGame.add(user);
		}
		return allPlayersByGame;
	}

	@Override
	public Game getGameByGameId(long gameId) {
		Game game = new Game();
		String sqlGetGameByGameId = "SELECT * from game where game_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetGameByGameId, gameId);
		if(results.next()) {
			game = mapRowToGame(results);
		}
		return game;
	}
	@Override
	public boolean isRealGame(long gameId) {
		boolean realGame = false;
		String sqlIfRealGame = "SELECT is_real_game FROM game WHERE game_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlIfRealGame, gameId);
		if(results.next()) {
			realGame = results.getBoolean("is_real_game");
		}
		return realGame;
		
	}
	

	private Game mapRowToGame(SqlRowSet results) {
		Game g = new Game();
		g.setGameId((results.getLong("game_id")));
		g.setNameOfGame(results.getString("name_of_game"));
		g.setStartDate(results.getDate("start_date").toLocalDate());
		g.setEndDate(results.getDate("end_date").toLocalDate());
		g.setOwnerName(results.getString("owner_name"));
		g.setRealGame(results.getBoolean("is_real_game"));
		return g;
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



	


	

}
