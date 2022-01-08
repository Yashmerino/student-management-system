package sms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Application {

	private JFrame frame;
	private JTextField loginField;
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
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 153, 255));
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JLabel connectText = new JLabel("Connect to a database:");
		connectText.setForeground(new Color(255, 255, 255));
		connectText.setFont(new Font("Tahoma", Font.PLAIN, 50));
		topPanel.add(connectText);
		
		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.CENTER);
		
		JLabel loginText = new JLabel("Login:");
		loginText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel passwordText = new JLabel("Password:");
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		loginField = new JTextField();
		loginField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GroupLayout gl_bottomPanel = new GroupLayout(bottomPanel);
		gl_bottomPanel.setHorizontalGroup(
			gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
					.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_bottomPanel.createSequentialGroup()
							.addGap(93)
							.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordText, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
								.addComponent(loginText))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
								.addComponent(loginField, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_bottomPanel.createSequentialGroup()
							.addGap(243)
							.addComponent(connectButton, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		gl_bottomPanel.setVerticalGroup(
			gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
					.addGap(134)
					.addGroup(gl_bottomPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_bottomPanel.createSequentialGroup()
							.addGroup(gl_bottomPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(loginField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(loginText))
							.addGap(18)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(passwordText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
					.addComponent(connectButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(51))
		);
		bottomPanel.setLayout(gl_bottomPanel);
	}
}
