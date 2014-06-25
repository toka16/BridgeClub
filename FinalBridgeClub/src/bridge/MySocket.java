package bridge;


import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import bridge.Card;
import bridge.ConvertStringToEnumerator.Nominal;
import bridge.ConvertStringToEnumerator.Suit;
import bridge.Table.Side;
import bridge.User.Status;


/**
 * WebSocket class for sending/getting information to/from clients.
 * Each MySocket class interacts only with one client.
 * @author toka
 *
 */
@ServerEndpoint(value = "/OurSocket", configurator = MyServerConfigurator.class)
public class MySocket {
	private Session session;
	private HttpSession httpSession;
	private User user;
		
	
	/**
	 * Method that is called when client connects with server.
	 * @param s - session associated with the client.
	 */
	@OnOpen
	public void OnOpen(Session s, EndpointConfig config){
		session = s;
		httpSession = (HttpSession)config.getUserProperties().get("httpsession");
		user = (User)httpSession.getAttribute("user");
		user.setSocket(this);
		
		if(user.getTable().getPlayer(user.getUsername())==null)
			user.getTable().addObserver(user);
		else
			user.getTable().describeTableForPlayer(user);
		
//		System.out.println(Thread.currentThread().getName());
	}
	
	/**
	 * Method that is called when client sends any information to server.
	 * This method parses the information and acts according to it.
	 * @param msg - information
	 */
	@OnMessage
	public void OnMessage(String msg){
		if(!user.getStatus().equals(Status.GUEST)){
			System.out.println(msg);
			if(msg.startsWith("arrow")){
				String side = msg.substring(6);
				Side s;
				if(side.equals("top")){
					s=Side.NORTH;
				}else if(side.equals("right")){
					s=Side.EAST;
				}else if(side.equals("bottom")){
					s=Side.SOUTH;
				}else{
					s=Side.WEST;
				}
				if(!user.getTable().sit(user, s)){
					user.notifyBrowser("yout can't sit here");
				}
			}else if(msg.startsWith("card")){
				String cardString = msg.substring(5);
				int devider;
				try{
					devider = cardString.indexOf(":");
				}catch(Exception e){
					notifyBrowser("illegal card");
					return;
				}
				if(devider<0){
					notifyBrowser("illegal card");
					return;
				}
				String suit = cardString.substring(0, devider);
				String nominal = cardString.substring(devider+1);
				int suitIndex = Integer.parseInt(suit);
				int nomIndex = Integer.parseInt(nominal);
				Nominal n = Nominal.values()[nomIndex];
				Suit s = Suit.values()[suitIndex];
				Card c = new Card(n , s);
				if(c.isValid() && user!=null)
					user.nextCard(c);
			}else if(msg.startsWith("bid")){
				String bidString = msg.substring(4);
				int devider1;
				int devider2;
				try{
					devider1 = bidString.indexOf(":");
					devider2 = bidString.indexOf(":", devider1+1);
				}catch(Exception e){
					notifyBrowser("illegal bid");
					return;
				}
				if(devider1<0 || devider2<0){
					notifyBrowser("illegal bid");
					return;
				}
				String suit = bidString.substring(0, devider1);
				String nominal = bidString.substring(devider1+1, devider2);
				String doubleString = bidString.substring(devider2+1);
				int suitIndex = Integer.parseInt(suit);
				int nomIndex = Integer.parseInt(nominal);
				Nominal n = Nominal.values()[nomIndex];
				Suit s = Suit.values()[suitIndex];
				Bid bid = new Bid(n, s, doubleString);
				if(bid.isValid())
					user.nextBid(bid);
				else
					notifyBrowser("you cant make that bid");
			}else if(msg.startsWith("playAgain")){
				String again = msg.substring(10);
				user.acceptNextGame(again.equals("1"));
			}
		}
	}
	
	@OnClose
	public void OnClose(Session s){
		if(user.getTable()!=null)
			user.getTable().removeObserver(user);
		else
			System.out.println("table is null");
	}
	
	
	/**
	 * Send information to browser.
	 * @param msg - information that should be sent to browser.
	 */
	public void notifyBrowser(String msg){
		try {
			if(session != null && session.isOpen())
				session.getBasicRemote().sendText(msg);
			else
				System.out.println("cant send msg");
		} catch (Exception ignore) {
			System.out.println("excepton while sending data");
		}
	}	

}
