package com.dialaya.tennis;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.dialaya.tennis.util.Util;

/**
 * A 2 players tennis game representation
 * 
 * @author dialaya
 * 
 */
public class Game extends Set implements ObservableSet, Serializable {

	private static final long serialVersionUID = -4104184133739608721L;
	// points
	private AtomicInteger player1Service = new AtomicInteger(0);
	private AtomicInteger player2Service = new AtomicInteger(0);
	
	// tie break counts
	private int player1TieCount;
	private int player2TieCount;

	private AtomicInteger player1Score = new AtomicInteger(0);
	private AtomicInteger player2Score = new AtomicInteger(0);


	private List<SetObserver> gameObserverList = new ArrayList<SetObserver>();
	
	Game(Match m) {
		super(m);
		addSetObserver(m);
		addSetObserver(new GameScoreChangeObserver(this));
	}


	private void winScore(String player) {
		if (!getMatch().isMatchPlayer(player) || !Util.isBlank(game)) {
			// FIXME ignore or warn
			return;
		}
		
		
		if (getMatch().getPlayer1().equalsIgnoreCase(player)) {
			if (type == GameType.STD) {
				player1Score.incrementAndGet();
			} else {
				player1TieCount++;
			}
			
		} else if (getMatch().getPlayer2().equalsIgnoreCase(player)) {
			if (type == GameType.STD) {
				player2Score.incrementAndGet();
			} else {
				player2TieCount++;
			}
			
		}
	}

	void winService(String player) {
		// a service represents a point
		// regarding the game type
		if(!Util.isBlank(game)) {
			//FIXME: stop.
			return;
		}

		if (type == GameType.STD) {
			if (getMatch().getPlayer1().equalsIgnoreCase(player)) {
				player1Service.incrementAndGet();
			} else if (getMatch().getPlayer2().equalsIgnoreCase(player)) {
				player2Service.incrementAndGet();
			}
		} else {
			if (getMatch().getPlayer1().equalsIgnoreCase(player)) {
				player1TieCount++;
			} else if (getMatch().getPlayer2().equalsIgnoreCase(player)) {
				player2TieCount++;
			}
		}
		
		updateGameScore(player);
	}

	/**
	 * close game for a score.
	 */
	private void resetServicePoint() {
		player1Service.set(0);
		player2Service.set(0);
	}

	String getGameStatus() {
		String gameStatus = null;
		if(type == GameType.TIE) {
			gameStatus = MessageFormat.format(SCORE_PATTERN,player1TieCount, player2TieCount);
		} else {
			gameStatus = buildStandarGameStatus();
		}

		return gameStatus;
	}
	
	private String buildStandarGameStatus() {
		int p1Point = player1Service.get();
		int p2Point = player2Service.get();
		int sumPoint = p1Point + p2Point;
		String stdGameStatus = null;
		
		if ((p1Point == p2Point) && p1Point >= 3) {
			stdGameStatus = "Deuce";
		} else {
			if (sumPoint >= 4 && (p1Point > 3 || p2Point > 3)) {
				int diff = p1Point - p2Point;
				if (Math.abs(diff) == 1) {
					stdGameStatus = "advantage";
				} else if (diff >= 2) {
					// FIXME: used to make decision on service winner
					stdGameStatus = getMatch().getPlayer1();
				} else {
					// FIXME: used to make decision on service winner
					stdGameStatus = getMatch().getPlayer2();
				}
			} else {
				stdGameStatus = MessageFormat.format(SCORE_PATTERN,
						getServicePointCall(p1Point),
						getServicePointCall(p2Point));
			}
		}
		return stdGameStatus;
	}

	public String getServicePointCall(int sValue) {
		String callValue = "unknown";
		switch (sValue) {
		case 0:
			callValue = "0";
			break;
		case 1:
			callValue = "15";
			break;
		case 2:
			callValue = "30";
			break;
		case 3:
			callValue = "40";
			break;
		case 4:
			callValue = "game?";
			break;
		default:
			break;
		}

		return callValue;
	}
	
	private void updateGameScore(String player) {
		if(!getMatch().isMatchPlayer(player)) {
			return;
		}
		if(type == GameType.STD) {
			if(getMatch().getPlayer1().equalsIgnoreCase(getGameStatus()) || getMatch().getPlayer2().equalsIgnoreCase(getGameStatus())) {
				winScore(player);
				resetServicePoint();
			}
		}
		checkGame();
		notifySetObservers();
	}
	
	private void checkGame() {
		int p1Score = player1Score.get();
		int p2Score = player2Score.get();
		
		if(p1Score < 6 && p2Score < 6) {
			// nothing to do
			return;
		} else {
			int scoreDiff = p1Score - p2Score;
			if (scoreDiff == 0 && (p1Score == p2Score && p1Score == 6)) {
				// tie break
				type = GameType.TIE;
				int tieDiff = player1TieCount - player2TieCount;
				// stop condition
				if((player1TieCount >= 7 || player2TieCount >= 7) && Math.abs(tieDiff) >= 2) {
					// game, set
					if(tieDiff > 0) {
						player1Score.incrementAndGet();
						game = getMatch().getPlayer1();
					} else {
						player2Score.incrementAndGet();
						game = getMatch().getPlayer2();
					}
					type = GameType.STD;
				}
			} else if((p1Score >= 6 || p2Score >= 6) && Math.abs(scoreDiff) >= 2) {
				// stop game
				// game, set
				if(scoreDiff > 0) {
					game = getMatch().getPlayer1();
				} else {
					game = getMatch().getPlayer2();
				}
			}
		}
	}
	
	/**
	 * Get the player 1 score.
	 * @return
	 */
	public int getPlayer1Score() {
		return player1Score.get();
	}
	
	/**
	 * Get the the player 2 score
	 * @return
	 */
	public int getPlayer2Score() {
		return player2Score.get();
	}
	
	public int getPlayer1TieScore() {
		return player1TieCount;
	}
	
	public int getPlayer2TieScore() {
		return player2TieCount;
	}

	public void addSetObserver(SetObserver g) {
		if(g != null) {
			gameObserverList.add(g);
		}
		
	}


	public void removeSetObserver(SetObserver g) {
		if(g != null) {
			gameObserverList.remove(g);
		}
		
	}


	public void notifySetObservers() {
		for(SetObserver o : gameObserverList) {
			o.update();
		}
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
