package game;

import game.Table.Side;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import servlets.AccountManager;
import club.User;

/**
 * WebSocket class for sending/getting information to/from clients.
 * Each MySocket class interacts only with one client.
 * @author toka
 *
 */
@ServerEndpoint("/tableSocket")
public class MySocket {
	private Session session;
	private User user;
	private Player player;
	private Table table;
		
	
	/**
	 * Method that is called when client connects with server.
	 * @param s - session associated with the client.
	 */
	@OnOpen
	public void OnOpen(Session s){
		session = s;
		table = AllInfo.getTableAt(0);
		System.out.println("new user added");
	}
	
	/**
	 * Method that is called when client sends any information to server.
	 * This method parses the information and acts according with it.
	 * @param msg - information
	 */
	@OnMessage
	public void OnMessage(String msg){
		System.out.println("new Message: "+msg);
		if(msg.startsWith("place")){
			String side = msg.substring(6);
			Side s = Table.parseSide(side);
			if(table.isAvaliable(s)){
				player = new Player(user, s);
				table.sit(player, s);
			}else{
				sendMsg(side+" side is already taken");
			}
		}else if(msg.startsWith("init")){
			String name = msg.substring(5);
			if(!name.equals("null")){
				user = AccountManager.getUser(name);
				user.setSocket(this);
				sendMsg("your name is "+user.getUsername()+" and you can sit at table "+table.getID());
			}
		}else if(msg.startsWith("move")){
			String cardString = msg.substring(5);
			int devider;
			try{
				devider = cardString.indexOf(":");
			}catch(Exception e){
				sendMsg("illegal card");
				return;
			}
			if(devider<0){
				sendMsg("illegal card");
				return;
			}
			String nominal = cardString.substring(0, devider);
			String suit = cardString.substring(devider+1);
			Card c = Card.parse(nominal, suit);
			if(c.isValid())
				if(player!=null)
					player.makeMove(c);
				else
					sendMsg("you cant move card, you are not sitting at a table");
			else
				sendMsg("card "+cardString+" doesn't exist");
		}
		
		
/*		Gson gson = new Gson();
		JsonObject json = gson.fromJson(msg, JsonObject.class);
		System.out.println("json created:");
		System.out.println(json.getAsString());
		JsonElement element = json.get("request");
		String el = element.getAsString();
		if(el.equals("authenticate")){
			System.out.println("authenticate request taken");
//			user = AllInfo.getUser(json.get("username").getAsString());
			user = new User("myname", Status.USER);
		}else if(el.equals("sit")){
			int tableID = json.get("tableID").getAsInt();
			
			Table t = new Table();
			AllInfo.addTable(t);
			table = AllInfo.getTableAt(0); //////////////////(tableID)
			if(table!=null && user != null){
				String sideString = json.get("side").getAsString();
				Side side = Table.parseSide(sideString);
				boolean p = table.sit(user, side);
				player = table.getPlayer(side);
				player.setSocket(this);
				JsonObject responce = new JsonObject();
				if(p){
					player = table.getPlayer(side);
					player.setSocket(this);
					
					responce.addProperty("responce", "successful");
				}else{
					responce.addProperty("responce", "taken");
				}
				sendMsg(responce.getAsString());
			}
		}else if(el.equals("move")){
			String nominalString = json.get("nominal").getAsString();
			String suitString = json.get("suit").getAsString();
			Card card = Card.parse(nominalString, suitString);
			player.makeMove(card);
		}*/
		
		
	}
	
	/**
	 * method that is called when connection is closed.
	 * @param s - session associated with client
	 */
	@OnClose
	public void OnClose(Session s){
		if(player!=null){
			player.leaveGame();
		}
	}
	
	
	/**
	 * Send information to client.
	 * @param msg - information that should be sent to client.
	 */
	public void sendMsg(String msg){
		try {
			if(session != null && session.isOpen())
				session.getBasicRemote().sendText(msg);
		} catch (Exception ignore) {}
	}

	/**
	 * That method should be call when any player makes move.
	 * @param player - the player which have made the move.
	 * @param card - the card that was moved.
	 */
	public void moveIsMaden(Player player, Card card){
//		JsonObject responce = new JsonObject();
//		responce.addProperty("responce", "move");
//		responce.addProperty("side", player.getSide().name());
//		responce.addProperty("nominal", card.getNominal().name());
//		responce.addProperty("suit", card.getSuit().name());
//		sendMsg(responce.getAsString());
		sendMsg("player "+player.getName()+" moved card "+card.getNominal().name()+card.getSuit().name());
	}
	
	
	/**
	 * That method should be called when one hand is played and 
	 * the winner side is detected.
	 * @param winner - the side that has won the hand.
	 */
	public void winnerSide(Side winner){
//		JsonObject responce = new JsonObject();
//		responce.addProperty("responce", "winner");
//		responce.addProperty("side", winner.name());
//		sendMsg(responce.getAsString());
		sendMsg("winner is "+winner.name());
	}
	
	/**
	 * That method should be called by table to say the client
	 * that it is his/her turn to make move.
	 */
	public void yourTurn(){
//		JsonObject responce = new JsonObject();
//		responce.addProperty("responce", "makeTurn");
		sendMsg("your turn");
	}
	
	/**
	 * That method should be called when new player sits at table.
	 * @param player - the player, who has sit at table.
	 * @param side - the side on which the player sits.
	 */
	public void newPlayerAdded(Player player, Side side){
//		JsonObject responce = new JsonObject();
//		responce.addProperty("responce", "newPlayer");
//		responce.addProperty("username", player.getName());
//		responce.addProperty("side", side.name());
		sendMsg("player "+player.getName()+" sit on "+side.name());
	}
	
	
}
