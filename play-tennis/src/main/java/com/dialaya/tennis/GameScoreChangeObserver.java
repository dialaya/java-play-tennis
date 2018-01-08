/**
 * 
 */
package com.dialaya.tennis;

import com.dialaya.tennis.util.Util;

/**
 * @author dialaya
 *
 */
public class GameScoreChangeObserver implements SetObserver {
	//logger ??
	//private static final
	private final Game observedGame; 

	/**
	 * 
	 */
	public GameScoreChangeObserver(Game g) {
		if(g != null) {
			this.observedGame = g;
			this.observedGame.addSetObserver(this);
		} else {
			observedGame = null;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.dialaya.tennis.SetObserver#update()
	 */
	public void update() {
		displayGameScore();
	}
	
	private void displayGameScore() {
		Util.displayGameScore(observedGame);
	}

}
