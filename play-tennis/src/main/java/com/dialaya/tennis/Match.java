/**
 * 
 */
package com.dialaya.tennis;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.dialaya.tennis.util.Util;

/**
 * 2 players Tennis match representation
 * @author dialaya
 *
 */
public class Match implements Constants, SetObserver{
	
	// sets
	private int player1SetCount = 0;
	private int player2SetCount = 0;
	
	// players
	private final String player1;
	private final String player2;
	
	// status
	private String matchStatus = MATCH_STATUS_NOTSTARTED;
	
	// games of a match
	private ArrayList<Set> playedGameList = new ArrayList<Set>();
	// the ongoing game
	private Game currentGame;
	
	// for follow
	StringBuilder matchWonSequence = new StringBuilder();
	
	/**
	 * 
	 */
	public Match(String player1, String player2) {
		if((player1 == null || "".equals(player1)) || (player2 == null || "".equals(player2))) {
			throw new IllegalArgumentException("player may not be null or empty");
		}
		
		if(player1.equalsIgnoreCase(player2)) {
			throw new IllegalArgumentException("A player could not play alone. Please, give different players");
		}
		this.player1 = player1;
		this.player2 = player2;
	}
	
	/**
	 * Starts a match
	 */
	public void play() {
		if(currentGame != null) {
			throw new IllegalStateException ("Could not start a new game until the current ends.");
		}
		currentGame = new Game(this);
		setMatchStatus(MATCH_STATUS_INPROGRESS);
		// game created !
	}
	
	/**
	 * Stop a match
	 */
	public void stop() {
		currentGame = null;
		if(isInProgress()) {
			setMatchStatus(MATCH_STATUS_NOTSTARTED);
		}
		System.out.println(toString());
	}
	
	public String getCurrentGameStatus() {
		if(currentGame != null) {
			return currentGame.getGameStatus();
		}
		return null;
	}

	/**
	 * @return the matchSetCount
	 */
	public int getMatchSetCount() {
		return playedGameList.size() + (currentGame != null ? 1 : 0);
	}


	/**
	 * @return the player1SetCount
	 */
	public int getPlayer1SetCount() {
		return player1SetCount;
	}

	/**
	 * @return the player2SetCount
	 */
	public int getPlayer2SetCount() {
		return player2SetCount;
	}

	public void winService(String player) {
		if(isInProgress() && currentGame != null && isMatchPlayer(player)) {
			currentGame.winService(player);
			matchWonSequence.append(player + " ");
		}
		// ignore
	}
	
	public boolean isMatchPlayer(String player) {
		if(!Util.isBlank(player)) {
			if(player1.equalsIgnoreCase(player) || player2.equalsIgnoreCase(player)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the match is in progress state
	 * @return true if it is in progress false otherwise
	 */
	public boolean isInProgress() {
		return MATCH_STATUS_INPROGRESS.equalsIgnoreCase(matchStatus);
	}

	/**
	 * @return the matchStatus
	 */
	public String getMatchStatus() {
		return matchStatus;
	}
	
	void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	/**
	 * @return the player1
	 */
	public String getPlayer1() {
		return this.player1;
	}
	
	private boolean isPlayer1(String player) {
		return !Util.isBlank(this.player1) && this.player1.equalsIgnoreCase(player);
	}

	/**
	 * @return the player2
	 */
	public String getPlayer2() {
		return this.player2;
	}
	
	private boolean isPlayer2(String player) {
		return !Util.isBlank(this.player2) && this.player2.equalsIgnoreCase(player);
	}
	

	public void update() {
		if(currentGame == null || !currentGame.hasWinner()) {
			// do nothing
			return;
		}
		Set playedGame = new PlayedGame(currentGame);
		playedGameList.add(playedGame);
		currentGame = null;
		checkMatch();
		System.out.println(toString());
	}
	
	private void checkMatch() {
		if(playedGameList != null && playedGameList.size() >= 3) {
			int p1SetCount = 0;
			int p2SetCount = 0;
			for(Set s : playedGameList) {
				if(s.hasWinner()) {
					// player wins set
					if(isPlayer1(s.getGame())) {
						p1SetCount++;
					} else if (isPlayer2(s.getGame())) {
						p2SetCount++;
					}
					
				}
			}
			player1SetCount = p1SetCount;
			player2SetCount = p2SetCount;
			// should have set to win game
			if(player1SetCount  == 3) {
				matchStatus = MessageFormat.format(MATCH_STATUS_PLAYER_WIN_PATTERN, player1);
				// End of match
				stop();
			} else if(player2SetCount == 3) {
				matchStatus = MessageFormat.format(MATCH_STATUS_PLAYER_WIN_PATTERN, player2);
				// End of match
				stop();
			}
		}
		// starts a new game to continue until the next decision (match winner)
		currentGame = new Game(this);
	}
	
	private String getMatchScore() {
		if(matchStatus.equalsIgnoreCase(MATCH_STATUS_NOTSTARTED)) {
			return "NA";
		}
		StringBuilder sb = new StringBuilder();
		for(Set s : playedGameList) {
			sb.append("(").append(s.getScore()).append(")");
		}
		if(matchStatus.equalsIgnoreCase(MATCH_STATUS_INPROGRESS) && currentGame != null) {
			sb.append("(").append(currentGame.getScore()).append(")");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		// used to display match detail
		StringBuilder mSb = new StringBuilder();
		mSb.append("Player 1: ").append(player1).append(System.getProperty("line.separator"))
		.append("Player 2: ").append(player2).append(System.getProperty("line.separator"))
		.append("Score: ").append(getMatchScore()).append(System.getProperty("line.separator"));
		if(currentGame != null && matchStatus.equalsIgnoreCase(MATCH_STATUS_INPROGRESS)) {
			mSb.append("Current game status: ").append(currentGame.getGameStatus()).append(System.getProperty("line.separator"));
		}
		mSb.append("Match Status: ").append(matchStatus).append(System.getProperty("line.separator"));
		return mSb.toString();
	}
}
