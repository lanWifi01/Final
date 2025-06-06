//class to run the levels (easy, average, hard, extreme)
package Finall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MainGame {
	//initialization of variables
	static Easy easy = new Easy();
	static Average ave = new Average();
	static Hard hard = new Hard();
	static Extreme ex = new Extreme();
	static Main m = new Main();
	static long fScore;
	static long fWPM;
	static long fAccu;
	static int coins;
	static Scanner sc = new Scanner(System.in);
    
	//method for the main game
    public static void MainGame() {
		
    	//initialization of local variables
		long score = 0;
		long wpm = 0;
		long accu = 0;
		long rounds = 0;
		int level = 1;
		
		//loop for the levels and xtra life
		while (true) {
			switch (level) {
				case 1:
					//easy game starts
					easy.Easy();
					
					//if user's score >= 3 user continues
					if (easy.easyGetScore()>=3) {
						//option to exit after a level is done
						System.out.print(Main.CYAN + "Continue or nah? (y/n): " + Main.RESET); //asks user if they want to continue
						String user = sc.nextLine();
						//user input validation
						while (!user.equalsIgnoreCase("y")&&!user.equalsIgnoreCase("n")) {
							System.out.print("Invalid input. y or n only: ");
							user = sc.nextLine();
						}
						if (user.equalsIgnoreCase("n")) {
							System.out.println(Main.RED + "Ahwsuu that's sad :(" + Main.RESET);
							score = score + easy.easyGetScore();
				    		wpm = wpm + easy.easyGetWPM();
				    		accu = accu + easy.easyGetAccuracy();
				    		rounds++;
				    		level+=99;//to stop the game loop
							break;
						}else if (user.equalsIgnoreCase("y")) {
							System.out.println(Main.GREEN + "That's great!" + Main.RESET);
						}
			    		score = score + easy.easyGetScore();
			    		wpm = wpm + easy.easyGetWPM();
			    		accu = accu + easy.easyGetAccuracy();
			    		rounds++;
			    		level++;
			    		break;
					}
					//user's score < 3 = fail
					else {
			    		// Failed Easy, offer Xtra Life
			            boolean used = StoreManager.useXtra(UserManager.getUserID(Main.getUser()));
			            if (used==true) {
			                System.out.println("Retrying Easy mode with Xtra Life...");
			                continue; // Retry Easy again
			            } else {
			                // No Xtra Life or declined to use it
			                score += easy.easyGetScore();
			                wpm += easy.easyGetWPM();
			                accu += easy.easyGetAccuracy();
			                rounds++;
			                level +=99;
			                break; // Exit loop
			            }
			    	}
				case 2:
					//average game starts
					ave.Average();
					
					//if user's score >= 12 user continues
					if (ave.aveGetScore()>=12) {
						//option to exit after a level is done
						System.out.print(Main.CYAN + "Continue or nah? (y/n): " + Main.RESET); //asks user if they want to continue
						String user = sc.nextLine();
						//user input validation
						while (!user.equalsIgnoreCase("y")&&!user.equalsIgnoreCase("n")) {
							System.out.print("Invalid input. y or n only: ");
							user = sc.nextLine();
						}
						if (user.equalsIgnoreCase("n")) {
							System.out.println(Main.RED + "Ahwsuu that's sad :(" + Main.RESET);
							score = score + ave.aveGetScore();
				    		wpm = wpm + ave.aveGetWPM();
				    		accu = accu + ave.aveGetAccuracy();
				    		rounds++;
				    		level+=99;//to stop the game loop
							break;
						}else if (user.equalsIgnoreCase("y")) {
							System.out.println(Main.GREEN + "That's great!" + Main.RESET);
						}
		    			score = score + ave.aveGetScore();
		    			wpm = wpm + ave.aveGetWPM();
		    			accu = accu + ave.aveGetAccuracy();
		    			rounds++;
		    			level++;
		    			break;
					}
					//user's score < 12 = fail
					else {
						// Failed Average, offer Xtra Life
			            boolean used = StoreManager.useXtra(UserManager.getUserID(Main.getUser()));
			            if (used==true) {
			                System.out.println("Retrying Average mode with Xtra Life...");
			                continue; // Retry Average again
			            } else {
			                // No Xtra Life or declined to use it
			                score += ave.aveGetScore();
			                wpm += ave.aveGetWPM();
			                accu += ave.aveGetAccuracy();
			                rounds++;
			                level +=99;
			                break; // Exit loop
			            }
					}
				case 3:
					//hard game starts 
					hard.Hard();
					
					//if user's score >= 12 user continues
	    			if (hard.hardGetScore()>=12) {
	    				//option to exit after a level is done
	    				System.out.print(Main.CYAN + "Continue or nah? (y/n): " + Main.RESET); //asks user if they want to continue
						String user = sc.nextLine();
						//user input validation
						while (!user.equalsIgnoreCase("y")&&!user.equalsIgnoreCase("n")) {
							System.out.print("Invalid input. y or n only: ");
							user = sc.nextLine();
						}
						if (user.equalsIgnoreCase("n")) {
							System.out.println(Main.RED + "Ahwsuu that's sad :(" + Main.RESET);
							score = score + hard.hardGetScore();
				    		wpm = wpm + hard.hardGetWPM();
				    		accu = accu + hard.hardGetAccuracy();
				    		rounds++;
				    		level+=99;//to stop the game loop
							break;
						}else if (user.equalsIgnoreCase("y")) {
							System.out.println(Main.GREEN + "That's great!" + Main.RESET);
						}
	    				score = score + hard.hardGetScore();
	    				wpm = wpm + hard.hardGetWPM();
	    				accu = accu + hard.hardGetAccuracy();
	    				rounds++;
	    				level++;
	    				break;
	    			}
	    			//user's score < 12 = fail
	    			else {
	    				// Failed Hard, offer Xtra Life
			            boolean used = StoreManager.useXtra(UserManager.getUserID(Main.getUser()));
			            if (used==true) {
			                System.out.println("Retrying Hard mode with Xtra Life...");
			                continue; // Retry Hard again
			            } else {
			                // No Xtra Life or declined to use it
			                score += hard.hardGetScore();
			                wpm += hard.hardGetWPM();
			                accu += hard.hardGetAccuracy();
			                rounds++;
			                level +=99;
			                break; // Exit loop
			            }
	    			}
				case 4: 
					//final level
					ex.Extreme();
    				score = score + ex.exGetScore();
    				wpm = wpm + ex.exGetWPM();
    				accu = accu + ex.exGetAccuracy();
    				rounds++; 
    				level++;
    				break;// Exit loop
			}
			if (level>4) {break;} //indicates when to stop	    	
		}
		
		//computation of game results
		fScore += score;
    	fWPM = wpm/rounds;
    	fAccu = accu/rounds;
    	coins = (int)score/2;
    	//accumulation of user coins
    	UserManager.addCoins(Main.getUser(), coins);
  	
    	//shows overall final game results
    	System.out.println(Main.CYAN + "Total Score: " + fScore);
    	System.out.println("Total WPM: " + fWPM);
    	System.out.println("Total Accuracy: " + fAccu);
    	System.out.println("Coins earned: " + Main.YELLOW + coins);
    	System.out.println(Main.CYAN + "Total coins: " + Main.YELLOW + UserManager.getCoins(Main.getUser()) + Main.RESET);
    	
    }
	
    //method to insert game results to the database 
	static void insertResults() {
		String sql = "INSERT INTO game_results (user_id, score, wpm, accuracy, played_at) VALUES (?, ?, ?, ?, NOW())";
		
		try (Connection conn = PowPatrolConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, getID());
			stmt.setFloat(2, fScore);
			stmt.setFloat(3, fWPM);
			stmt.setFloat(4, fAccu);

			stmt.executeUpdate();
			System.out.println("\nGame result inserted!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method to get user's user id from the database for specific purposes
	static Integer getID() {
		String sql = "SELECT id FROM users WHERE username = ?";
		
		try (Connection conn = PowPatrolConn.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, Main.getUser());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return null; // user not found
            }
		}
		catch(Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
	
	//method to update game leaderboard
	static void updateLead(int user_id) {
		String statsSQL = "SELECT MAX(score) AS maxScore, AVG(wpm) AS aveWPM, AVG(accuracy) AS aveAccu FROM game_results WHERE user_id = ?";
	    String selectSQL = "SELECT user_id FROM leaderboard WHERE user_id = ?";
	    String insertSQL = "INSERT INTO leaderboard (user_id, highest_score, average_wpm, average_accuracy) VALUES (?, ?, ?, ?)";
	    String updateSQL = "UPDATE leaderboard SET highest_score = ?, average_wpm = ?, average_accuracy = ? WHERE user_id = ?";
	    
	    try (Connection conn = PowPatrolConn.getConnection()){
	    	//holder of stats 
	    	float maxScore = 0;
	    	float aveWPM = 0;
	    	float aveAccu = 0;
	    	
	    	//throws user's stats from game_results to the holders
	    	try (PreparedStatement stats = conn.prepareStatement(statsSQL)){
	    		stats.setInt(1, user_id);
	    		ResultSet rs = stats.executeQuery();
	    		if (rs.next()) {
	    			maxScore = rs.getFloat("maxScore");
	    			aveWPM = rs.getFloat("aveWPM");
	    			aveAccu = rs.getFloat("aveAccu");
	    		}
	    	}
	    	
	    	//check if user exists
	    	try (PreparedStatement select = conn.prepareStatement(selectSQL)) {
	    		select.setInt(1, user_id);
	    		ResultSet rs = select.executeQuery();
	    		
	    		if (rs.next()) { //if user exists, update
	    			try (PreparedStatement update = conn.prepareStatement(updateSQL)) {
	    				update.setFloat(1, maxScore);
	    				update.setFloat(2, aveWPM);
	    				update.setFloat(3, aveAccu);
	    				update.setInt(4, user_id);
	    			}
	    		}else { //if user doesn't exist, insert
	    			try (PreparedStatement insert = conn.prepareStatement(insertSQL)) {
	    				insert.setInt(1, user_id);
	    				insert.setFloat(2, maxScore);
	    				insert.setFloat(3, aveWPM);
	    				insert.setFloat(4, aveAccu);	    				
	    			}
	    		}
	    	}
	    	System.out.println("Leaderboard updated!!");
	    	
	    }catch(Exception e) {
	    	System.out.println("Error updating leaderboard: " + e.getMessage());
	    }
	}
	
}
