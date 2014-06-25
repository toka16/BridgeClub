package bridge;

import bridge.Card;
import bridge.Table;

import java.util.ArrayList;
import java.util.List;
import bridge.ConvertStringToEnumerator.Suit;

public class PartOfTable {
	
	// Instance variables:
	private User user;
	private Table table;
	private MyTimer timer;
	private Card cardInPlay;
	private List<Card> remainCardList;
	
	/**
	 * Constructor of PartOfTable class.
	 * @param user
	 */
	public PartOfTable(User user, Table table){
		this.user = user;
		this.table = table;
		this.timer = new MyTimer(TIME, this);
		remainCardList = new ArrayList<Card>();
	}
	
	/**
	 * The method sets cardInPlay object.
	 * @param card
	 */
	public void setPlayCard(Card card){
		cardInPlay = card;
	}
	
	
	
	/**
	 * The method saves user cards data.
	 * @param card
	 */
	public void addUserCard(Card card){
		remainCardList.add(card);
	}
	
	/**
	 * The method removes card object from the card list.
	 * @param card
	 */
	public void removeCard(Card card){
		remainCardList.remove(card);
	}
	
	/**
	 * The method returns table object.
	 * @return
	 */
	public Table getTable(){
		return table;
	}
	
	/**
	 * The method returns user object.
	 * @return
	 */
	public User getUser(){
		return user;
	}
	
	/**
	 * The method returns card that is in paly.
	 * @return
	 */
	public Card playingCard(){
		return cardInPlay;
	}
	
	/**
	 * The method returns cards list which are in the hand of user and is valid.
	 * @param leadCard - first card in "playHand"
	 * @param trumpSuit - suit which is trump.
	 * @return
	 */
	public List<Card> getValidCardsList(Card leadCard){
		List<Card> validCards = new ArrayList<Card>();
		if(leadCard!=null){
			fillsList(validCards, leadCard.getSuit());
		}
		return (validCards.size() == 0) ? remainCardList : validCards;
	}
	
	public List<Card> getCardList(){
		return remainCardList;
	}
	
	public MyTimer getTimer(){
		return timer;
	}
	
	public void leaveGame(){
		
	}
	
	public void setAllCard(List<Card> cards){
		for(int i = 0; i < cards.size(); i++){
			Card card = cards.get(i);
			remainCardList.add(card);
		}
	}
	
	/**
	 * The method saves cards which have the same suit as leader card.
	 * @param cardsList - list of valid cards
	 * @param suit - suit of leader card or trump.
	 */
	private void fillsList(List<Card> cardsList, Suit suit){
		for(int i = 0; i < remainCardList.size(); i++){
			Card cardInHand = remainCardList.get(i);
			if(cardInHand.getSuit().equals(suit)){
				cardsList.add(cardInHand);
			}
		}
	}
	
	
	// Constant variables:
	private static final int TIME = 7;
}
