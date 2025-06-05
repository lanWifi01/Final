//class for the database connection
package Finall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PowPatrolConn {
	
	//database url, user, and password
	static String url = "jdbc:mysql://localhost:3306/pow_patrol";
	static String dbUser = "root";
	static String dbPassword = "";
	
	//reusable method for database connection
	public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }
}
