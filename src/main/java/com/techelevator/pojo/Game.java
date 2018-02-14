package com.techelevator.pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class Game {

	private String nameOfGame;
	private LocalDate startDate;
	private LocalDate endDate;
	private String ownerName;
	private List <User> players = new ArrayList<>();
	private Long gameId;
	private boolean isRealGame;
	private boolean currentGame;

	public boolean isRealGame() {
		return isRealGame;
	}

	public void setRealGame(boolean isRealGame) {
		this.isRealGame = isRealGame;
	}

	public String getNameOfGame() {
		return nameOfGame;
	}

	public void setNameOfGame(String nameOfGame) {
		this.nameOfGame = nameOfGame;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public List <User> getPlayers() {
		return players;
	}

	public void setPlayers(List <User> players) {
		this.players = players;
	}


	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	
	public long getDaysRemaining() {
		LocalDate now = LocalDate.now();
		LocalDate endDate = this.getEndDate();
		long daysBetween = ChronoUnit.DAYS.between(now, endDate);
		 
		return daysBetween;
	}
	
	public boolean isCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(boolean currentGame) {
		this.currentGame = currentGame;
	}

}
