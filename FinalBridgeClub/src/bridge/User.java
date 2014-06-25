package bridge;

import bridge.MySocket;
import bridge.Table.Side;

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
	private String MAIL;
	private Status STATUS;
	private String PHONE;
	private String SEX;
	private String BIRTH_DATE;
	
	private MySocket socket;
	private Table table;
	private Side side;
	
	private int userID;

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
	
	public void setMail(String mail){
		MAIL = mail;
	}
	
	public void setPhoneNumber(String number){
		PHONE = number;
	}
	
	public void setSex(String sex){
		SEX = sex;
	}
	
	public void setBirthDate(String birthDate){
		BIRTH_DATE = birthDate;
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
	
	public String getMail(){
		return MAIL;
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
	
	public void setUserID(int id){
		userID = id;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public String getPhoneNumber(){
		return PHONE;
	}
	
	public String getSex(){
		return SEX;
	}
	
	public String getBirthDate(){
		return BIRTH_DATE;
	}
	
	/**
	 * Set socket for communication to client
	 * @param socket
	 */
	public void setSocket(MySocket socket){
		this.socket=socket;
	}
	
	/**
	 * Set table at where the user is at current time
	 * @param table
	 */
	public void setTable(Table table){
		this.table = table;
	}
	
	public Table getTable(){
		return table;
	}
	
	public void setSide(Side side){
		this.side = side;
	}
	
	public Side getSide(){
		return side;
	}
	
	
	/**
	 * Send message to client
	 * @param msg
	 */
	public void notifyBrowser(String msg){
		socket.notifyBrowser(msg);
	}
	
	public void setPartOfTable(Side side){
		table.sit(this, side);
	}
	
	public void acceptNextGame(boolean accept){
		if(side!=null)
			table.playerAccepts(this, accept);
	}
	
	public void nextBid(Bid bid){
		if(side!=null)
			table.bid(this, bid);
	}
	
	public void nextCard(Card card){
		if(side!=null)
			table.card(this, card);
	}
	
	public void leaveGame(){
		if(table != null){
			if(table.getPlayer(USERNAME)==null){
				table.removeObserver(this);
			}else{
				table.leaveGame(this);
			}
		}
	}
}