package servlets;

import java.util.HashMap;

import club.User;
import club.User.Status;

public class AccountManager {
	private static HashMap<String, String> map = new HashMap<String, String>();

	
	// Return true if given name exists
	// else return false
	public static boolean nameExists(String name){
		return map.containsKey(name);
	}
	

	// Return true if given account exists
	// else return false
	public static boolean accountExists(String name, String pass){
		String password = map.get(name);
		return (password != null && password.equals(pass));
	}
	
	
	// Save name and password
	public static void createAccount(String name, String pass){
		map.put(name, pass);
	}
	
	public static User getUser(String username){
		User temp = null;
		if(nameExists(username)){
			temp = new User(username, Status.USER);
		}
		return temp;
	}
}
