package bridge;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import bridge.Table.Side;
import bridge.Table.Vulnerable;

public class Deal {
	private static final int NUMBER_OF_CARDS_PER_SIDE = 13;
	private static final int NUMBER_OF_SIDES = 4;
	
	private int ID;
	private int numOfDeal;
	private Side dealer;
	private Vulnerable vulnerable;
	private ArrayList<Card>[] cards;
	
	@SuppressWarnings("unchecked")
	public Deal(CardDeck deck){
		Random r = new Random();
		numOfDeal = r.nextInt(16)+1;
		ID = SaverInDB.saveDeal(numOfDeal);
		Map<String, Object> sideVulnerable = GetterFromDB.getVulnerable(numOfDeal);
		dealer = (Side)sideVulnerable.get("dealer");
		vulnerable = (Vulnerable)sideVulnerable.get("vulnerable");
		cards = new ArrayList[NUMBER_OF_SIDES];
		initCards();
		deck.shuffle();
		fillCards(deck);
	}
	
	private void initCards(){
		for(int i=0; i<cards.length; i++){
			cards[i] = new ArrayList<Card>();
		}
	}
	
	public int getID(){
		return ID;
	}
	
	private void fillCards(CardDeck deck){
		for(int i=0; i<NUMBER_OF_CARDS_PER_SIDE; i++){
			for(int j=0; j<NUMBER_OF_SIDES; j++){
				cards[j].add(deck.getNextCrad());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Card> getSideCards(Side side){
		return (ArrayList<Card>)cards[side.ordinal()].clone();
	}
	
	public Side getDealer(){
		return dealer;
	}
	
	public int getDealNumber(){
		return numOfDeal;
	}
	
	public Vulnerable getVulnerable(){
		return vulnerable;
	}
}
