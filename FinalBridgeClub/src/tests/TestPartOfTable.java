package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bridge.Card;
import bridge.ConvertStringToEnumerator.Nominal;
import bridge.ConvertStringToEnumerator.Suit;
import bridge.PartOfTable;
import bridge.Table;
import bridge.User;
import bridge.User.Status;

public class TestPartOfTable {
	
	private PartOfTable partTable;
	private List<Card> ls;
	private Card twoHeart; 
	private Card threeHeart;
	private Card fiveSpade;
	private Card fiveDiamond;
	private Card sevenDiamond;
	private Card cardLead;
	
	@Before
	public void test(){
		partTable = new PartOfTable(new User("userName", Status.USER),  new Table());
		ls = new ArrayList<Card>();
		twoHeart = new Card(Nominal.TWO, Suit.HEART); 
		threeHeart = new Card(Nominal.THREE, Suit.HEART);;
		fiveSpade = new Card(Nominal.FIVE, Suit.SPADE);;
		fiveDiamond = new Card(Nominal.FIVE, Suit.DIAMOND);;
		sevenDiamond = new Card(Nominal.SEVEN, Suit.DIAMOND);;
		cardLead = new Card(Nominal.FIVE, Suit.HEART);
		fillList();
	}
	

	// The method test list validation.
	@Test
	public void testCardsList(){
		assertEquals(partTable.getCardList(), ls);
	}
	
	// The method fills the given list.
	private void fillList(){
		ls.add(twoHeart);
		ls.add(threeHeart);
		ls.add(fiveSpade);
		ls.add(fiveDiamond);
		ls.add(sevenDiamond);
		
		partTable.addUserCard(twoHeart);
		partTable.addUserCard(threeHeart);
		partTable.addUserCard(fiveSpade);
		partTable.addUserCard(fiveDiamond);
		partTable.addUserCard(sevenDiamond);
	}
	
	// The method compares valid cards list.
	@Test
	public void testValidCardsOne(){
		assertEquals(partTable.getValidCardsList(new Card(Nominal.TWO, Suit.CLUB)), partTable.getCardList());
	}
	
	@Test
	public void testValidCardsTwo(){
		List<Card> validList = new ArrayList<Card>();
		validList.add(twoHeart);
		validList.add(threeHeart);
		assertEquals(partTable.getValidCardsList(cardLead), validList);
	}
	
	// The method tests cards lists after removes.
	@Test
	public void testaddUserCard(){
		removesCard();
		assertEquals(partTable.getCardList(), ls);
	}
	
	// The method removes some elements from lists.
	private void removesCard(){
		partTable.removeCard(twoHeart);
		partTable.removeCard(threeHeart);
		
		ls.remove(twoHeart);
		ls.remove(threeHeart);
	}
	
}
