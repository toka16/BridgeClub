package bridge;

import bridge.Card;
import bridge.ConvertStringToEnumerator.Suit;
import bridge.Table.Side;

public class PlayHand {
	
	// Instance variables:
	private Side leader;
	private Suit leadSuit;
	private Card[] cardsArray;
	private int gameID;
	
	/**
	 * Constructor of PlayHand class. It gives a side of leader and his/her card.
	 * @param sideLead
	 * @param suitLead
	 */
	public PlayHand(Side sideLead, Suit suitLead, int gameID){
		cardsArray = new Card[PLAYERS];
		leader = sideLead;
		leadSuit = suitLead;
		this.gameID = gameID;
	}
	
	/**
	 * The method saves given card and which side moved card.
	 * @param side
	 * @param card
	 */
	public void setCard(Side side, Card card){
		cardsArray[side.ordinal()] = card;
		SaverInDB.saveDealGameCards(gameID, card);
	}
	
	public Card getCard(Side side){
		return cardsArray[side.ordinal()];
	}
	
	/**
	 * The method returns lead side.
	 * @return
	 */
	public Side getSideLeader(){
		return leader;
	}
	
	/**
	 * The method returns playingHand suit.
	 * @return
	 */
	public Suit getSuit(){
		return leadSuit;
	}
	
	
	/**
	 * The method returns winner player side.
	 * @return
	 */
	public Side getWinner(){
		Card winnerCard = cardsArray[leader.ordinal()];
		int winnerCardIndex = leader.ordinal();
		for(Side cur = leader.nextSide(); cur!=leader; cur = cur.nextSide()){
			Card nextCard = cardsArray[cur.ordinal()];
			if(winnerCard.compare(nextCard, leadSuit) < 0){
				winnerCard = nextCard;
				winnerCardIndex = cur.ordinal();
			}
		}
		
//		for(int i = 1; i < cardsArray.length; i++){
//			Card nextCard = cardsArray[i];
//			if(winnerCard.compare(nextCard, leadSuit) < 0){
//				winnerCard = nextCard;
//				winnerCardIndex = i;
//			}
//		}
		return Side.values()[winnerCardIndex];
	}
	
	/**
	 * Check if all players have made their moves
	 * @return
	 */
	public boolean allDone(){
		for(int i = 0; i < cardsArray.length; i++){
			if(cardsArray[i] == null)
				return false;
		}
		return true;
	}
	
	// Constant variables:
	private static final int PLAYERS = 4;
}
