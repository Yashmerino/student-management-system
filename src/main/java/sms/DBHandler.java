package sms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	public DBHandler() {

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
	 * Creates a table of students
	 * 
	 * @param tableName - The table's desired name
	 */
	public static boolean createTable(final String tableName) {
		try {
			Connection connection = DriverManager.getConnection(DB_URL, login, password);
			Statement statement = connection.createStatement();

			// Check if a table with tableName name already exists
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			while (rs.next()) {
				if (rs.getString(3).equals(tableName)) {
					JOptionPane.showMessageDialog(new JFrame(), "Table " + tableName + " already exists. Reading data.",
							"Success", JOptionPane.INFORMATION_MESSAGE);

					return true;
				}
			}

			String sqlScript = "create table " + tableName + "(ID INTEGER not NULL, " + " First varchar(255), "
					+ "Last varchar(255), " + "Age INTEGER, " + "Course varchar(255), " + "StartYear INTEGER, "
					+ "PRIMARY KEY ( id ))";

			statement.executeUpdate(sqlScript);

			// Return true if no exception has been thrown
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

			// Return false if an exception has been thrown
			return false;
		}
	}
}
