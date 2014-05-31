package game;

import game.Table.Side;

import java.util.ArrayList;

import club.User;

/**
 * Player that is sitting at table
 * @author toka
 *
 */
public class Player {
	private CardList cards;
	private Side side;
	private Table table;
	private boolean myTurn;
	private User user;
	
	/**
	 * Constructor for player
	 * @param name - player name
	 * @param s - side where player sits
	 */
	public Player(User user, Side s){
		side = s;
		cards = new CardList();
		table = null;
		myTurn = false;
		this.user = user;
	}
	
	/**
	 * Add card to the players card list
	 * @param c
	 */
	public void addCard(Card c){
		cards.add(c);
	}
	
	/**
	 * Return all cards that player has
	 * @return
	 */
	public ArrayList<Card> getCards(){
		return cards.getAll();
	}
	
	/**
	 * Check if the player has the given card
	 * @param c
	 * @return
	 */
	public boolean hasCard(Card c){
		return cards.hasCard(c);
	}
	
	/**
	 * Return players name
	 * @return
	 */
	public String getName(){
		return user.getUsername();
	}
	
	/**
	 * Return the side where player sits
	 * @return
	 */
	public Side getSide(){
		return side;
	}
	
	/**
	 * Remove card from players card list
	 * @param c
	 */
	public void removeCard(Card c){
		cards.remove(c);
	}
	
	/**
	 * Set reference to the table
	 * @param t
	 */
	public void setTable(Table t){
		table = t;
	}
	
	
	/**
	 * That method is called by client using WebSocket when client wants
	 * to make a move. Method should check if the move is valid and
	 * act according it.
	 * @param card
	 */
	public void makeMove(Card card){
		//if(cards.hasCard(card)){
		if(myTurn){
			table.makeMove(this, card);
			myTurn = false;
		}else{
			sendMessage("not your turn");
		}
	}
	
	/**
	 * That method should be called by table when any player makes move
	 * @param player
	 * @param card
	 */
	public void newMoveMade(Player player, Card card){
		user.newMoveMade(player, card);
	}
	
	/**
	 * That method should be called by table when the hand winner is detected
	 * @param winner
	 */
	public void winnerSide(Side winner){
		user.winnerSide(winner);
	}
	
	/**
	 * That method should be called by table to say the client
	 * that it is his turn
	 */
	public void yourTurn(){
		myTurn = true;
		user.yourTurn();
	}
	
	/**
	 * That method should be called by table when new player sits at it
	 * @param player
	 * @param side
	 */
	public void newPlayer(Player player, Side side){
		user.newPlayerAdded(player, side);
	}
	
	/**
	 * Send information to user
	 * @param msg
	 */
	public void sendMessage(String msg){
		user.sendMessage(msg);
	}
	
	/**
	 * That method should be called by table when any player leaves table.
	 * @param player - player who has left the table
	 */
	public void playerLeavesGame(Player player){
		user.playerLeavesGame(player);
	}
	
	/**
	 * That method should be called by user when he/she wants to leave game.
	 */
	public void leaveGame(){
		if(table!=null)
			table.stand(this);
	}
	
	/**
	 * That method should be called when game stops
	 */
	public void gameStopped(){
		myTurn = false;
		user.gameStopped();
	}
}
