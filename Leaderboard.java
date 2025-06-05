//class to open every leaderboard menu options
package Finall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Leaderboard {
	
	//colors
	static String RESET = "\u001B[0m";
	static String RED = "\u001B[31m";
	static String GREEN = "\u001B[32m";
	static String YELLOW = "\u001B[33m";
	static String CYAN = "\u001B[36m";
	static String PINK = "\u001B[35m";
	static String HOT_PINK = "\u001B[38;2;255;105;180m";
	
	//method to get the top 10 fastest players based from wpm
	static void showTop10WPM() {
		String sql = "SELECT users.username, leaderboard.average_wpm FROM leaderboard "
				+ "JOIN users ON leaderboard.user_id = users.id "
				+ "ORDER BY average_wpm DESC;";
        try (Connection conn = PowPatrolConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery();) {
        	int rank = 1;
        	
        	while (rs.next()) {
        		String username = rs.getString("username");
        		Integer wpm = rs.getInt("average_wpm");
        		
        		System.out.println("Rank " + rank + ": " + username + "	||WPM: " + wpm);
        		rank++;
        		
        	}
        	
        } 
        catch (Exception e) {
        	System.out.println("❌ Error: " + e.getMessage());
            
        }
	}
	
	//method to get the top 10 players for accuracy
	static void showTop10Accuracy() {
		String sql = "SELECT users.username, leaderboard.average_accuracy FROM leaderboard "
				+ "JOIN users ON leaderboard.user_id = users.id "
				+ "ORDER BY average_accuracy DESC;";
        try (Connection conn = PowPatrolConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery();) {
        	int rank = 1;
        	
        	while (rs.next()) {
        		String username = rs.getString("username");
        		Integer accuracy = rs.getInt("average_accuracy");
        		
        		System.out.println("Rank " + rank + ": " + username + "	||Accuracy: " + accuracy);
        		rank++;
        		
        	}
        	
        } 
        catch (Exception e) {
        	System.out.println("❌ Error: " + e.getMessage());
            
        }
	}
	
	//method to get the top 10 players with highest scores
	static void showTop10HighScores() {
		String sql = "SELECT users.username, leaderboard.highest_score FROM leaderboard "
				+ "JOIN users ON leaderboard.user_id = users.id "
				+ "ORDER BY highest_score DESC;";
        try (Connection conn = PowPatrolConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery();) {
        	int rank = 1;
        	
        	while (rs.next()) {
        		String username = rs.getString("username");
        		Integer score = rs.getInt("highest_score");
        		
        		System.out.println("Rank " + rank + ": " + username + "	||Highest Score: " + score);
        		rank++;
        		
        	}
        	
        } 
        catch (Exception e) {
        	System.out.println("❌ Error: " + e.getMessage());
            
        }
	}
}
