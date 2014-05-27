package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import club.User;

/**
 * This class contains information about current state of the club.
 * Tables where sits at list one player and users that are on site.
 * @author toka
 *
 */
public class AllInfo {
	private static List<Table> list = new ArrayList<Table>();
	private static HashMap<String, User> map = new HashMap<String, User>();
	
	private static Table t = new Table(); // droebiti magida testirebistvis
	
	public static synchronized void addTable(Table t){
		int id = list.size();
		t.setID(id);
		list.add(t);
	}
	
	public static synchronized Table getTableAt(int index){
//		Table res = null;
//		if(index >= 0 && index < list.size())
//			res = list.get(index);
		return t;
	}
	
	public static synchronized void removeTableAt(int index){
		if(index >= 0 && index < list.size())
			list.remove(index);
	}
	
	public static synchronized void addUser(User u){
		map.put(u.getUsername(), u);
	}
	
	public static synchronized User getUser(String username){
		return map.get(username);
	}
	
}
