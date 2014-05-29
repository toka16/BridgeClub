package game;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;


public class DBConnectionPool implements Runnable {

	/* The address of database. */
	private String fullAddressServer;
	
	// The password of database.
	private String passwordStr;
	
	// The name of databse user name.
	private String userName;
	
	/* The list of available connections. */
	private List<Connection> availableConnectionList;
	 
	/* The list of used connection. */
	private List<Connection> usedConnectionList;
	
	// The thread provides to clear unused threads. The target of this thread is that it doesn't cause many unused connections.
	private Thread clearThread;
	
	// The constructor of ConnectionPool class.
	public DBConnectionPool(String addressDB, String user, String password){
		availableConnectionList = new ArrayList<Connection>();
		usedConnectionList = new ArrayList<Connection>();

		fullAddressServer = addressDB;
		userName = user;
		passwordStr = password;

		fillsAvailList();
		clearThread = new Thread(this);
		clearThread.start();
	}
	
	
	// The method fills list of available connections.
	private void fillsAvailList(){
		for(int i = 0; i < CONNECTIONS_COUNT; i++){
			Connection con = getNewConnection();
			availableConnectionList.add(con);
		}
	}
	
	// The method returns new connection.
	private Connection getNewConnection(){
		Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(fullAddressServer, userName, passwordStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	// The method returns connections quantity.
	public int getAvailableConnectionCount(){
		return availableConnectionList.size();
	}
	
	// The method choice a connection
	public synchronized Connection choiceConnection() throws SQLException {
		Connection con = null;
		if(availableConnectionList.size() == 0){ // if no more available connections, creates new.
			con = getNewConnection();
			usedConnectionList.add(con);
		}
		else{ // if exist available connection use it.
			int lastIndex = availableConnectionList.size() - 1;
			con = availableConnectionList.get(lastIndex);
			availableConnectionList.remove(lastIndex);
		// sheidzleba connection-i daxuros iuzerma gamoyenebis shemdeg, unchoiceConnection metodis dros davamatebt daxulul connection-s.
			if(con.isClosed())
				con = getNewConnection();
			usedConnectionList.add(con);
		}
		return con;
	}
	
	// The method unchoice unused connection.
	public synchronized void unchoiceConnection(Connection conct){
		if(conct != null){
			availableConnectionList.add(conct);
			usedConnectionList.remove(conct);
		}
	}
	
	
	@Override
	public void run() {
		try{
			while(true){
				synchronized (this){ // radgan aq this obieqti iblokeba sincronulad moxdeba public metodebisa da am kodis gamodzaxeba.
					while(availableConnectionList.size() > CONNECTIONS_COUNT){
						int lastIndex = availableConnectionList.size() - 1;
						Connection con = availableConnectionList.get(lastIndex);
						availableConnectionList.remove(lastIndex);
						
						con.close();
					}
				}
				Thread.sleep(6000); // ??
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	/* The number of initial connections. */
	private static final int CONNECTIONS_COUNT = 4;
}
