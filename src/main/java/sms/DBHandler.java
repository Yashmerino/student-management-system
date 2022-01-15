package sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class that allows access to a database for reading and writing data
 * purposes
 * 
 * @author Artiom
 *
 */
public class DBHandler {
	/**
	 * Login to connect to the database
	 */
	private static String login;
	/**
	 * Password to connect to the database
	 */
	private static String password;
	/**
	 * Database URL, DB's name must be "studentsdb"
	 */
	static final String DB_URL = "jdbc:mysql://localhost:3306/studentsdb";

	/**
	 * Default constructor
	 */
	DBHandler() {

	}

	/**
	 * Static initializers
	 */
	static {
		login = "root";
	}

	/**
	 * @return The login to connect to the database
	 */
	public static String getLogin() {
		return login;
	}

	/**
	 * @param login - The login to set to connect to the database
	 */
	public static void setLogin(final String login) {
		DBHandler.login = login;
	}

	/**
	 * @return The password to connect to the database
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @param password - The password to set to connect to the database
	 */
	public static void setPassword(final String password) {
		DBHandler.password = password;
	}

	/**
	 * 
	 * @param tableName - The table's desired name
	 */
	public static boolean createTable(final String tableName) {
		try {
			Connection connection = DriverManager.getConnection(DB_URL, login, password);
			Statement statement = connection.createStatement();

			String sqlScript = "create table " + tableName + "(id INTEGER not NULL, " + " First varchar(255), "
					+ "Last varchar(255), " + "Age INTEGER, " + "Course varchar(255), " + "StartYear INTEGER, "
					+ "PRIMARY KEY ( id ))";

			// Return true if no exception has been thrown
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

			// Return false if an exception has been thrown
			return false;
		}
	}
}
