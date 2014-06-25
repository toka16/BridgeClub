package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


import bridge.Card;
import bridge.CardDeck;
import bridge.ConvertStringToEnumerator.Nominal;
import bridge.ConvertStringToEnumerator.Suit;

public class TestCardDeck {
	
	// Instance variables:
	private CardDeck cardDeck;
	
	@Before
	public void setUp(){
		cardDeck = new CardDeck();
	}
	
	// The method tests arraySize. It must be 52.
	@Test
	public void testArraySize(){
		int size = 52;
		assertEquals(cardDeck.getDeckSize(), size);
	}
	
	// The method tests whether some cards are equals cards of array in CardDeck class.
	@Test
	public void testElems(){ // crad1.equal(card2) doesn't work ???????
		Card clubSec = new Card(Nominal.TWO, Suit.CLUB);
		Card cardInArray = cardDeck.getAppropCard(0);
		assertEquals(clubSec, cardInArray);
	}
	
	// The method tests CardDeck class compare method.
	@Test
	public void testCompare(){
		Card clubSec = new Card(Nominal.TWO, Suit.CLUB);
		Card dimondThree = new Card(Nominal.THREE, Suit.DIAMOND);
		int isEqual = clubSec.compare(dimondThree, Suit.DIAMOND);
		assertEquals(isEqual, -1);
		
		Card heartJack = new Card(Nominal.JACK, Suit.HEART);
		Card heartTen = new Card(Nominal.TEN, Suit.HEART);
		int isEqualHeart = heartJack.compare(heartTen, Suit.HEART);
		assertEquals(isEqualHeart, 1);
	}
}
