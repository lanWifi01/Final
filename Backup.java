//class to backup the database
package Finall;

public class Backup {
	
	//backup method for game results
	public static void backupGameRes() {
		String dbName = "pow_patrol";
		String backupPath = "C:\\xampp\\backup.sql";
		String dbTables = "users game_results leaderboard";
		
		String command = "C:\\xampp\\mysql\\bin\\mysqldump --quick --single-transaction -u " + PowPatrolConn.dbUser + 
				" -p" + PowPatrolConn.dbPassword + " " + dbName + " " + dbTables + " -r " + backupPath;
		
		try {
			Process process = Runtime.getRuntime().exec(command);
			
			int complete = process.waitFor();
			
			if (complete == 0) {
				System.out.println(Main.GREEN + "Backup Complete!!" + Main.RESET);
			}else {
				System.out.println(Main.RED + "Backup Failed!!" + Main.RESET);
			}
		}catch(Exception e) { 
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	//backup method for users interaction with the store and user's inventory items
	public static void backupStore() {
		String dbName = "pow_patrol";
		String backupPath = "C:\\xampp\\backup.sql";
		String dbTables = "user_purchases user_inventory";
		
		String command = "C:\\xampp\\mysql\\bin\\mysqldump --quick --single-transaction -u " + PowPatrolConn.dbUser + 
				" -p" + PowPatrolConn.dbPassword + " " + dbName + " " + dbTables + " -r " + backupPath;
		
		try {
			Process process = Runtime.getRuntime().exec(command);
			
			int complete = process.waitFor();
			
			if (complete == 0) {
				System.out.println(Main.GREEN + "Backup Complete!!" + Main.RESET);
			}else {
				System.out.println(Main.RED + "Backup Failed!!" + Main.RESET);
			}
		}catch(Exception e) { 
			System.out.println("Error: " + e.getMessage());
		}
	}
}
