package bridge;

import java.util.ArrayList;

import bridge.Table.Side;

public class PlayingDeal {
	private Deal deal;
	private User[] players;
	private Bidding bidding;
	private ArrayList<PlayHand> hands;
	private int takenHand;
	private int lostHand;
	private int pointPlus;
	private int compensation;
	private int imp;
	
	private int gameID;
	
	public PlayingDeal(CardDeck deck){
		deal = new Deal(deck);
		players = new User[4];
		hands = new ArrayList<PlayHand>();
		takenHand = lostHand = pointPlus = compensation = imp = 0;
		gameID = SaverInDB.saveGameStartingData(deal.getID());
		Side s = Side.NORTH;
		for(int i=0; i<4; i++, s = s.nextSide()){
			SaverInDB.saveDealCards(deal.getID(), s, deal.getSideCards(s));
		}
		SaverInDB.saveCompensationPointToDeal(deal.getID());
		compensation = GetterFromDB.getCompensationPointFromDeal(gameID);
		bidding = new Bidding(gameID);
	}
	
	public void setUser(User user, Side side){
		players[side.ordinal()] = user;
		SaverInDB.savesDealGamesPlayers(gameID, side, user.getUserID());
	}
	
	public void setBidding(Bidding bidding){
		this.bidding = bidding;
	}
	
	public void setPointPlus(int points){
		pointPlus = points;
	}
	
	public void setCompensation(int compensation){
		this.compensation = compensation;
	}
	
	public void setImp(int imp){
		this.imp = imp;
	}
	
	public void addPlayHand(PlayHand hand){
		hands.add(hand);
	}
	
	public void incrementTakenHand(){
		takenHand++;
	}
	
	public void incrementLostHand(){
		lostHand++;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public int getTakenHand(){
		return takenHand;
	}
	
	public int getLostHand(){
		return lostHand;
	}
	
	public int getPointPlus(){
		return pointPlus;
	}
	
	public int getCompensation(){
		return compensation;
	}
	
	public int getImps(){
		return imp;
	}
	
	public ArrayList<Card> getSideCards(Side side){
		return deal.getSideCards(side);
	}
	
	public Deal getDeal(){
		return deal;
	}
	
	public Bidding getBidding(){
		return bidding;
	}
	
	public boolean isOver(){
		return (hands.size()>12);
	}
	
	public void countPointPlus(){
		pointPlus = SaverInDB.saveGameDataAfterEndGame(takenHand, gameID);
	}
	
	public void countImp(){
		imp = GetterFromDB.getImpScore(pointPlus-compensation);
	}
	
	public void countScores(){
		countPointPlus();
		countImp();
	}
	
	public void biddingIsOver(){
		SaverInDB.saveGameDataAfterEndBidding(gameID, bidding.getContracter());
	}
	
	public int getCompensationScore(){
		return compensation;
	}
}
