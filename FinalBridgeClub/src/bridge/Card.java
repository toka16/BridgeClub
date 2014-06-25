package bridge;

import bridge.ConvertStringToEnumerator;
import bridge.ConvertStringToEnumerator.Nominal;
import bridge.ConvertStringToEnumerator.Suit;

/**
 * Class for one card
 * @author toka
 *
 */
public class Card implements Comparable<Card>{
	
	private Nominal nom;
	private Suit suit;
	
	/**
	 * Constructor for Card. It gives Enumerators.
	 * @param nominal - Enumerator Nominal
	 * @param suit - Enumerator Suit
	 */
	public Card(Nominal nominal, Suit suit){
		nom = nominal;
		this.suit = suit;
	}
	
	/**
	 * The second constructor of Card class. It gives String objects.
	 * @param nominalStr - String object
	 * @param suitStr - String object
	 */
	public Card(String nominalStr, String suitStr){
		this(ConvertStringToEnumerator.StringToNominal(nominalStr), ConvertStringToEnumerator.StringToSuit(suitStr));
	}
	
	/**
	 * Return nominal of the card
	 * @return
	 */
	public Nominal getNominal(){
		return nom;
	}
	
	/**
	 * Return suit of the card
	 * @return
	 */
	public Suit getSuit(){
		return suit;
	}
	
	/**
	 * Compare the card to the given card according to the lead suit.
	 * @param card - card to compare
	 * @param leadSuit - lead suit
	 * @return return -1 if the given card is better
	 *                0 if cards are equal
	 *                1 otherwise
	 */
	public int compare(Card card, Suit leadSuit){
		if(this.suit.equals(card.suit)){
			if(this.nom.ordinal() > card.nom.ordinal())
				return 1;
			else if(this.nom.ordinal() < card.nom.ordinal())
				return -1;
			else 
				return 0;
		}else{
			if(this.suit.equals(leadSuit))
				return 1;
			else if(card.suit.equals(leadSuit))
				return -1;
			else
				return 1;
		}
	}
	
	/**
	 * The method compares two card. Returns true if they nominal and suit are equal, false otherwise.
	 * @param card
	 * @return
	 */
	@Override
	public boolean equals(Object cardObject){
		Card card = (Card) cardObject;
		return (this.getNominal().equals(card.getNominal()) && this.getSuit().equals(card.getSuit()));
	}
	
	/**
	 * Check if the card is valid.
	 * @return
	 */
	public boolean isValid(){
		return (nom!=null && suit!=null);
	}

	@Override
	public int compareTo(Card card) {
		return this.getNominal().ordinal() - card.getNominal().ordinal();
	}
	

}
