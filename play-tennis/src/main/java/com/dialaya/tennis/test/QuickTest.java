package com.dialaya.tennis.test;

import java.util.Random;

import com.dialaya.tennis.Match;

public class QuickTest {

	public QuickTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] players = new String[] {"A","B"};
		Match m = new Match(players[0], players[1]);
		m.play();
		
		int i = 0;
		
		while(i++ < 10000 && m.isInProgress()) {
			int idx = new Random().nextInt(players.length);
			String serviceWinner = players[idx];
			
			m.winService(serviceWinner);
			System.out.println(m.getCurrentGameStatus());
		}
		
	}

}
