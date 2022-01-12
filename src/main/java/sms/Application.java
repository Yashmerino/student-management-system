package sms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The class that manages the front-end part of the application and holds the
 * actions performed out there
 * 
 * @author Artiom
 *
 */
public class Application {

	/**
	 * The contents of the application
	 */
	private JFrame frame;
	/**
	 * The text field that stores the login the user has written
	 */
	private JTextField loginField;
	/**
	 * The text field that stores the password the user has written
	 */
	private JTextField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Student Management System");

		// The blue-colored panel in the top part of the application
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 155, 255));
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);

		// The text that informs the user that they have to connect to a database
		JLabel connectText = new JLabel("Connect to a database:");
		connectText.setForeground(new Color(255, 255, 255));
		connectText.setFont(new Font("Tahoma", Font.PLAIN, 50));
		topPanel.add(connectText);

		// The panel in the bottom part of the application
		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.CENTER);

		// The text that informs the user where they have to type the login
		JLabel loginText = new JLabel("Login:");
		loginText.setBounds(95, 135, 55, 25);
		loginText.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// The text that informs the user where they have to type the password
		JLabel passwordText = new JLabel("Password:");
		passwordText.setBounds(95, 175, 100, 25);
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// Initializes the text field where user writes the login
		loginField = new JTextField();
		loginField.setBounds(200, 140, 330, 20);
		loginField.setColumns(10);

		// Initializes the text field where user writes the password
		passwordField = new JTextField();
		passwordField.setBounds(200, 180, 330, 20);
		passwordField.setColumns(10);

		// The button to press after the login and password were written
		JButton connectButton = new JButton("Connect");
		connectButton.setBounds(243, 288, 138, 33);
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bottomPanel.setLayout(null);
		bottomPanel.add(passwordText);
		bottomPanel.add(loginText);
		bottomPanel.add(passwordField);
		bottomPanel.add(loginField);
		bottomPanel.add(connectButton);
	}
}
