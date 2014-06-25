package bridge;

import java.util.ArrayList;

/**
 * Table class for managing one table.
 * It contains information about the players which sits at table, 
 * about game, contract, cards and so on,
 * and manages playing.
 * @author toka
 */
public class Table {
	public static enum Side{
		NORTH, EAST, SOUTH, WEST;
		
		public Side nextSide(){
			int index = (this.ordinal()+1)%4;
			return Side.values()[index];
		}
		
		public Side previousSide(){
			int index = (this.ordinal()+3)%4;
			return Side.values()[index];
		}
		
		public boolean isOposite(Side side){
			return !(this.ordinal() == side.ordinal() || this.ordinal() == (side.ordinal()+2)%4);
		}
	}
	
	public static enum PlayingStage{
		WaitForDeal, WaitForAccept, Bidding, BiddingOver, Playing, PlayingOver
	}
	
	public static enum Vulnerable{
		NOLINE, NS, EW, BOTHLINE
	}
	
	private int ID;
	private PartOfTable[] parts;
	private PlayHand cardsInPlay;
	private Bidding bidding;
	private PlayingStage currentStage;
	private PlayingDeal playingDeal;
	private JsonCreator jCreator;
	private ArrayList<User> observers;
	private Side currentSide;
	
	/**
	 * Initialize a new table.
	 */
	public Table(){
		parts = new PartOfTable[4];
		observers = new ArrayList<User>();
		jCreator = new JsonCreator(this);
		currentStage = PlayingStage.WaitForDeal;
	}
	
	/**
	 * Set tableID
	 * @param id
	 */
	public void setID(int id){
		ID = id;
	}
	
	/**
	 * Return tableID
	 * @return
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * That method should be called when user tries to sit at the table.
	 * @param player - the player who is trying to sit
	 * @param side - the side where player wants to sit
	 * @return true - if table accepts the player,
	 *         false - if the place is already taken.
	 */
	public boolean sit(User user, Side side){
		boolean res = false;
		if(isAvaliable(side) && user.getSide()==null){
			removeObserver(user);
			parts[side.ordinal()] = new PartOfTable(user, this);
			user.setSide(side);
			res = true;
			notifyAllUser();
			if(allPlaceTaken()){
				currentStage = PlayingStage.WaitForAccept;
				getAcceptsFromPlayers();
			}
		}
		return res;
	}
	
	
	public void describeTableForPlayer(User user){
		user.notifyBrowser(jCreator.tableDescriptionForSide(user.getSide()));
	}
	
	
	public void addObserver(User user){
		user.notifyBrowser(jCreator.tableDescription());
		observers.add(user);
	}
	
	public void removeObserver(User user){
		observers.remove(user);
	}
	
	public User getPlayer(String username){
		for(int i=0; i<parts.length; i++){
			if(parts[i]!=null && parts[i].getUser().getUsername().equals(username))
				return parts[i].getUser();
		}
		return null;
	}

	
	/**
	 * Check if the given side is available to sit
	 * @param s
	 * @return true - if the side is available
	 *         false - if the side is taken
	 */
	public boolean isAvaliable(Side s){
		return (parts[s.ordinal()] == null);
	}
	
	/**
	 * Check if all sides are taken and table is ready to start game.
	 * @return
	 */
	private boolean allPlaceTaken(){
		for(int i=0; i<parts.length; i++)
			if(parts[i]==null)
				return false;
		
		return true;
	}
	
	
	/**
	 * The method is called when the table is ready to start game.
	 */
	private void startGame(){
		currentStage = PlayingStage.Bidding;
		CardDeck deck = new CardDeck();
		playingDeal = new PlayingDeal(deck);
		ID = playingDeal.getGameID();
		fillUsersInPlayingDeal();
		deal();
		bidding = playingDeal.getBidding();
		currentSide = playingDeal.getDeal().getDealer();
		parts[currentSide.ordinal()].getTimer().start();
		notifyAllUser();
	}
	
	private void fillUsersInPlayingDeal(){
		for(int i=0; i<parts.length; i++){
			playingDeal.setUser(parts[i].getUser(), parts[i].getUser().getSide());
		}
	}

	private boolean[] acceptedPlayers;
	public void getAcceptsFromPlayers(){
		acceptedPlayers = new boolean[parts.length];
		for(int i=0; i<acceptedPlayers.length; i++)
			acceptedPlayers[i] = false;
		notifyAllUser();
	}
	
	public void playerAccepts(User user, boolean accept){
		if(accept){
			acceptedPlayers[user.getSide().ordinal()] = true;
			notifyAllUser();
			if(allAccepted())
				startGame();
		}else{
			leaveGame(user);
		}
	}
	
	private boolean allAccepted(){
		for(int i=0; i<acceptedPlayers.length; i++){
			if(!acceptedPlayers[i])
				return false;
		}
		return true;
	}
	
	public boolean playerAccepted(Side side){
		return acceptedPlayers[side.ordinal()];
	}
	
	public void leaveGame(User user){
		parts[user.getSide().ordinal()] = null;
		addObserver(user);
		if(currentStage.ordinal() >= PlayingStage.Bidding.ordinal() && 
				currentStage.ordinal() < PlayingStage.PlayingOver.ordinal()){
			currentStage = PlayingStage.PlayingOver;
		}else{
			currentStage = PlayingStage.WaitForDeal;
		}
		notifyAllUser();
	}
	
	public void bid(User user, Bid bid){
		parts[currentSide.ordinal()].getTimer().stop();
		bidding.saveBid(currentSide, bid);
		currentSide = currentSide.nextSide();
		if(bidding.biddingIsOver()){
			Side declarer = bidding.getContracter();
			if(declarer == null){
				System.out.println("        declarer is null, stage: PlayingOver");
				currentStage = PlayingStage.PlayingOver;
				playingDeal.setPointPlus(0);
				playingDeal.countImp();
				getAcceptsFromPlayers();
			}else{
				System.out.println("            declarer is not null, stage: BiddingOver");
				currentSide = declarer.nextSide();
				currentStage = PlayingStage.BiddingOver;
				currentSide = bidding.getContracter().nextSide();
				playingDeal.biddingIsOver();
				cardsInPlay = new PlayHand(currentSide, bidding.getContract().getBidSuit(), ID);
			}
		}
		parts[currentSide.ordinal()].getTimer().start();
		notifyAllUser();
	}
	
	public void card(User user, Card card){
		if(cardsInPlay.allDone())
			cardsInPlay = new PlayHand(currentSide, bidding.getContract().getBidSuit(), ID);
		if(currentStage.equals(PlayingStage.BiddingOver))
			currentStage = PlayingStage.Playing;
		
		parts[currentSide.ordinal()].getTimer().stop();;
		cardsInPlay.setCard(currentSide, card);
		parts[currentSide.ordinal()].removeCard(card);
		if(cardsInPlay.allDone()){
			playingDeal.addPlayHand(cardsInPlay);
			currentSide = cardsInPlay.getWinner();
			incrementWinnersHand(currentSide);
			if(playingDeal.isOver()){
				gameIsOver();
				return;
			}
		}else{
			currentSide = currentSide.nextSide();
		}
		parts[currentSide.ordinal()].getTimer().start();
		notifyAllUser();
	}
	
	private void gameIsOver(){
		currentStage = PlayingStage.PlayingOver;
		parts[currentSide.ordinal()].getTimer().stop();
		playingDeal.countScores();
		getAcceptsFromPlayers();
	}
	
	private void incrementWinnersHand(Side winner){
		Side leader = bidding.getContracter();
		if(leader.isOposite(winner)){
			playingDeal.incrementLostHand();
		}else{
			playingDeal.incrementTakenHand();
		}
	}
	
	public void deal(){
		Side s = Side.NORTH;
		for(int i=0; i<4; i++, s = s.nextSide()){
			parts[s.ordinal()].setAllCard(playingDeal.getSideCards(s));
		}
	}
	
	public PlayingStage getStage(){
		return currentStage;
	}
	
	public PartOfTable getPart(Side side){
		return parts[side.ordinal()];
	}
	
	public PlayHand getPlayHand(){
		return cardsInPlay;
	}
	
	public PlayingDeal getPlayingDeal(){
		return playingDeal;
	}
	
	public Bidding getBidding(){
		return bidding;
	}
	
	public Side getCurrentSide(){
		return currentSide;
	}
	
	private void notifyObservers(){
		String msg = jCreator.tableDescription();
		for(int i=0; i<observers.size(); i++)
			observers.get(i).notifyBrowser(msg);
	}
	
	
	private void notifyEachPlayer(){
		for(int i=0; i<parts.length; i++){
			if(parts[i]!=null){
				Side s = parts[i].getUser().getSide();
				String msg = jCreator.tableDescriptionForSide(s);
				parts[i].getUser().notifyBrowser(msg);
			}
		}
	}
	
	private void notifyAllUser(){
		notifyObservers();
		notifyEachPlayer();
	}
}
