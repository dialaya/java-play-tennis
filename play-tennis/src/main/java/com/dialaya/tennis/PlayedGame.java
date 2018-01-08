/**
 * 
 */
package com.dialaya.tennis;

import java.io.Serializable;

/**
 * @author dialaya
 *
 */
public class PlayedGame extends Set implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6298938025036790172L;
	private int player1Score;
	private int player2Score;
	
	private int player1TieScore;
	private int player2TieScore;

	/**
	 * @param m
	 */
	public PlayedGame(Match m) {
		super(m);
	}
	
	PlayedGame (Game game) {
		this(game.getMatch());
		if(game != null) {
			this.player1Score = game.getPlayer1Score();
			this.player2Score = game.getPlayer2Score();
			this.game = game.getGame();
			this.player1TieScore = game.getPlayer1TieScore();
			this.player2TieScore = game.getPlayer2TieScore();
		}
	}

	/* (non-Javadoc)
	 * @see com.dialaya.tennis.Set#getPlayer1Score()
	 */
	@Override
	public int getPlayer1Score() {
		return player1Score;
	}

	/* (non-Javadoc)
	 * @see com.dialaya.tennis.Set#getPlayer2Score()
	 */
	@Override
	public int getPlayer2Score() {
		return player2Score;
	}

	@Override
	public int getPlayer1TieScore() {
		return this.player1TieScore;
	}

	@Override
	public int getPlayer2TieScore() {
		return this.player2TieScore;
	}

}
