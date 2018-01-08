/**
 * 
 */
package com.dialaya.tennis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dialaya
 *
 */
public class MatchTest {
	
	private Match match;
	
	private String matchWinSequence;
	
	String player1;
	String player2;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		player1 = "A";
		player2 = "B";
		match = new Match(player1, player2);
		matchWinSequence = "A A A A B A A A A B B B A B B B A B B B B A B B B A B B B";
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		player1 = null;
		player2 = null;
		match = null;
		matchWinSequence = null;
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#play()}.
	 */
	@Test
	public void testPlay() throws Exception{
		match.play();
		try {
			match.play();
			fail();
		} catch (IllegalStateException expectedEx) {
			assertEquals("Could not start a new game until the current ends.", expectedEx.getMessage());
		}
		
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#stop()}.
	 */
	@Test
	public void testStop() {
		assertEquals(Constants.MATCH_STATUS_NOTSTARTED, match.getMatchStatus());
		assertTrue(null == match.getCurrentGameStatus());
		match.play();
		assertEquals(Constants.MATCH_STATUS_INPROGRESS, match.getMatchStatus());
		assertTrue(null != match.getCurrentGameStatus());
		match.stop();
		assertTrue(null == match.getCurrentGameStatus());
		assertEquals(Constants.MATCH_STATUS_NOTSTARTED, match.getMatchStatus());
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getCurrentGameStatus()}.
	 */
	@Test
	public void testGetCurrentGameStatus() {
		assertTrue(null == match.getCurrentGameStatus());
		match.play();
		assertEquals(Constants.MATCH_STATUS_INPROGRESS, match.getMatchStatus());
		assertEquals(match.getCurrentGameStatus() , "0-0");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "15-0");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "30-0");
		match.winService(player2);
		assertEquals(match.getCurrentGameStatus() , "30-15");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "40-15");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "0-0");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "15-0");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "30-0");
		match.winService(player2);
		assertEquals(match.getCurrentGameStatus() , "30-15");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "40-15");
		match.winService(player2);
		match.winService(player2);
		assertEquals(match.getCurrentGameStatus() , "Deuce");
		match.stop();
		assertTrue(null == match.getCurrentGameStatus());
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getMatchSetCount()}.
	 */
	@Test
	public void testGetMatchSetCount() {
		matchWinSequence = "A A A A B A A A A B B B A B B B A B B B B A B B B A B B B B A B B B A B B B B A A A A B A A A A B B B A B B B A B B B B A B B B A B B B B A B B B A B B B B A A B A A A B A A A B B A B A B A B A A A A B A B B A B B A B B A A A B B A B B A A B A B A A B A B A";
		String [] wins = matchWinSequence.split(" ");
		match.play();
		for(String v : wins) {
			match.winService(v);
		}
		assertTrue(3 == match.getMatchSetCount());
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getPlayer1SetCount()}.
	 */
	@Test
	public void testGetPlayer1SetCount() {
		matchWinSequence = "A A A A B A A A A B B B A B B B A B B B B A B B B A B B B B A B B B A B B B B A A A A B A A A A B B B A B B B A B B B B A B B B A B B B B A B B B A B B B B A A B A A A B A A A B B A B A B A B A A A A B A B B A B B A B B A A A B B A B B A A B A B A A B A B A A A B B A B A A A B A A B A B A B A A B A B B B A A B B B B A A A B A A B B A B B B B B B B A A B B A A B A B B B B A B B A";
		String [] wins = matchWinSequence.split(" ");
		match.play();
		for(String v : wins) {
			match.winService(v);
		}
		assertTrue(1 == match.getPlayer1SetCount());
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getPlayer2SetCount()}.
	 */
	@Test
	public void testGetPlayer2SetCount() {
		matchWinSequence = "A A A A B A A A A B B B A B B B A B B B B A B B B A B B B B A B B B A B B B B A A A A B A A A A B B B A B B B A B B B B A B B B A B B B B A B B B A B B B B A A B A A A B A A A B B A B A B A B A A A A B A B B A B B A B B A A A B B A B B A A B A B A A B A B A A A B B A B A A A B A A B A B A B A A B A B B B A A B B B B A A A B A A B B A B B B B B B B A A B B A A B A B B B B A B B A";
		String [] wins = matchWinSequence.split(" ");
		match.play();
		for(String v : wins) {
			match.winService(v);
		}
		assertTrue(2 == match.getPlayer2SetCount());
		match.winService("B");
		assertTrue(3 == match.getPlayer2SetCount());
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#winService(java.lang.String)}.
	 */
	@Test
	public void testWinService() {
		match.winService("K");
		assertTrue(null == match.getCurrentGameStatus());
		match.play();
		assertEquals(match.getCurrentGameStatus() , "0-0");
		match.winService(player1);
		assertEquals(match.getCurrentGameStatus() , "15-0");
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#isMatchPlayer(java.lang.String)}.
	 */
	@Test
	public void testIsMatchPlayer() {
		assertFalse(match.isMatchPlayer("K"));
		assertTrue(match.isMatchPlayer(player1));
		assertTrue(match.isMatchPlayer(player1));
		match = new Match("X", "Y");
		assertFalse(match.isMatchPlayer(player1));
		assertFalse(match.isMatchPlayer(player1));
		assertFalse(match.isMatchPlayer("K"));
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getMatchStatus()}.
	 */
	@Test
	public void testGetMatchStatus() {
		assertEquals(Constants.MATCH_STATUS_NOTSTARTED, match.getMatchStatus());
		match.play();
		assertEquals(Constants.MATCH_STATUS_INPROGRESS, match.getMatchStatus());
		match.stop();
		assertEquals(Constants.MATCH_STATUS_NOTSTARTED, match.getMatchStatus());
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getPlayer1()}.
	 */
	@Test
	public void testGetPlayer1() {
		assertFalse("K".equalsIgnoreCase(match.getPlayer1()));
		assertTrue("A".equalsIgnoreCase(match.getPlayer1()));
		match = new Match("X", "Y");
		assertFalse("A".equalsIgnoreCase(match.getPlayer1()));
		assertTrue("X".equalsIgnoreCase(match.getPlayer1()));
		assertFalse("K".equalsIgnoreCase(match.getPlayer1()));
	}

	/**
	 * Test method for {@link com.dialaya.tennis.Match#getPlayer2()}.
	 */
	@Test
	public void testGetPlayer2() {
		assertFalse("A".equalsIgnoreCase(match.getPlayer2()));
		assertTrue("B".equalsIgnoreCase(match.getPlayer2()));
		match = new Match("D", "K");
		assertFalse("B".equalsIgnoreCase(match.getPlayer2()));
		assertFalse("D".equalsIgnoreCase(match.getPlayer2()));
		assertTrue("K".equalsIgnoreCase(match.getPlayer2()));
	}

}
