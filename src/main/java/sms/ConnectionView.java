package sms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * The class that holds the front-end connection part of the application and
 * manages the actions performed out there
 * 
 * @author Artiom
 *
 */
public class ConnectionView {

	/**
	 * The contents of the connection window where you have to connect to a database
	 */
	private JFrame connectionFrame;

	/**
	 * The text field that stores the login the user has written
	 */
	private JTextField loginField;
	private JPasswordField passwordField;
	private JTextField databaseUrlField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionView window = new ConnectionView();
					window.connectionFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectionView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connectionFrame = new JFrame();
		connectionFrame.setBounds(100, 100, 640, 480);
		connectionFrame.setResizable(false);
		connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connectionFrame.setTitle("Student Management System");

		// The blue-colored panel in the top part of the application
		JPanel topPanel = new JPanel();
		topPanel.setBackground(SystemColor.textHighlight);
		connectionFrame.getContentPane().add(topPanel, BorderLayout.NORTH);

		// The text that informs the user that they have to connect to a database
		JLabel connectText = new JLabel("Connect to a database:");
		connectText.setForeground(new Color(255, 255, 255));
		connectText.setFont(new Font("Tahoma", Font.PLAIN, 50));
		topPanel.add(connectText);

		// The panel in the bottom part of the application
		JPanel bottomPanel = new JPanel();
		connectionFrame.getContentPane().add(bottomPanel, BorderLayout.CENTER);

		// The text that informs the user where they have to type the login
		JLabel loginText = new JLabel("Login:");
		loginText.setBounds(68, 134, 55, 25);
		loginText.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// The text that informs the user where they have to type the password
		JLabel passwordText = new JLabel("Password:");
		passwordText.setBounds(68, 174, 100, 25);
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// Initializes the text field where user writes the login
		loginField = new JTextField();
		loginField.setBounds(223, 141, 330, 20);
		loginField.setColumns(10);

		// Initializes the text field where user writes the password
		passwordField = new JPasswordField();
		passwordField.setBounds(223, 181, 330, 20);

		// The field where user should write the database url
		databaseUrlField = new JTextField();
		databaseUrlField.setText("jdbc:mysql://localhost:3306/studentsdb");
		databaseUrlField.setColumns(10);
		databaseUrlField.setBounds(223, 96, 330, 20);

		// The text that informs user where they have to write database url
		JLabel databaseUrlText = new JLabel("Database URL:");
		databaseUrlText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		databaseUrlText.setBounds(68, 91, 130, 25);

		// The button to press after the login and password were written
		JButton connectButton = new JButton("Connect");
		connectButton.setBounds(240, 290, 140, 35);
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// Execute connection and create a table when "Connect" button pressed
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If one of the fields are empty then warn user about it
				if (loginField.getText().equals("") || passwordField.getText().equals("")
						|| databaseUrlField.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in all the empty fields!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Get login, password and database url from fields and set them for database
					// handler
					DBHandler.setLogin(loginField.getText());
					DBHandler.setPassword(passwordField.getText());
					DBHandler.setDatabaseUrl(databaseUrlField.getText());

					// If table has\hasn't been successfully created then inform the user about that
					if (DBHandler.createTables()) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Connection with the database has been successfully established!", "Success",
								JOptionPane.INFORMATION_MESSAGE);

						// Open a new window where you can manage the table and close the old one
						ManagementView.main(null);
						connectionFrame.dispose();

					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Connection with the database hasn't been established!\nPlease check your credentials!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		bottomPanel.setLayout(null);
		bottomPanel.add(passwordText);
		bottomPanel.add(loginText);
		bottomPanel.add(passwordField);
		bottomPanel.add(loginField);
		bottomPanel.add(connectButton);
		bottomPanel.add(databaseUrlText);
		bottomPanel.add(databaseUrlField);

	}
}
