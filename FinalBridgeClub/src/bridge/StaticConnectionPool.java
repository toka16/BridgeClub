package bridge;

public class StaticConnectionPool {
	
	// Constant variables:
	private static final String SERVER_URL = DBInfo.DB_SERVER_URL;
	private static final String USER_NAME = DBInfo.DB_USER_NAME;
	private static final String PASSWORD = DBInfo.DB_PASSWORD;
	
	// Instance variables:
	private static ConnectionPool connectionPool = new ConnectionPool(SERVER_URL, USER_NAME, PASSWORD);
	
	/**
	 * The method returns connectionPool object.
	 * @return
	 */
	public static ConnectionPool getConnectionPoolOjbect(){
		return connectionPool;
	}
}
