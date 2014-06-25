package bridge;

import bridge.Card;
import bridge.Table.Side;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import bridge.StaticConnectionPool;

import com.mysql.jdbc.Connection;

import bridge.User.Status;

public class SaverInDB {
	
	
	private static void makeLastLine(CallableStatement stmt, Connection con) throws SQLException{
		stmt.execute();
		stmt.close();
		StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(con);
	}
	
	/**
	 * The method updates user password.
	 * @param user
	 * @param newPassword
	 */
	public static void changePassword(User user, String newPassword){
		try {
			String query = "update registrationTable " + 
							"set userPassword = '" + newPassword + "' " +
							"where userName = '" + user.getUsername() + "'";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method saves a given bid into database.
	 * @param bid - bid in play.
	 * @param dealID - id of deal.
	 */
	public static void saveBidInDatabase(int gameID, Bid bid){
		System.out.println("bid nominal:"+bid.getBidNominal().name()+" suit: "+bid.getBidSuit().name()+" double: "+bid.getBidDoubledFlag());
		try {
			String query = "{ call saveBid(?, ?, ?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.setInt(1, gameID);
			statement.setInt(2, bid.getBidSuit().ordinal());
			statement.setInt(3, bid.getBidNominal().ordinal());
			statement.setInt(4, bid.getBidDoubledFlag());
			makeLastLine(statement, connect);
			System.out.print("print: save bid. " + bid.getBidSuit() + ", " + bid.getBidNominal()+ ", " + bid.getBidDoubledFlag());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The method saves user data in database and returns his/her ID in database table.
	 * @param userName
	 * @param password
	 * @param playerName
	 * @param playerSurname
	 * @param mail
	 * @param phone
	 * @param birthDate - yyyy-mm-dd
	 * @param gender
	 * @param status
	 * @return
	 */
	public static int userRegistration(String userName, String password, String playerName, String playerSurname, String mail, String phone, 
								String birthDate, String gender, Status status){
		int userID = 0;
		try {
			String query = "{ call insertIntoRegistrationTable(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.registerOutParameter(1, userID);
			statement.setString(2, userName);
			statement.setString(3, password);
			statement.setString(4, playerName);
			statement.setString(5, playerSurname);
			statement.setString(6, mail);
			statement.setString(7, phone);
			statement.setString(8, birthDate);
			statement.setString(9, gender);
			statement.setInt(10, status.ordinal());
			makeLastLine(statement, connect);
			System.out.print("print: save user data");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * The method saves trick (vziatki) cards in database one by one.
	 * @param gameID
	 * @param card
	 */
	public static void saveDealGameCards(int gameID, Card card){
		try {
			String query = "{ call saveDealGameCards(?, ?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.setInt(1, gameID);
			statement.setInt(2, card.getSuit().ordinal());
			statement.setInt(3, card.getNominal().ordinal());
			makeLastLine(statement, connect);
			System.out.print("print: save deal_game cards");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method saves players for given game.
	 * @param gameID
	 * @param side
	 * @param userID
	 */
	public static void savesDealGamesPlayers(int gameID, Side side, int userID){
		try {
			String query = "{ call savesGamePlayers(?, ?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.setInt(1, gameID);
			statement.setInt(2, side.ordinal());
			statement.setInt(3, userID);
			makeLastLine(statement, connect);
			System.out.print("print: save deal_Games players");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method just saves dealID.
	 * @param dealID
	 */
	public static int saveGameStartingData(int dealID){
		int result = -1;
		try {
			String query = "{ call saveStartGameInfo(?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.registerOutParameter(1, result);
			statement.setInt(2, dealID);
			statement.execute();
			result = statement.getInt(1);
			statement.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
			System.out.println("print: game Table saves dealID " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * The method saves data when bidding is over.
	 * @param gameID
	 * @param declarer
	 */
	public static void saveGameDataAfterEndBidding(int gameID, Side declarer){
		try {
			String query = "{ call updateDealGame(?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.setInt(1, declarer.ordinal());
			statement.setInt(2, gameID);
			makeLastLine(statement, connect);
			System.out.print("print: game Table saves declarer and contract. declarer is: " + declarer.name());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * The method saves eventually data for game.
	 * @param handsCount - trick(vziatki) quantity.
	 * @param score
	 * @param gameID
	 */
	public static int saveGameDataAfterEndGame(int handsCount, int gameID){
		int result = -1;
		try {
			String query = "{ call eventuallyUpdateDealGame(?, ?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.registerOutParameter(1, result);
			statement.setInt(2, handsCount);
			statement.setInt(3, gameID);
			statement.execute();
			result = statement.getInt(1);
			statement.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
			System.out.println("print: game Table saves handsCount and score. inserting is over. score = " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * The method saves side's cards in deal case.
	 * @param dealID
	 * @param side
	 * @param card
	 */
	public static void saveDealCards(int dealID, Side side, List<Card> cards){
		try {
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			for(int i = 0; i < cards.size(); i++){
				Card cardElem = cards.get(i);
				String query = "{ call saveDealCards(?, ?, ?, ?) }";
				CallableStatement statement = connect.prepareCall(query);
				statement.setInt(1, dealID);
				statement.setInt(2, side.ordinal());
				statement.setInt(3, cardElem.getSuit().ordinal());
				statement.setInt(4, cardElem.getNominal().ordinal());
				statement.execute();
				statement.close();
			}
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
			System.out.print("print: saves deal_cards");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method saves compensation points.
	 * @param idOfDeal
	 */
	public static void saveCompensationPointToDeal(int idOfDeal){
		try {
			String query = "{ call insert_compensation_point(?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.setInt(1, idOfDeal);
			makeLastLine(statement, connect);
			System.out.println("print: insert_compensation_point. ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method saves deal number and NS hands score at the time of game.
	 * @param side
	 * @param vulnerable
	 * @param cardsScoreInNSHands
	 * @param hasNS
	 * @return
	 */
	public static int saveDeal(int dealNumber){
		int dealID = 0;
		try {
			String query = "{ call saveDeals(?, ?) }";
			Connection connect = StaticConnectionPool.getConnectionPoolOjbect().choiceConnection();
			CallableStatement statement = connect.prepareCall(query);
			statement.registerOutParameter(1, dealID);
			statement.setInt(2, dealNumber);
			statement.execute();
			dealID = statement.getInt(1);
			statement.close();
			StaticConnectionPool.getConnectionPoolOjbect().unchoiceConnection(connect);
			System.out.print("print: save deal " + dealID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dealID;
	}
}