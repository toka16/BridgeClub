package club;

import game.Card;
import game.MySocket;
import game.Player;
import game.Table.Side;

/**
 * User class that contains information about the user 
 * which is currently on site
 * @author toka
 *
 */
public class User {
	public static enum Status{
		GUEST, USER, ADMINISTRATOR
	}
	
	private String USERNAME;
	private String FIRST_NAME;
	private String LAST_NAME;
	private Status STATUS;
	private MySocket socket;

	/**
	 * Constructor for User
	 * @param username
	 * @param st - status of the user should be administrator, user or guest
	 */
	public User(String username, Status st){
		this.USERNAME = username;
		this.STATUS = st;
	}
	
	/**
	 * Set users firstName
	 * @param name
	 */
	public void setFirstName(String name){
		FIRST_NAME = name;
	}
	
	/**
	 * Set users lastName
	 * @param lName
	 */
	public void setLastName(String lName){
		LAST_NAME = lName;
	}
	
	/**
	 * Return users firstName
	 * @return
	 */
	public String getFirstName(){
		return FIRST_NAME;
	}
	
	/**
	 * Return users lastName
	 * @return
	 */
	public String getLastName(){
		return LAST_NAME;
	}
	
	/**
	 * Return users username
	 * @return
	 */
	public String getUsername(){
		return USERNAME;
	}
	
	/**
	 * Return users status
	 * @return
	 */
	public Status getStatus(){
		return STATUS;
	}
	
	/**
	 * Set socket for communication to client
	 * @param socket
	 */
	public void setSocket(MySocket socket){
		this.socket=socket;
	}
	
	/**
	 * Send message to client
	 * @param msg
	 */
	public void sendMessage(String msg){
		socket.sendMsg(msg);
	}

	/**
	 * Tell client about new move on the table
	 * @param player
	 * @param card
	 */
	public void newMoveMade(Player player, Card card) {
		socket.moveIsMaden(player, card);
	}
	
	/**
	 * Tell client about winner side on the table
	 * @param winner
	 */
	public void winnerSide(Side winner){
		socket.winnerSide(winner);
	}
	
	
	/**
	 * Tell client that it is his/her turn
	 */
	public void yourTurn(){
		socket.yourTurn();
	}
	
	/**
	 * Tell client about new player on the table
	 * @param player
	 * @param side
	 */
	public void newPlayerAdded(Player player, Side side){
		socket.newPlayerAdded(player, side);
	}
	
	/**
	 * Tell client that someone has left the game
	 * @param player
	 */
	public void playerLeavesGame(Player player){
		sendMessage("player "+player.getName() +" on side "+player.getSide()+" leaves game");
	}
	
	/**
	 * Tell client that the game has stopped
	 */
	public void gameStopped(){
		sendMessage("game stopped");
	}
}
