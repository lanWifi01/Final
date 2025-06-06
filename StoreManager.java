//class for the store manager
package Finall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StoreManager {
	static Scanner sc = new Scanner(System.in);
	
	//method to open the store
	public static void openStore(String username) {
		String sql = "SELECT * FROM store_items";
        try (Connection conn =  PowPatrolConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
        	
        	ResultSet rs = stmt.executeQuery();
        	
        	//store welcome
        	System.out.println(Main.HOT_PINK + "============================================" + Main.RESET);
            System.out.println(Main.HOT_PINK + "            Welcome to the STORE            " + Main.RESET);
            System.out.println(Main.HOT_PINK + "============================================" + Main.RESET);
            
            //checks every available store items from the inventory and its price
            while (rs.next()) {
            	//variables for the 
            	int item_id = rs.getInt("item_id");
            	String item_name = rs.getString("item_name");
            	String desc = rs.getString("item_description");
            	int price = rs.getInt("price");
            	System.out.println("ITEMS");
            	System.out.println(item_id + ". " + item_name + " - " + desc + "	||Costs: " + price + " coins");
            }
            
            //shows user's available coins
            System.out.println("");
            System.out.println("Coins available: " + UserManager.getCoins(username));
            System.out.print("\nEnter the ID of the item you want to buy (or type 'back'): ");
            String input = sc.nextLine();
            
            if (input.equalsIgnoreCase("back")) return; //takes the user back to the typing test menu
            
            int itemId = Integer.parseInt(input); //makes the string user input into an integer
            
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM store_items WHERE item_id = ?");
            ps.setInt(1, itemId);
            ResultSet item = ps.executeQuery();
            
            if (item.next()) {
            	int id = item.getInt("item_id");
            	String name = item.getString("item_name");
            	int price = item.getInt("price");
            	
            	//if user successfully buys
            	if (UserManager.getCoins(username) >= price) {
                    UserManager.spendCoins(username, price);
                    System.out.println("You bought: " + name + "! Remaining coins: " + UserManager.getCoins(username));
                    insertToInventory(UserManager.getUserID(username), id);
                    insertPurchase(UserManager.getUserID(username), id);
                    
                } else { //if user doesn't have enough coins
                    System.out.println("You don't have enough coins!");
                }
            } else { //invalid item id, or the item doesn't exist
                System.out.println("Invalid item ID.");
            }
        	
        }catch (Exception e) { //catches anomalies
        	System.out.println("Failed to open: " + e.getMessage());
        }
	}
	
	//method to insert user purchases to the database
	public static void insertPurchase(int user_id, int item_id) {
		try (Connection conn =  PowPatrolConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_purchases (user_id, item_id, purchased_at) VALUES (?, ?, NOW())")) {

			stmt.setInt(1, user_id);
			stmt.setInt(2, item_id);
			stmt.executeUpdate();

			System.out.println("Purchase recorded successfully!");

		} catch (Exception e) {
			System.out.println("Failed to record purchase: " + e.getMessage());
		}
	}
	
	//method to insert to user inventory in the database
	public static void insertToInventory(int user_id, int item_id) {
		try (Connection conn =  PowPatrolConn.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_inventory WHERE user_id = ? AND item_id = ?")) {
			
			ps.setInt(1, user_id);
			ps.setInt(2, item_id);
			
			
			ResultSet rs = ps.executeQuery();

			if (rs.next()) { //chacks if the user inventory has a specific item and adds it 
				PreparedStatement update = conn.prepareStatement("UPDATE user_inventory SET quantity = quantity + 1 WHERE user_id = ? AND item_id = ?");
				update.setInt(1, user_id);
				update.setInt(2, item_id);
				update.executeUpdate();
			}else { //if the user inventory doesn't have the item yet, it inserts the purchased items and the id of user whom purchased it
				PreparedStatement insert = conn.prepareStatement("INSERT INTO user_inventory (user_id, item_id, quantity) VALUES (?, ?, 1)");
				insert.setInt(1, user_id);
				insert.setInt(2, item_id);
				insert.executeUpdate();
			}
			System.out.println("Purchase recorded successfully!");

		} catch (Exception e) {
			System.out.println("Failed to record purchase: " + e.getMessage());
		}
	}
	
	//method to use the item xtra life
	public static boolean useXtra(int user_id) {
		//asks the user if they want to use the xtra life
		System.out.print("Would you like to use your Xtra Life? (y/n): ");
		String user = sc.nextLine();
		//validates user input
		while (!user.equalsIgnoreCase("y")&&!user.equalsIgnoreCase("n")) {
			System.out.print("Invalid input. Please enter 'y' or 'n': ");
	        user = sc.nextLine();
		}
		
		if (user.equalsIgnoreCase("y")) { //user agrees to use the xtra life which in turns updates the quantity in the database
			try (Connection conn = PowPatrolConn.getConnection();
					PreparedStatement check = conn.prepareStatement("SELECT quantity FROM user_inventory WHERE user_id = ? AND item_id = ?")) {
				check.setInt(1, user_id);
				check.setInt(2, 1); 
				ResultSet rs = check.executeQuery();
				
				if (rs.next()&&rs.getInt("quantity")>0) { //if the user still has remaining xtra lives and uses it
					PreparedStatement use = conn.prepareStatement("UPDATE user_inventory SET quantity = quantity - 1 WHERE user_id = ? AND item_id = ?");
					use.setInt(1, user_id);
					use.setInt(2, 1);
					use.executeUpdate();
					System.out.println(Main.YELLOW + "You have used your Xtra life!!" + Main.RESET);
					return true;
				}else { //if the user doesn't have xtra life
					System.out.println(Main.RED + "You don't have an Xtra Life left, sorry huhu" + Main.RESET);
					return false;
				}
				
			}catch (Exception e) { //catches the error for xtra lives
				System.out.println("Error while using Xtra Life: " + e.getMessage());
				return false;
			}
		}else if (user.equalsIgnoreCase("n")) { //if the user doesn't want to use their xtra life
			System.out.println(Main.YELLOW + "You chose not to use an Xtra Life." + Main.RESET);
			return false;
		}
		return true;
	}
	
	//method to show user inventory
	public static void showInventory(int user_id) {
		System.out.println(Main.CYAN + "============================================" + Main.RESET);
		System.out.println(Main.CYAN + "            " + Main.getUser() + "'s INVENTORY    " + Main.RESET);
		System.out.println(Main.CYAN + "============================================" + Main.RESET);
		String sql = "SELECT store_items.item_name, user_inventory.quantity \n"
				+ "FROM user_inventory JOIN store_items ON user_inventory.item_id = store_items.item_id \n"
				+ "WHERE user_inventory.user_id = ?;";
		try (Connection conn =  PowPatrolConn.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, user_id);
			
			ResultSet rs = ps.executeQuery();
			int items = 0;
			
			boolean hasItems = false;
			
			while (rs.next()) {
				items++;
				hasItems = true;
				String item = rs.getString("item_name");
				int quantity = rs.getInt("quantity");
				System.out.println(items + ". " + item + "	||quantity: " + quantity);
			}
			if (!hasItems) { //if user has nothing in the inventory yet
					System.out.println(Main.YELLOW + "Your inventory is empty! 🕸️" + Main.RESET);
			}
						
			System.out.println(Main.CYAN + "============================================" + Main.RESET);
			System.out.println(Main.CYAN + "" + Main.RESET);

		} catch (Exception e) {
			System.out.println("Failed to open inventory: " + e.getMessage());
		}
	}
	
}
