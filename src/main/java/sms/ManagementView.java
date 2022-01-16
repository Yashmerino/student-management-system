package sms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 * The class that holds the front-end table-management part of the application
 * and manages the actions performed out there
 * 
 * @author Artiom
 *
 */
public class ManagementView {

	/**
	 * The contents of the management window where you read and write students data
	 */
	private JFrame managementFrame;

	/**
	 * The table containing all students
	 */
	private JTable table;

	/**
	 * The field where user should write the student's name
	 */
	private JTextField nameField;
	/**
	 * The field where user should write the student's surname
	 */
	private JTextField surnameField;
	/**
	 * The field where user should write the student's age
	 */
	private JTextField ageField;
	/**
	 * The field where user should write the student's attended course
	 */
	private JTextField courseField;
	/**
	 * The field where user should write the year when the student started attending
	 * the course
	 */
	private JTextField startYearField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagementView window = new ManagementView();
					window.managementFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagementView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		managementFrame = new JFrame();
		managementFrame.setBounds(100, 100, 860, 540);
		managementFrame.setResizable(false);
		managementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		managementFrame.setTitle("Student Management System");
		managementFrame.getContentPane().setLayout(null);

		// The panel where students table is located
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
		tablePanel.setBounds(260, 10, 575, 395);
		managementFrame.getContentPane().add(tablePanel);
		tablePanel.setLayout(null);

		// The scroll pane that allows navigation through table
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setBounds(10, 10, 555, 375);
		tablePanel.add(tableScrollPane);

		// Initializing the table and setting its model
		table = new JTable();
		tableScrollPane.setViewportView(table);
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Name", "Surname", "Age", "Course", "StartYear" }));

		// The panel where all buttons are located
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new LineBorder(new Color(0, 120, 215), 5));
		buttonsPanel.setBackground(UIManager.getColor("Button.background"));
		buttonsPanel.setBounds(10, 415, 825, 80);
		managementFrame.getContentPane().add(buttonsPanel);
		buttonsPanel.setLayout(null);

		// The button to press to exit the application
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		exitButton.setBounds(558, 10, 126, 60);
		buttonsPanel.add(exitButton);

		// The button to press to delete an information from the table
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		deleteButton.setBounds(422, 10, 126, 60);
		buttonsPanel.add(deleteButton);

		// The button to press to update an information in the table
		JButton updateButton = new JButton("Update");
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		updateButton.setBounds(286, 10, 126, 60);
		buttonsPanel.add(updateButton);

		// The button to press to add a student to the table
		JButton addButton = new JButton("Add");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		addButton.setBounds(150, 10, 126, 60);
		buttonsPanel.add(addButton);

		// The panel where user writes information about a student
		JPanel studentPanel = new JPanel();
		studentPanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
		studentPanel.setBounds(10, 10, 240, 395);
		managementFrame.getContentPane().add(studentPanel);
		studentPanel.setLayout(null);

		// The text that informs the user where they have to write the student's name
		JLabel nameText = new JLabel("Name");
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameText.setBounds(10, 22, 67, 19);
		studentPanel.add(nameText);

		// Initializing name text field
		nameField = new JTextField();
		nameField.setBounds(85, 23, 143, 22);
		studentPanel.add(nameField);
		nameField.setColumns(10);

		// The text that informs the user where they have to write the student's surname
		JLabel surnameText = new JLabel("Surname");
		surnameText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		surnameText.setBounds(10, 54, 67, 19);
		studentPanel.add(surnameText);

		// Initializing surname text field
		surnameField = new JTextField();
		surnameField.setColumns(10);
		surnameField.setBounds(85, 51, 143, 22);
		studentPanel.add(surnameField);

		// The text that informs the user where they have to write the student's age
		JLabel ageText = new JLabel("Age");
		ageText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ageText.setBounds(10, 86, 67, 19);
		studentPanel.add(ageText);

		// Initializing age text field
		ageField = new JTextField();
		ageField.setColumns(10);
		ageField.setBounds(85, 83, 143, 22);
		studentPanel.add(ageField);

		// The text that informs the user where they have to write the student's
		// attended course
		JLabel courseText = new JLabel("Course");
		courseText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseText.setBounds(10, 118, 67, 19);
		studentPanel.add(courseText);

		// Initializing course text field
		courseField = new JTextField();
		courseField.setColumns(10);
		courseField.setBounds(85, 115, 143, 22);
		studentPanel.add(courseField);

		// The text that informs the user where they have to write the year when student
		// started attending the course
		JLabel startYearText = new JLabel("StartYear");
		startYearText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		startYearText.setBounds(10, 150, 67, 19);
		studentPanel.add(startYearText);

		// Initializing startYear yext field
		startYearField = new JTextField();
		startYearField.setColumns(10);
		startYearField.setBounds(85, 147, 143, 22);
		studentPanel.add(startYearField);
	}

}
