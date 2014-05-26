package game;

import game.Card.Suit;
import game.Table.Side;

/**
 * Class that manages one hand of the play
 * @author toka
 *
 */
public class PlayHand {
	private Card[] cards;
	private Suit leadSuit;

	private int leader;
	private int current;
	private Side[] sides;
	
	/**
	 * Initialize PlayHand
	 */
	public PlayHand(){
		cards = new Card[4];
		sides = Side.values();
	}
	
	/**
	 * Set side that made the first  move of the hand
	 * @param leader
	 */
	public void setLeader(Side leader){
		this.leader = current = leader.ordinal();
	}
	
	/**
	 * Set the lead suit
	 * @param leadSuit
	 */
	public void setLeadSuit(Suit leadSuit){
		this.leadSuit = leadSuit;
	}
	
	/**
	 * Set card that moved player
	 * @param card
	 */
	public void setCard(Card card){
		cards[current] = card;
	}
	
	// Count next index according to the sides clockwise
	private int nextIndex(int index){
		index = (index+1)%4;
		return index;
	}
	
	/**
	 * Set current player to next
	 * @return
	 */
	public Side next(){
		current = nextIndex(current);
		return sides[current];
	}
	
	/**
	 * Detect the winner side
	 * @return
	 */
	public Side winner(){
		Card leaderCard = cards[leader];
		int winnerIndex = leader;
		int currentIndex = leader;
		for(int i=1; i<4; i++){
			currentIndex = nextIndex(currentIndex);
			if(leaderCard.compare(cards[currentIndex], leadSuit) <= 0){
				winnerIndex = currentIndex;
				leaderCard = cards[winnerIndex];
			}
		}
		
		return sides[winnerIndex];
	}
	
	/**
	 * Check if all players have made their moves
	 * @return
	 */
	public boolean allDone(){
		for(int i=0; i<cards.length; i++){
			if(cards[i]==null)
				return false;
		}
		return true;
	}
	
	/**
	 * That method should be called when one hand is completed,
	 * winner is detected and next hand should be started.
	 */
	public void nextHand(){
		for(int i=0; i<cards.length; i++)
			cards[i]=null;
	}
}
