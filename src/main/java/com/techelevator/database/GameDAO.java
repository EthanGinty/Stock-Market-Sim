package com.techelevator.database;


import java.time.LocalDate;
import java.util.List;

import com.techelevator.pojo.Game;
import com.techelevator.pojo.User;

public interface GameDAO {

	public void createGame(String nameOfGame, LocalDate startDate, LocalDate endDate, String ownerName, boolean isRealGame,
			String[] players);

	public void addPlayerToGame(String[] userIds,long gameId);

	public List<Game> getAllGamesForUser(long userId);

	public List<User> getAllPlayersbyGameId(long gameId);

	public Game getGameByGameId(long gameId);
	
	public boolean isRealGame(long gameId);
	


}
