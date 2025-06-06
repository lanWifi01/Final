//class for user profile option
package Finall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ProfileManager {
	//colors
	static String RESET = "\u001B[0m";
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String YELLOW = "\u001B[33m";
    static String CYAN = "\u001B[36m";
    static String PINK = "\u001B[35m";
    static String HOT_PINK = "\u001B[38;2;255;105;180m";
    
    //method to show the profile viewer menu
	static void showProfile() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println(CYAN + "============================================" + RESET);
            System.out.println(CYAN + "              View profile for:             " + RESET);
            System.out.println(CYAN + "============================================" + RESET);
            System.out.println("1. All Games");
            System.out.println("2. Show Inventory");
            System.out.println("3. Show my coins");
            System.out.println("4. Back");
            System.out.print("Select option: ");
            String input = sc.nextLine();
            
            switch (input) {
            case "1": //option to show all games played by the user even the time and date
            	allGames();
            	break;
            case "2": //option to show user's purchased items and its quantity
            	StoreManager.showInventory(UserManager.getUserID(Main.getUser()));
            	break;
            case "3": //option to show users remaining coins accumulated from playing
            	System.out.println(CYAN + "============================================" + RESET);
            	System.out.println(CYAN + "              Total coins: " + YELLOW + UserManager.getCoins(Main.getUser())+ "🟡" + RESET);
            	System.out.println(CYAN + "============================================" + RESET);
            	break;
            case "4": //option to go back to typing test menu
                return; 
            default:
                System.out.println("Invalid input.");
            }

		}
		
	}
	
	//method to show all the user's games played even the date and time the user played
	static void allGames() {
		String sql = "SELECT score, wpm, accuracy, played_at FROM game_results where user_id = ?";
		
		 try (Connection conn = PowPatrolConn.getConnection();
				 PreparedStatement stmt = conn.prepareStatement(sql);) {
			 stmt.setInt(1, MainGame.getID());
			 ResultSet rs = stmt.executeQuery();
			 int games = 1;
			 
			 System.out.println("");
			 System.out.println(Main.getUser() + "'s All Games");
			 
			 
			 if(!rs.next()) { //if user did not play yet
				 System.out.println(YELLOW + "You didn't play yet." + RESET);
				 return;
			 }
			 while (rs.next()) {
				 int score = rs.getInt("score");
				 float wpm = rs.getFloat("wpm");
				 float accuracy = rs.getFloat("accuracy");
				 String dateTime = rs.getString("played_at");
				 System.out.println("Game no. " + games + "" +	"	||Score: " + score + "	||WPM: " + wpm + "	||Accuracy: " + accuracy + "	||Date: " + dateTime);
				 games++;
			 }
			 	
		 }catch (Exception e) {
			 System.out.println("❌ Error: " + e.getMessage());
		 }
	}
	
}
