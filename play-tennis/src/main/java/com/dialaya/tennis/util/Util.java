/**
 * 
 */
package com.dialaya.tennis.util;

import com.dialaya.tennis.Game;

/**
 * @author dialaya
 *
 */
public class Util {
	
	/**
	 * Displays game score
	 * @param game
	 */
	public static void displayGameScore(final Game game) {
		if(game != null) {
			System.out.println("(" + game.getScore() + ")");
		}
	}
	
	//public static void displayMatchScores
	
	public static boolean isBlank(String v) {
		return v == null || "".equals(v.trim());
	}

}
