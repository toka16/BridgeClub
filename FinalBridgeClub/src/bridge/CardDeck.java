package bridge;

import java.util.Random;

import bridge.Card;
import bridge.ConvertStringToEnumerator.Nominal;
import bridge.ConvertStringToEnumerator.Suit;

public class CardDeck {

	// Instance variables:
	private Card[] cardArray;
	private Random random;
	private int nextCrad;
	private int index;
	
	/**
	 * Constructor of CardDeck, nothing given.
	 */
	public CardDeck(){
		cardArray = new Card[CARD_NUMBERS];
		random = new Random();
		nextCrad = cardArray.length - 1;
		index = 0;
		fillCardsArray();
	}
	
	/**
	 * The method fills cards array.
	 */
	private void fillCardsArray(){
		saveAppropCard(Suit.CLUB);
		saveAppropCard(Suit.DIAMOND);
		saveAppropCard(Suit.HEART);
		saveAppropCard(Suit.SPADE);
	}
	
	/**
	 * The method saves Card object in the cardArray.
	 * @param suit - the parameter of card (CLUB, DIAMOND, HEART, SPADE).
	 */
	private void saveAppropCard(Suit suit){
		Nominal[] nominalsArray = Nominal.values();
		for(int i = 2; i < NOMINAL_COUNT; i++){
			cardArray[index++] = new Card(nominalsArray[i], suit);
		}
	}
	
	/**
	 * The method shuffles cards.
	 */
	public void shuffle(){
		for(int i = 0; i < SHUFFLE_COUNT; i++){
			int oneIndex = random.nextInt(CARD_NUMBERS);
			int twoIndex = random.nextInt(CARD_NUMBERS);
			swap(cardArray, oneIndex, twoIndex);
		}
	}
	
	/**
	 * The method swaps elements in given array
	 * @param arr - cards array.
	 * @param i - index one
	 * @param j - index two
	 */
	private void swap(Card[] arr, int i, int j){
		Card temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/**
	 * The method returns cards for deal.
	 * @return
	 */
	public Card getNextCrad(){
		return cardArray[nextCrad--];
	}
	
	/**
	 * The method returns true if all cards have already distributed, false otherwise.
	 * @return
	 */
	public boolean isEmpty(){
		return nextCrad == 0;
	}
	
	/**
	 * The method returns size of the card deck. The method helps to us for testing CardDeck class.
	 * @return
	 */
	public int getDeckSize(){
		return cardArray.length;
	}
	
	/**
	 * The method returns card which is on the given index. The method helps to us for testing CardDeck class.
	 * @param index - index of cardArray.
	 * @return
	 */
	public Card getAppropCard(int index){
		return cardArray[index];
	}
	
	// Constant variables:
	private static final int CARD_NUMBERS = 52;
	private static final int SHUFFLE_COUNT = 20;
	private static final int NOMINAL_COUNT = 15;
}
