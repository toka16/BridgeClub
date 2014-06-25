package bridge;

import bridge.ConvertStringToEnumerator.Nominal;
import bridge.ConvertStringToEnumerator.Suit;

public class Bid {
	
	// Instance variables:
	private Nominal nominal;
	private Suit suit;
	private int doubledFlag;
	
	// The constructor Bid class.
	public Bid(Nominal nominal, Suit suit, String doubledFlag){
		this.nominal = nominal;
		this.suit = suit;
		this.doubledFlag = Integer.parseInt(doubledFlag);
	}
	
	public void setBidNominal(Nominal n){
		nominal = n;
	}
	
	public void setBidSuit(Suit s){
		suit = s;
	}
	
	// The method returns bid nominal.
	public Nominal getBidNominal(){
		return nominal;
	}
	
	// The method returns bid suit.
	public Suit getBidSuit(){
		return suit;
	}
	
	// The method returns 1 if it is contra, 2 - re-contra, 0 - no contra
	public int getBidDoubledFlag(){
		return doubledFlag;
	}
	
	public boolean isValid(){
		return (nominal!=null && suit!=null);
	}
}
