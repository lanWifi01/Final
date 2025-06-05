//class for the LeaderBoard Manager
package Finall;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LBManager {
	
	//method to show leaderboard
	static void showLeaderboard() {
        while (true) {
        	Scanner sc = new Scanner(System.in);
        	System.out.println(Main.HOT_PINK + "============================================" + Main.RESET);
            System.out.println(Main.HOT_PINK + "              Leaderboard Menu              " + Main.RESET);
            System.out.println(Main.HOT_PINK + "============================================" + Main.RESET);
            //leaderboard menu
            System.out.println("1. Show the Top 10 Fastest");
            System.out.println("2. Show the Top 10 by Accuracy");
            System.out.println("3. Show the Top 10 Most Points");
            System.out.println("4. Back");
            System.out.print("Select option: ");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                	System.out.println("");
                	//calls the method showTop10WPM()
                    Leaderboard.showTop10WPM();
                    break;
                case "2":
                	System.out.println("");
                	//calls the method showTop10Accuracy()
                    Leaderboard.showTop10Accuracy();
                    break;
                case "3":
                	System.out.println("");
                	//calls the method showTop10HighScores()
                    Leaderboard.showTop10HighScores();
                    break;
                case "4":
                    return; // Back to typing test menu
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
	
	
}
