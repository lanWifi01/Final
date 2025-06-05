//class to run the FINAL GAME
package Finall;

import java.util.Scanner;

public class Main {
	//colors
	static String RESET = "\u001B[0m";
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String YELLOW = "\u001B[33m";
    static String CYAN = "\u001B[36m";
    static String PINK = "\u001B[35m";
    static String HOT_PINK = "\u001B[38;2;255;105;180m";
	
    //initialization of the user variable wherein the username will be put
	public static String user = null;
	static Scanner sc = new Scanner(System.in);
	
	//main method
	public static void main(String[] args) {
        UserManager userManager = new UserManager();
        String username = null; //initializing the variable username

        // User login, sign-up, and change password loop
        while (true) {
        	//welcome part of the game
        	System.out.println(HOT_PINK + "============================================" + RESET);
        	System.out.println(HOT_PINK + "        WELCOME TO THE TYPING GAME!!        " + RESET);
        	System.out.println(HOT_PINK + "============================================" + RESET);
            System.out.println("1. Login");
            System.out.println("2. Create new user");
            System.out.println("3. Change password");
            System.out.println("4. Exit");
            System.out.print("Select option: ");
            System.out.print(HOT_PINK);
            String input = sc.nextLine().trim();
            System.out.println(RESET);
            
            //user login
            if (input.equals("1")) {
            	//user validation and login
                while (true) {
                    System.out.print("Username (or type 'back' to return): ");
                    String u = sc.nextLine();
                    if (u.equalsIgnoreCase("back")) break;
                    System.out.print("Password (or type 'back' to return): ");
                    String p = sc.nextLine();
                    if (p.equalsIgnoreCase("back")) break;
                    if (userManager.login(u, p)) {
                        username = u;
                        System.out.println("Login successful!");
                        System.out.println("");
                        System.out.println(HOT_PINK + "== Welcome " + CYAN +  username + HOT_PINK + "!! ==" + RESET);
                        user = username;
                        //once the user logs in and is validated with passwords, the runGame() method will come to action
                        runGame();
                        break;
                    } else {
                        System.out.println("Login failed.");
                    }
                }
            } 
            //user sign-up
            else if (input.equals("2")) {
            	//user validation
                while (true) {
                    System.out.print("Enter username (or type 'back' to return): ");
                    String newU = sc.nextLine();
                    if (newU.equalsIgnoreCase("back")) break;
                    System.out.print("Enter password (or type 'back' to return): ");
                    String newP = sc.nextLine();
                    if (newP.equalsIgnoreCase("back")) break;
                    if (userManager.createUser(newU, newP)) {
                        System.out.println("User created successfully!");
                        break;
                    }
                }
            } 
            //user password change option
            else if (input.equals("3")) {
            	//user validation
            	while (true) {
                    System.out.print("Enter username (or type 'back' to return): ");
                    String u = sc.nextLine();
                    if (u.equalsIgnoreCase("back")) break;
                    System.out.print("Enter old password (or type 'back' to return): ");
                    String oldP = sc.nextLine();
                    if (oldP.equalsIgnoreCase("back")) break;
                    System.out.print("Enter new password (or type 'back' to return): ");
                    String newP = sc.nextLine();
                    if (newP.equalsIgnoreCase("back")) break;
                    if (UserManager.changePW(u, oldP, newP)) {
                        System.out.println("Password changed successfully!");
                        break;
                    } else {
                        System.out.println("Try again!");
                    }               
                }
            } 
            //game exit portal
            else if (input.equals("4")) {
                System.out.println("Goodbye!");
                sc.close();
                return;
            } else {
                System.out.println("Invalid input. Please enter 1, 2, or 3 only.");
            }
        }

	}
	
	//method to get user's username
	static String getUser() {
		return user;
	}
	
	//method to run the inside of the game after user login
	static void runGame() {
        while (true) {
        	System.out.println(HOT_PINK + "============================================" + RESET);
            System.out.println(HOT_PINK + "              Typing Test Menu              " + RESET);
            System.out.println(HOT_PINK + "============================================" + RESET);
            System.out.println("1. Play");
            System.out.println("2. Show Leaderboard");
            System.out.println("3. View My Profile");
            System.out.println("4. View Store");
            System.out.println("5. Log out");

            System.out.print("\nSelect option: ");
            String menuInput = sc.nextLine().trim();

            switch (menuInput) {
                case "1": //option to play the main game with easy, average, hard, and extreme
                    MainGame.MainGame();
                    MainGame.insertResults();
                    MainGame.updateLead();
                    break;
               
                case "2": //option to show the leaderboard menu
                	LBManager.showLeaderboard();
                    break;
               
                case "3": //option to show the user profile menu
                	ProfileManager.showProfile();
                    break;
                case "4": //option to open the store
                	StoreManager.openStore(Main.getUser());
                    break;
                case "5":                
                    // Log out option and makes the user null again to run the welcome part of the game
                	System.out.println(HOT_PINK + "============================================" + RESET);
                    System.out.println(HOT_PINK + "          You have been logged out.         " + RESET);
                    System.out.println(HOT_PINK + "============================================" + RESET);
                    user = null;
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
	}
}
