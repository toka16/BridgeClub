package bridge;

public abstract class ConvertStringToEnumerator {
	public static enum Nominal{
		PASS, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, 
		JACK, QUIN, KING, ACE
	}
	public static enum Suit{
		CLUB, DIAMOND, HEART, SPADE, NOTRUMP
	}
	
	/**
	 * Parse nominal from string
	 * @param nominal
	 * @return nominal associated with the given string
	 */
	public static Nominal StringToNominal(String nominal){
		nominal = nominal.toUpperCase();
		Nominal res;
		try{
			res = Nominal.valueOf(nominal);
		}catch(Exception e){
			res = null;
		}
		return res;
	}
	
	/**
	 * Parse suit from string
	 * @param suit
	 * @return suit associated with the given string
	 */
	public static Suit StringToSuit(String suit){
		suit = suit.toUpperCase();
		Suit res;
		try{
			res = Suit.valueOf(suit);
		}catch(Exception e){
			res = null;
		}
		return res;
	}
		
//	/**
//	 * Parse card with the given two strings
//	 * @param nominal
//	 * @param suit
//	 * @return card associate with the given strings
//	 */
//	public static Card stringsToCard(String nominal, String suit){
//		return new Card(parseNominal(nominal), parseSuit(suit));
//	}
//	
//	/**
//	 * Parse bid with the given two strings
//	 * @param nominal
//	 * @param suit
//	 * @return
//	 */
//	public static Bid stringsToBid(String nominal, String suit){
//		return new Bid(parseNominal(nominal), parseSuit(suit));
//	}
}
