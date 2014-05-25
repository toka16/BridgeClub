package game;

/**
 * Table class for managing one table.
 * It contains information about the players which sits at table, 
 * about game, contract, cards and so on,
 * and manages playing.
 * @author toka
 *
 */
public class Table {
	public static enum Side{
		EAST, SOUTH, WEST, NORTH
	}
	
	private Player[] players;
	private PlayHand cardsInPlay;
	private int ID;
	
	/**
	 * Initialize a new table.
	 */
	public Table(){
		players = new Player[4];
		cardsInPlay = new PlayHand();
	}
	
	/**
	 * Set tableID
	 * @param id
	 */
	public void setID(int id){
		ID = id;
	}
	
	/**
	 * That method should be called when user tries to sit at the table.
	 * @param player - the player who is trying to sit
	 * @param side - the side where player wants to sit
	 * @return true - if table accepts the player,
	 *         false - if the place is already taken.
	 */
	public boolean sit(Player player, Side side){
		boolean res = false;
		if(isAvaliable(side)){
			player.setTable(this);
			players[side.ordinal()] = player;
			res = true;
			for(int i=0; i<players.length; i++){
				if(players[i]!=null)
					players[i].newPlayer(player, side);
			}
			if(allPlaceTaken())
				startGame();
		}
		return res;
	}
	
	/**
	 * Check if the given side is available to sit
	 * @param s
	 * @return true - if the side is available
	 *         false - if the side is taken
	 */
	public boolean isAvaliable(Side s){
		return (players[s.ordinal()] == null);
	}
	
	/**
	 * Check if all sides are taken and table is ready to start game.
	 * @return
	 */
	public boolean allPlaceTaken(){
		for(int i=0; i<players.length; i++)
			if(players[i]==null)
				return false;
		
		System.out.println("all place taken");
		return true;
	}
	
	/**
	 * That method should be called when any player wants to leave table.
	 * @param side
	 */
	public void stand(Side side){
		players[side.ordinal()] = null;
	}
	
	/**
	 * The method is called when the table is ready to start game.
	 */
	public void startGame(){
		for(int i=0; i<players.length; i++){
			Player cur = players[i];
			cur.sendMessage("your name is "+cur.getName()+" your side is "+cur.getSide().name());
		}
		players[2].yourTurn();
	}
	
	/**
	 * That method should be called by player when he tries to make move.
	 * Method also checks if all players have made moves on that hand 
	 * and detects winner.
	 * @param player - the player who wants to make move.
	 * @param card - the card that is ought to be moved.
	 */
	public void makeMove(Player player, Card card){
		cardsInPlay.setCard(card);
		for(int i=0; i<players.length; i++){
			players[i].newMoveMade(player, card);
		}
		if(cardsInPlay.allDone()){
			Side winner = cardsInPlay.winner();
			for(int i=0; i<players.length; i++){
				players[i].winnerSide(winner);
			}
			cardsInPlay.nextHand();
			cardsInPlay.setLeader(winner);
			players[winner.ordinal()].yourTurn();
		}else{
			Side nextSide = cardsInPlay.next();
			players[nextSide.ordinal()].yourTurn();
		}
	}
	
	/**
	 * Takes string and parses it to side
	 * @param side - string associated with side
	 * @return
	 */
	public static Side parseSide(String side){
		side = side.toUpperCase();
		return Side.valueOf(side);
	}
	
	/**
	 * Return player that sits on the given side
	 * @param side
	 * @return
	 */
	public Player getPlayer(Side side){
		return players[side.ordinal()];
	}
}
