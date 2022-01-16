package sms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
	static final String DB_URL;

	/**
	 * The table's name on which actions are performed
	 */
	private static String tableName;

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
		DB_URL = "jdbc:mysql://localhost:3306/studentsdb";
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
	 * @return The table's name on which actions are performed
	 */
	public static String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName - the name to set to the table
	 */
	public static void setTableName(String tableName) {
		DBHandler.tableName = tableName;
	}

	/**
	 * Creates a table of students
	 * 
	 * @param tableName - The table's desired name
	 * @return true if everything went fine, and false otherwise
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

			String sqlScript = "create table " + tableName + "(ID INTEGER not NULL AUTO_INCREMENT, "
					+ " Name varchar(255), " + "Surname varchar(255), " + "Age INTEGER, " + "Gender varchar(6), "
					+ "Course varchar(255), " + "StartYear INTEGER, " + "PRIMARY KEY ( id ))";

			statement.executeUpdate(sqlScript);

			// Return true if no exception has been thrown
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

			// Return false if an exception has been thrown
			return false;
		}
	}

	/**
	 * Adds a new student to the table
	 * 
	 * @return true if everything went fine, and false otherwise
	 */
	public static boolean addStudent() {
		try {
			Connection connection = DriverManager.getConnection(DB_URL, login, password);
			PreparedStatement preparedStatement = connection.prepareStatement("insert into " + tableName
					+ " (Name, Surname, Age, Gender, Course, StartYear) values " + "(?, ?, ?, ?, ?, ?)");

			preparedStatement.setString(1, ManagementView.nameField.getText());
			preparedStatement.setString(2, ManagementView.surnameField.getText());
			preparedStatement.setInt(3, Integer.parseInt(ManagementView.ageField.getText()));
			preparedStatement.setString(4, ManagementView.genderSelectionBox.getSelectedItem().toString());
			preparedStatement.setString(5, ManagementView.courseField.getText());
			preparedStatement.setInt(6, Integer.parseInt(ManagementView.startYearField.getText()));

			preparedStatement.executeUpdate();

			// Return true if no exception has been thrown
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

			// Return false if an exception has been thrown
			return false;
		}
	}

	/**
	 * Updates the contents of the table
	 * 
	 * @return true if everything went fine, and false otherwise
	 */
	public static boolean update() {
		int howManyColumns = 0, currentColumn = 0;

		try {
			Connection connection = DriverManager.getConnection(DB_URL, login, password);
			PreparedStatement preparedStatement = connection.prepareStatement("select * from " + tableName);

			// Reading data from table
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData rsmData = resultSet.getMetaData();

			howManyColumns = rsmData.getColumnCount();

			DefaultTableModel recordTable = (DefaultTableModel) ManagementView.table.getModel();
			recordTable.setRowCount(0);

			while (resultSet.next()) {
				Vector columnData = new Vector();

				for (currentColumn = 1; currentColumn <= howManyColumns; currentColumn++) {
					columnData.add(resultSet.getString("ID"));
					columnData.add(resultSet.getString("Name"));
					columnData.add(resultSet.getString("Surname"));
					columnData.add(resultSet.getString("Age"));
					columnData.add(resultSet.getString("Gender"));
					columnData.add(resultSet.getString("Course"));
					columnData.add(resultSet.getString("StartYear"));
				}

				recordTable.addRow(columnData);
			}

			// Return true if no exception has been thrown
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

			// Return false if exception has been thrown
			return false;
		}
	}

}
