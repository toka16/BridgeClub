package bridge;

import java.util.ArrayList;
import java.util.List;

import bridge.ConvertStringToEnumerator.Nominal;
import bridge.Table.Side;

public class Bidding {
	
	// Instance variables:
	private List<Bid>[] bidArray;
	private int passCount;
	private Side contracter;
	private Bid contract;
	
	private Bid lastBid;
	private Side lastSide;
	
	private int gameID;
	
	/**
	 * The constructor of Bidding class.
	 */
	@SuppressWarnings("unchecked")
	public Bidding(int gameID){
		bidArray = new ArrayList[4];
		for (int i = 0; i < PLAYERS; i++) {
			bidArray[i] = new ArrayList<Bid>();
		}
		passCount = 0;
		this.gameID = gameID;
	}
	

	/**
	 * The method saves bidding.
	 * @param side - current side who made bid.
	 * @param playerBid - current bid was made by appropriate user of side.
	 */
	public void saveBid(Side side, Bid playerBid){
		if(playerBid.getBidDoubledFlag() > 0){
			playerBid.setBidNominal(lastBid.getBidNominal());
			playerBid.setBidSuit(lastBid.getBidSuit());
			passCount=0;
			contract = playerBid;
			lastBid = playerBid;
			lastSide = side;
		}else{
			if(playerBid.getBidNominal().equals(Nominal.PASS)){
				passCount += 1;
			}else{
				lastBid = playerBid;
				lastSide = side;
				contract = playerBid;
				contracter = side;
				passCount = 0;
			}
		}
		
		int sideIndex = side.ordinal();
		bidArray[sideIndex].add(playerBid);
		SaverInDB.saveBidInDatabase(gameID, playerBid); // saves in database
	}

	
	public Bid getLastBid(){
		return lastBid;
	}
	
	public Side getLastSide(){
		return lastSide;
	}
	
	/**
	 * The method return boolean variable, which indicates whether bidding end or not.
	 * @return true - if bidding is over.
	 * 		   false - otherwise.
	 */
	public boolean biddingIsOver(){
		if((contract!=null && passCount == 3) || passCount == 4)
			return true;
		return false;
	}
	

	/**
	 * The method returns side which made last bid.
	 * @return
	 */
	public Side getContracter(){
		return contracter;
	}
	
	/**
	 * The method returns last bid.
	 * @return
	 */
	public Bid getContract(){
		return contract;
	}
	
	/**
	 * The method returns set of arrayList according to appropriate side.
	 * We will need a sequence of data and will not be useful to return a iterator.
	 * @param side
	 * @return
	 */
	public List<Bid> getSideBiddingSet(Side side){
		int sideIndex = side.ordinal();
		List<Bid> sideArrayList = bidArray[sideIndex];
		return sideArrayList;
	}
	
	// The constant variables:
	public static final int PLAYERS = 4;
}
