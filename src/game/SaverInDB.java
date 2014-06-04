package game;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Semaphore;

import com.mysql.jdbc.Connection;


public class SaverInDB {
	
	private DBConnectionPool connectPool;
	public Semaphore publicSem = new Semaphore(0);
	
	public SaverInDB() {
		connectPool = new DBConnectionPool(ADDRESS, USER, PASSWORD);
	}
	
	public void createNewUser(String userName, String password, String playingName, String mail, String phone, String date){
		try {
			Connection connect = connectPool.choiceConnection();
			Statement stmt = connect.createStatement();
			insertUserTable(stmt, userName, password, playingName, mail, phone, date);
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			// ma  nawilshi davwert im shemtxvevis kodebs roca momxmarebel sheyavs ukve gamoyenebuli saxelebi registraciis dros.
			e.printStackTrace();
		}
	}
	
	// Helper method for createNewUser() method. It insert row in userstabel in database.
	private void insertUserTable(Statement stmt, String userNameStr, String passwordStr, String playName, 
									String mailStr, String phone, String date){
		try{
			stmt.executeQuery("use bridgeclub");
			String insertUser = "insert into registrationTable(userName, userPassword, playerName, mail, phone, birthday, registerTime) " + 
									" values(\"" + userNameStr + "\", \"" + passwordStr + "\", \"" + 
											playName + "\", \"" + mailStr + "\", \"" + phone + "\", \""  + date + "\", now());";
			stmt.executeUpdate(insertUser);
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	// The method deletes user info in database.
	public void deleteUser(String userName){
		try {
			Connection connect = connectPool.choiceConnection();
			Statement stmt = connect.createStatement();
			deleteUser(stmt, userName);
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// The method deletes user.
	private void deleteUser(Statement stmt, String userName){
		try{
			stmt.executeQuery("use bridgeclub");
			String deleteUser = "delete from registrationTable where userName = \"" + userName + "\"";
			stmt.executeUpdate(deleteUser);
		} catch (SQLException ex){
			// shegvidzlia gamoviyenot es adgilebi araswori inpormaciis shemoyvanisas.
			ex.printStackTrace();
		}
	}
	
	// The method saves deal.
	public void saveCards(char side, String userName, String card){
		try {
			Connection connect = connectPool.choiceConnection();
			Statement stmt = connect.createStatement();
			stmt.executeQuery("use bridgeclub");
			String saveCard = "insert into handCardTable(side, appropUserName, cardsList) " + 
							" value(\"" + side + "\", \"" + userName +"\", \"" + card + "\")";
			stmt.executeUpdate(saveCard);
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			// ...
			e.printStackTrace();
		}
	}
	
	// The method updates user info.
	public void updateUser(String userName, String password, String playingName, String mail){
		try {
			Connection connect = connectPool.choiceConnection();
			Statement stmt = connect.createStatement();
			stmt.executeQuery("use bridgeclub");
			String updateUser = "update registrationTable set userPassword = \"" + password +
								"\", playerName = \"" + playingName +"\", mail = \"" + mail + "\" where userName = \"" + userName + "\"";
			stmt.executeUpdate(updateUser);
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			// ..
			e.printStackTrace();
		}
	}

	// The method saves deal. The method contains of savePalyingDeal() method.
	public void savePalyngDeal(char side, String userName, int tableN, String offer, int numGame, String zone, String player){
		try {
			Connection connect = connectPool.choiceConnection();
			Statement stmt = connect.createStatement();
			stmt.executeQuery("use bridgeclub");
			String saveDeal = "insert into playingDeal(side, appropUserName, tableNumber, bidOffer, gameNumber, zonePair, player) " + 
									" values(\"" + side + "\", \"" + userName + "\", \"" + 
									tableN + "\", \"" + offer + "\", \"" + numGame + "\", \"" + zone + "\", \"" + player + "\");";
			stmt.executeUpdate(saveDeal);
			stmt.close();
			connect.close();
		} catch (SQLException e) {
			// ...
			e.printStackTrace();
		}
	}
	
	public void TestThread(){
		String uN = "user";
		String uP = "1";
		String plN = "player";
		String mail = "mail";
		String phone = "223344";
		String date = "2011-07-05";
		for(int i = 0; i < 4; i++){
			Thread th = new Thread(new MyThread(i, uN + 0, uP + i, plN + i, mail + i, phone, date));
			th.start();
			publicSem.release();
		}
		
//		Thread th = new Thread(new MyThread(4, uN + 1, uP, plN, mail, phone, date));
//		th.start();
		publicSem.release();
	}
	
	/** */
	private class MyThread implements Runnable{

		private int number;
		private String usrName;
		private String passwd;
		private String playingN;
		private String mailStr;
		private String phoneStr;
		private String dateStr;
		
		public MyThread(int digit, String userName, String password, String playingName, String mail, String phone, String date){
			number = digit;
			usrName = userName;
			passwd = password;
			playingN = playingName;
			mailStr = mail;
			phoneStr = phone;
			dateStr = date;
		}
		
		@Override
		public void run() {
//			if(number == 4){
				updateUser(usrName, passwd, playingN, mailStr);
//			}
//			else{
//				createNewUser(usrName, passwd, playingN, mailStr, phoneStr, dateStr);
//			}
		}
		
	}
	/** */
	 
	public static void main(String[] args) {
		SaverInDB sv = new SaverInDB();
		sv.TestThread();
		for(int i = 0; i < 5; i++){
			try {
				sv.publicSem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("End The main thread!!! bazashi xuti chamateba ara aris. imito ro erti thread-i updata-s aketebs");
//		sv.createNewUser("user12", "112209", "myName", "mail@gmail.com", "598-14-02-89", "2011-05-07");
//		sv.deleteUser("user12");
//		sv.saveCards('N', "bridgemaster", "2H-5H-7H-8H-10S-12S-13S-7D-8D-9D-14D-12C-13C"); // ???
//		sv.updateUser("user12", "newPassword", "newName", "mail@gmail.com");
//		sv.savePalyngDeal('S', "user12", 0, "off", 1, "NS", "user");
	}
	
	// Constant variables:
	private static final String ADDRESS = "jdbc:mysql://localhost:3306";
	private static final String USER = "root";
	private static final String PASSWORD = "12345";
}
