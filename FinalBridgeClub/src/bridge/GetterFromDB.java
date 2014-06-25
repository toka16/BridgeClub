package bridge;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bridge.Table.Side;
import bridge.Table.Vulnerable;
import bridge.User.Status;

import com.mysql.jdbc.Connection;

public class GetterFromDB {
	
	/**
	 * The method returns registered users userNames.
	 * @return
	 */
	public static List<String> getRegisteredUsers(){
		List<String> usersNameList = new ArrayList<String>();
		try {
			String query = "select userName from registrationTable";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()){
				usersNameList.add(resultSet.getString(1));
			}
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersNameList;
	}
	
	/**
	 * The function returns the given user score history.
	 * @param user
	 * @return
	 */
	public static List<Object[]> getScoresHistory(User user){
		List<Object[]> resultList = new ArrayList<Object[]>();
		try {
			String query = "select * from " + USERS_SCORES_VIEW +
							" where userID = '" + user.getUserID() + "'";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()){
				Object[] rawArray = new Object[5];
				rawArray[0] = new Integer(resultSet.getInt(2));
				rawArray[1] = Side.values()[resultSet.getInt(3)];
				rawArray[2] = new Integer(resultSet.getInt(4));
				rawArray[3] = new Integer(resultSet.getInt(5));
				rawArray[4] = new Integer(resultSet.getInt(6));
				resultList.add(rawArray);
				System.out.println("dealGameID: " + rawArray[0] + ". side: " + rawArray[1] + ". score: " + rawArray[2] + ". comPoint: " + rawArray[3] + ". imp: " + rawArray[4]);
			}
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * The method returns pair user scores.
	 * @param one
	 * @param two
	 * @return
	 */
	public static List<Object[]> getPairUserSumScores(User one, User two){
		List<Object[]> resultList = new ArrayList<Object[]>();
		try {
			String query = "select * from " + Pairs_SCORES_VIEW;
			if(one != null && two != null)
				query += " where userName1 = '" + one.getUsername() + "' and userName2 = '" +  two.getUsername() + "'";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()){
				Object[] rawArray = new Object[5];
				rawArray[0] = resultSet.getString(1);
				rawArray[1] = resultSet.getString(2);
				rawArray[2] = new Integer(resultSet.getInt(3));
				rawArray[3] = new Integer(resultSet.getInt(4));
				rawArray[4] = new Integer(resultSet.getInt(5));
				resultList.add(rawArray);
				System.out.println("user1: " + rawArray[0] + ". user2: " + rawArray[1] + ". sumscore: " + rawArray[2] + ". sumcomPoint: " + rawArray[3] + ". sumimp: " + rawArray[4]);
			}
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	
	public static List<Object[]> getUsersTotalImp(User user){
		List<Object[]> resultList = new ArrayList<Object[]>();
		try {
			String query = "select * from " + IMP_VIEW;
			if(user != null) 
				query += " where userName = '" + user.getUsername() + "'";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()){
				Object[] rawArray = new Object[4];
				rawArray[0] = resultSet.getString(1);
				rawArray[1] = new Integer(resultSet.getInt(2));
				rawArray[2] = new Integer(resultSet.getInt(3));
				rawArray[3] = new Integer(resultSet.getInt(4));
				resultList.add(rawArray);
				System.out.println("userName: " + rawArray[0] + ". sumscore: " + rawArray[1] + ". sumcompScore: " + rawArray[2] + ". sumimpScore: " + rawArray[3]);
			}
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * The method returns user object.
	 * @param userName
	 * @return
	 */
	public static User getUser(String userName, String password) {
		User user = null;
		try {
			String query = "select * from registrationTable " + 
							"where userName = '" + userName + "' and userPassword = '" + password +"'";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			user = getAppropUser(resultSet, userName);
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * The method returns boolean result - whether the given user exists.
	 * @param userName
	 * @return
	 */
	public static boolean isExistedUser(String nameOfUser){
		boolean result = false;
		try {
			String query = "select userName from registrationTable where userName = '" + nameOfUser + "'";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()){
				result = resultSet.getString(1).length() > 0;
			}
			System.out.println("print: result " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * The method is a helper method for getUser() method.
	 * @param rs
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	private static User getAppropUser(ResultSet rs, String userName) throws SQLException {
		User user = null;
		while(rs.next()){
			int idOfUser = rs.getInt(1);
			String playerName = rs.getString(4);
			String playerSurname = rs.getString(5);
			String mail = rs.getString(6);
			int statusNum = rs.getInt(11);
			Status[] statusArray = Status.values();
			user = new User(userName, statusArray[statusNum]);
			user.setFirstName(playerName);
			user.setLastName(playerSurname);
			user.setMail(mail);
			user.setUserID(idOfUser);
			user.setPhoneNumber(rs.getString(7));
			user.setBirthDate(rs.getString(8));
			user.setSex(rs.getString(9));
			System.out.println("print user info:  " + playerName + " " + playerSurname + " " + mail + " " + statusNum);
		}
		return user;
	}
	
	
	/**
	 * The method returns vulnerable integer according to given dealNumber.
	 * @param dealNumber
	 * @return
	 */
	public static Map<String, Object> getVulnerable(int dealNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String query = "select dealerSide, vulnerable from deal_numbers where ID = " + dealNumber;
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()){
				Side dealerSide = Side.values()[resultSet.getInt(1)];
				Vulnerable vulner = Vulnerable.values()[resultSet.getInt(2)];
				map.put("dealer", dealerSide);
				map.put("vulnerable", vulner);
				System.out.println("print: dealerSide is " + dealerSide.name() + " .  Vulnerables:  " + vulner.name());
			}
			
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * The method returns an appropriate score for the given parameters.
	 * @param gameID
	 * @return
	 */
	public static int getCompensationPointFromDeal(int gameID){
		int result = -1;
		try{
			String query = "{ ? = call getCompensationPointFromDeal(?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement stmt = connect.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, gameID);
			stmt.execute();
			result = stmt.getInt(1);
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
			System.out.println("print: compensation Score from deal by game ID is: " + result);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * The function returns imp score for delta parameter.
	 * @param delta
	 * @return
	 */
	public static int getImpScore(int delta){
		int result = -1;
		try{
			String query = "{ ? = call get_imp(?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement stmt = connect.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, delta);
			stmt.execute();
			result = stmt.getInt(1);
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
			System.out.println("print: the imp score is " + result);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	

	
	// Constance variables:
	private static final String USERS_SCORES_VIEW = "scores_by_users";
	private static final String Pairs_SCORES_VIEW = "pairs_by_imp";
	private static final String IMP_VIEW = "users_by_imp";
}
