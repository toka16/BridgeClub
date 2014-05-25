package game;

/**
 * Class for one card
 * @author toka
 *
 */
public class Card {
	public static enum Nominal{
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, 
		JACK, QUIN, KING, ACE
	}
	public static enum Suit{
		CLUB, DIAMOND, HEART, SPADE, NOTRUMP
	}
	
	private Nominal nom;
	private Suit suit;
	
	/**
	 * Constructor for Card
	 * @param nominal
	 * @param suit
	 */
	public Card(Nominal nominal, Suit suit){
		nom = nominal;
		this.suit = suit;
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
	 * Parse nominal from string
	 * @param nominal
	 * @return nominal associated with the given string
	 */
	private static Nominal parseNominal(String nominal){
		nominal = nominal.toUpperCase();
		return Nominal.valueOf(nominal);
	}
	
	/**
	 * Parse suit from string
	 * @param suit
	 * @return suit associated with the given string
	 */
	private static Suit parseSuit(String suit){
		suit = suit.toUpperCase();
		return Suit.valueOf(suit);
	}
	
	/**
	 * Parse card with the given two strings
	 * @param nominal
	 * @param suit
	 * @return card associate with the given strings
	 */
	public static Card parse(String nominal, String suit){
		return new Card(parseNominal(nominal), parseSuit(suit));
	}
	
	/**
	 * Check if the card is valid.
	 * @return
	 */
	public boolean isValid(){
		return (nom!=null && suit!=null);
	}
}
