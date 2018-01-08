/**
 * 
 */
package com.dialaya.tennis;

import java.text.MessageFormat;


/**
 * @author dialaya
 *
 */
public abstract class Set implements Constants{
	// game match to keep a ref
	private final Match match;
	
	// the set winner
	protected String game;
	
	enum GameType {
		STD, TIE;
	}

	// by default a game is standard type(until it changes to a tie)
	protected GameType type = GameType.STD;
	
	/**
	 * 
	 */
	public Set(Match m) {
		if(m == null) {
			throw new IllegalArgumentException("Match should not be null.");
		}
		this.match = m;
	}
	
	public Match getMatch() {
		return match;
	}
	
	/**
	 * Get the player 1 score.
	 * @return
	 */
	abstract public int getPlayer1Score();
	
	/**
	 * Get the the player 2 score
	 * @return
	 */
	abstract public int getPlayer2Score();
	
	
	// When Tiebreak is played
	abstract public int getPlayer1TieScore();
	abstract public int getPlayer2TieScore();
	
	/**
	 * Returns the score
	 * 
	 * @return the score
	 */
	public String getScore() {
		return MessageFormat.format(SCORE_PATTERN, getPlayer1Score(), getPlayer2Score());
	}
	
	/**
	 * Returns the Set winner, player name, if it has otherwise returns null
	 * @return the winner or null
	 */
	public String getGame() {
		return game;
	}
	
	/**
	 * Check if the game has a winner
	 * @return
	 */
	public boolean hasWinner() {
		return game != null && !"".equals(game);
	}

}
