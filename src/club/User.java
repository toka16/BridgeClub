package club;

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
}
