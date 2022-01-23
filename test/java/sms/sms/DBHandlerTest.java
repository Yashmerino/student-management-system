package sms;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class that tests DBHandler class' methods
 * 
 * @author Artiom
 *
 */
public class DBHandlerTest {

	public DBHandlerTest() {
		// In case I set a password for the database
		// DBHandler.setPassword("password");
	}

	@Before
	public void init() {
		// Create tables in order to test methods that modifies them
		DBHandler.createTable();
	}

	@Test
	public void checkIfTableExistsTest() throws SQLException {

		boolean result = DBHandler.checkIfTableExists("Test");
		assertEquals(false, result);

		result = DBHandler.checkIfTableExists("students");
		assertEquals(true, result);

		result = DBHandler.checkIfTableExists("courses");
		assertEquals(true, result);

		result = DBHandler.checkIfTableExists("faculties");
		assertEquals(true, result);

		result = DBHandler.checkIfTableExists("Students");
		assertEquals(false, result);

		result = DBHandler.checkIfTableExists("coures");
		assertEquals(false, result);

		result = DBHandler.checkIfTableExists("5161521");
		assertEquals(false, result);

		result = DBHandler.checkIfTableExists("=-/*-+");
		assertEquals(false, result);

		result = DBHandler.checkIfTableExists("");
		assertEquals(false, result);

		result = DBHandler.checkIfTableExists("   ");
		assertEquals(false, result);

	}

}
