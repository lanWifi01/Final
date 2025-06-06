//class to show the user manager
package Finall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
	
	// Login method: returns true if username/password match
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
        	Connection conn = PowPatrolConn.getConnection(); //the same as conn = DriverManager.getConnection(url, user, password);
        	PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) { //parameters in the try catch closes the resources used
                return rs.next(); // If a record exists, login successful
            }	
        } 
        catch (Exception e) {
        	System.out.println("❌ Login error: " + e.getMessage());
            return false;
        }
    }
    
    //user creation method
    public boolean createUser(String username, String password) {
    	try {
        	Connection conn = PowPatrolConn.getConnection();
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { //Checks for same username
                System.out.println("Username already exists!");
            } else {
            	System.out.println("❌ User creation failed: " + e.getMessage());
            }
            return false;
        }
    }
    
    //password change method
    public static boolean changePW(String username, String oldpass, String newpass) {
    	String checkSql = "SELECT * FROM users WHERE username = ? AND password = ?";
    	String sql = "UPDATE users SET password = ? WHERE username = ?";
    	try (Connection conn = PowPatrolConn.getConnection()) {
    		PreparedStatement stmt = conn.prepareStatement(sql);
    		PreparedStatement checkStmt = conn.prepareStatement(checkSql);
    		checkStmt.setString(1, username);
            checkStmt.setString(2, oldpass);
            ResultSet rs = checkStmt.executeQuery();
            
    		if (rs.next()) {
    			stmt.setString(1, newpass);
                stmt.setString(2, username);
                stmt.executeUpdate();
    		}else {
    			System.out.println("❌ Invalid username or password.");
    			return false;
    		}
    		return true;
    	}
    	catch (Exception e) {
        	System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    
    //method to show coins that will be used in the show my profile menu
    public static int getCoins(String username) {
    	String sql = "SELECT coins FROM users WHERE username = ?";
    	try (Connection conn = PowPatrolConn.getConnection()) {
    		PreparedStatement stmt = conn.prepareStatement(sql);
    		stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	int coins = rs.getInt("coins");
            	return coins;
            }
    		
    	}
    	catch (Exception e) {
        	System.out.println("❌ Error: " + e.getMessage());
        }
    	return 0;
    }
    
    //method to add coins after every game
    public static void addCoins(String username, int amount) {
    	String sql = "UPDATE users SET coins = coins + ? WHERE username = ?";
        try (Connection conn =  PowPatrolConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (Exception e) {
        	System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    //method to use the coins whenever a user buys from the store
    public static boolean spendCoins(String username, int amount) {
        int current = getCoins(username);
        if (current < amount) return false;

        try (Connection conn = PowPatrolConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET coins = coins - ? WHERE username = ?")) {
            stmt.setInt(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //method to get the user id from the username
    public static int getUserID(String username) {
    	try (Connection conn = PowPatrolConn.getConnection();
    			PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			int id = rs.getInt("id");
        		return id;
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
    
    //method to delete a user
    public static void userDelete(int user_id) {
    	
    	try (Connection conn = PowPatrolConn.getConnection();) {
    		
    		//deletes from user_inventory first
    		try (PreparedStatement inv = conn.prepareStatement("DELETE FROM user_inventory WHERE user_id = ?")){
    			inv.setInt(1, user_id);
    			inv.executeUpdate();
    		}
    		
    		//then, deletes from game_results
    		try (PreparedStatement gameRes = conn.prepareStatement("DELETE FROM game_results WHERE user_id = ?")){
    			gameRes.setInt(1, user_id);
    			gameRes.executeUpdate();
    		}
    		
    		//then, deletes from user_purchases
    		try (PreparedStatement purch = conn.prepareStatement("DELETE FROM user_purchases WHERE user_id = ?")){
    			purch.setInt(1, user_id);
    			purch.executeUpdate();
    		}
    		
    		//then, deletes from leaderboard
    		try (PreparedStatement lead = conn.prepareStatement("DELETE FROM leaderboard WHERE user_id = ?")){
    			lead.setInt(1, user_id);
    			lead.executeUpdate();
    		}
    		
    		//finally deletes from users
    		try (PreparedStatement users = conn.prepareStatement("DELETE FROM users WHERE id = ?")){
    			users.setInt(1, user_id);
    			int rows = users.executeUpdate();

    			if (rows > 0) {
    				System.out.println(Main.GREEN + "✅ User and related data deleted successfully!" + Main.RED + "\nHope you won't regret" + Main.RESET);
    			} else {
    				System.out.println(Main.RED + "⚠️ No user found with that ID." + Main.RESET);
    			}
    		}
    		System.out.println("");
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}

/*

try {   
	String sql = "SELECT * FROM sampleUser WHERE username = ? AND password = ?";
	PreparedStatement ps = c.prepareStatement(sql);
	ps.setString(1, username); // replaces first `?`
	ps.setString(2, password); // replaces second `?`
	ResultSet r = ps.executeQuery();
	
	if (r.next()) {
		System.out.println("✅ Login successful. Welcome, " + username + "!");
        Authenticated = true;
        currentUser = username;
	}
	else {
        System.out.println("❌ Login failed. Try again.");
        Authenticated = false;
    }
 }
 catch (Exception e) {
	 System.err.println("❌ Login error: " + e.getMessage());
 }*/