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
		Match m = new Match("A", "B");
		m.play();
		
		int i = 0;
		String[] players = new String[] {"A","B"};
		
		while(i++ < 1000) {
			int idx = new Random().nextInt(players.length);
			String serviceWinner = players[idx];
			
			m.winService(serviceWinner);
			System.out.println(m.getCurrentGameStatus());
		}
		
	}

}
