package sms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
	static JFrame managementFrame;

	/**
	 * The table containing all students
	 */
	static JTable table;

	/**
	 * The field where user should write the student's name
	 */
	static JTextField nameField;

	/**
	 * The field where user should write the student's surname
	 */
	static JTextField surnameField;

	/**
	 * The field where user should write the student's age
	 */
	static JTextField ageField;

	/**
	 * The field where user should write the date when the student started attending
	 * the course
	 */
	static JTextField startedDateField;

	/**
	 * The box that user uses in order to select student's gender
	 */
	static JComboBox genderSelectionBox;

	/**
	 * The box that allows user to select a course for a student
	 */
	static JComboBox courseSelectionBox;

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
		// Clear the selection in the table, to avoid issues with updateDatabase method
		// when cells are selected
		table.clearSelection();
		DBHandler.updateStudents();
	}

	/**
	 * Updates the list of courses
	 */
	private void updateCourses() {
		// Get the lists of courses
		DefaultComboBoxModel courses = new DefaultComboBoxModel(DBHandler.getCourses());
		courseSelectionBox.setModel(courses);
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
				new String[] { "ID", "Name", "Surname", "Age", "Gender", "Course", "Started", "Graduation" }) {
			boolean[] columnEditables = new boolean[] { false, true, true, true, true, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Creating a sorter for the table
		TableRowSorter tableSorter = new TableRowSorter(table.getModel());
		table.setRowSorter(tableSorter);

		// Creating a Table Listener to detect cell modifications
		table.getModel().addTableModelListener(new TableModelListener() {

			// Actions to perform when a cell has been edited
			public void tableChanged(TableModelEvent e) {
				if (!DBHandler.updateDatabase()) {
					JOptionPane.showMessageDialog(managementFrame,
							"An error has occured!\nCheck your input one more time!", "Student Management System",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// The panel where all buttons are located
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new LineBorder(new Color(0, 120, 215), 5));
		buttonsPanel.setBackground(UIManager.getColor("Button.background"));
		buttonsPanel.setBounds(10, 415, 825, 80);
		managementFrame.getContentPane().add(buttonsPanel);

		// The button to press to delete an information from the table
		JButton deleteButton = new JButton("Delete");
		deleteButton.setName("deleteButton");

		// Actions to perform when "delete" button clicked
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If no row has been selected
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(managementFrame, "No student has been selected!",
							"Student Management System", JOptionPane.ERROR_MESSAGE);
				} else {
					// Asking the user if they are sure about that
					if (JOptionPane.showConfirmDialog(managementFrame,
							"Deleting a student from the table may result in losing important information.\nAre you sure?",
							"Student Management System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						if (DBHandler.deleteStudent()) {
							JOptionPane.showMessageDialog(managementFrame, "Student successfully removed!",
									"Student Management System", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(managementFrame,
									"Something went wrong!\nTry restarting the application!",
									"Student Management System", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		// The button to press to add a student to the table
		JButton addButton = new JButton("Add");
		addButton.setName("addButton");

		// Actions to perform when "add" button clicked
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();

				if (DBHandler.getFaculties().length == 0) {
					JOptionPane.showMessageDialog(managementFrame,
							"You can't add a student yet!\nAdd a faculty and a course first!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				// If one of the fields are empty warn user about that
				if (nameField.getText().equals("") || surnameField.getText().equals("") || ageField.getText().equals("")
						|| startedDateField.getText().equals("")) {

					JOptionPane.showMessageDialog(managementFrame, "Please fill in all the fields!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						// Check if the written data is written correctly according to the format
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						format.setLenient(false);
						format.parse(startedDateField.getText());
					} catch (ParseException ex) {
						ex.printStackTrace();

						JOptionPane.showMessageDialog(managementFrame,
								"Please type a correct data according to the format YYYY-MM-DD!", "Error",
								JOptionPane.ERROR_MESSAGE);

						return;
					}

					if (DBHandler.addStudent()) {
						JOptionPane.showMessageDialog(managementFrame, "The student has been added successfully!",
								"Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nCheck the credentials!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});
		buttonsPanel.setLayout(new GridLayout(0, 5, 0, 0));

		addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonsPanel.add(addButton);

		// The button to press to update an information in the table
		JButton updateButton = new JButton("Update");

		// Actions to perform when "update" button clicked
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();
				DBHandler.updateStudents();
			}
		});

		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonsPanel.add(updateButton);
		buttonsPanel.add(deleteButton);

		// The button to press to exit the application
		JButton exitButton = new JButton("Exit");
		exitButton.setName("exitButton");

		// Actions to perform when "exit" button clicked
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(managementFrame, "Are you sure?", "Student Management System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					managementFrame.dispose();
					System.exit(0);
				}
			}
		});

		// The button that user have to press in order to disconnect from the current
		// database
		JButton disconnectButton = new JButton("Disconnect");
		disconnectButton.setName("disconnectButton");

		// Actions to perform when "disconnect" button has been clicked
		disconnectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(managementFrame,
						"Do you want to disconnect from the current database?", "Student Management System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					// Return back to the connection window
					ConnectionView.main(null);
					managementFrame.dispose();
				}
			}
		});

		disconnectButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonsPanel.add(disconnectButton);

		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonsPanel.add(exitButton);

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
		nameField.setName("nameField");
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
		surnameField.setName("surnameField");
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
		ageField.setName("ageField");
		ageField.setColumns(10);
		ageField.setBounds(85, 83, 143, 22);
		studentPanel.add(ageField);

		// The text that informs the user where they have to write the student's
		// attended course
		JLabel courseText = new JLabel("Course");
		courseText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseText.setBounds(10, 156, 67, 19);
		studentPanel.add(courseText);

		// The text that informs the user where they have to write the date when student
		// started attending the course
		JLabel startedDateText = new JLabel("Started");
		startedDateText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		startedDateText.setBounds(10, 188, 67, 19);
		studentPanel.add(startedDateText);

		// Initializing startedDate text field
		startedDateField = new JTextField();
		startedDateField.setName("startedDataField");
		startedDateField.setColumns(10);
		startedDateField.setBounds(85, 185, 143, 22);
		startedDateField.setText("YYYY-MM-DD");
		studentPanel.add(startedDateField);

		// The text that informs the user where they have to select student's gender
		JLabel genderText = new JLabel("Gender");
		genderText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		genderText.setBounds(10, 120, 67, 19);
		studentPanel.add(genderText);

		// Initializing the box where user selects the student's gender
		genderSelectionBox = new JComboBox();
		genderSelectionBox.setName("genderSelectionBox");
		genderSelectionBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		genderSelectionBox.setModel(new DefaultComboBoxModel(sms.Gender.values()));
		genderSelectionBox.setBounds(85, 120, 143, 22);
		studentPanel.add(genderSelectionBox);

		// Button that adds a new faculty
		JButton addFacultyButton = new JButton("Add Faculty");
		addFacultyButton.setName("addFacultyButton");

		// Actions to perform when "add faculty" button clicked
		addFacultyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String facultyName = "";

				facultyName = JOptionPane.showInputDialog(managementFrame, "Type the name of the faculty");

				if (facultyName == null || facultyName.equals("")) {
					JOptionPane.showMessageDialog(managementFrame,
							"The faculty hasn't been added!\nPlease type a name for it!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (DBHandler.checkIfElementExists(DBHandler.getFacultiesTable(), facultyName)) {
						JOptionPane.showMessageDialog(managementFrame,
								"The faculty hasn't been added!\nThe faculty " + facultyName + " already exists!",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						if (DBHandler.addFaculty(facultyName)) {
							JOptionPane.showMessageDialog(managementFrame, "The faculty has been added successfully!",
									"Success", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(managementFrame, "The faculty hasn't been added!\nTry again!",
									"Success", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		addFacultyButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addFacultyButton.setBounds(10, 220, 220, 30);
		studentPanel.add(addFacultyButton);

		// Button that adds a new course
		JButton addCourseButton = new JButton("Add Course");
		addCourseButton.setName("addCourseButton");
		addCourseButton.addActionListener(new ActionListener() {

			// Actions to perform when "add course" button clicked
			public void actionPerformed(ActionEvent e) {
				// If there are no faculties there is no way to add a course
				if (DBHandler.getFaculties().length == 0) {
					JOptionPane.showMessageDialog(managementFrame, "You can't add a course!\nAdd a faculty first",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String courseName = "", faculty = "";
				int duration = 0;

				courseName = JOptionPane.showInputDialog(managementFrame, "Type the name of the course");

				// If no name has been written for the course
				if (courseName == null || courseName.equals("")) {
					JOptionPane.showMessageDialog(managementFrame,
							"The course hasn't been added!\nPlease type a name for it!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					String[] faculties = DBHandler.getFaculties();
					faculty = (String) JOptionPane.showInputDialog(null, "Students Management System",
							"Choose the faculty for the course", JOptionPane.QUESTION_MESSAGE, null, faculties,
							faculties[0]);

					// If no faculty has been selected for the course
					if (faculty == null || faculty.equals("")) {
						JOptionPane.showMessageDialog(managementFrame,
								"The course hasn't been added!\nPlease select a faculty for it or add a new one!",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						// In case the user types letters for the duration
						try {
							duration = Integer.parseInt(JOptionPane.showInputDialog(managementFrame,
									"Type the duration of the course(months)"));
						} catch (NumberFormatException ex) {
							ex.printStackTrace();

							JOptionPane.showMessageDialog(managementFrame,
									"The course hasn't been added!\nPlease type the duration of the course without using other characters than digits!",
									"Error", JOptionPane.ERROR_MESSAGE);

							return;
						}

						if (DBHandler.checkIfElementExists(DBHandler.getCoursesTable(), courseName)) {
							JOptionPane.showMessageDialog(managementFrame,
									"The course hasn't been added!\nThe course " + courseName + " already exists!",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							if (DBHandler.addCourse(courseName, faculty, duration)) {
								JOptionPane.showMessageDialog(managementFrame,
										"The course has been added successfully!", "Success",
										JOptionPane.INFORMATION_MESSAGE);

								updateCourses();
							} else {
								JOptionPane.showMessageDialog(managementFrame,
										"The course hasn't been added!\nTry again!", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});

		addCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addCourseButton.setBounds(10, 260, 220, 30);
		studentPanel.add(addCourseButton);

		// Initializing the course selection box
		courseSelectionBox = new JComboBox();
		courseSelectionBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseSelectionBox.setBounds(85, 154, 143, 22);
		updateCourses();
		studentPanel.add(courseSelectionBox);

		// Button that allows to delete a faculty
		JButton deleteFacultyButton = new JButton("Delete Faculty");
		deleteFacultyButton.setName("deleteFacultyButton");

		// Actions to perform when "Delete Faculty" button clicked
		deleteFacultyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();

				String faculty = (String) JOptionPane.showInputDialog(null, "Students Management System",
						"Choose the faculty to delete", JOptionPane.QUESTION_MESSAGE, null, DBHandler.getFaculties(),
						DBHandler.getFaculties()[0]);

				// If no faculty has been selected
				if (faculty == null) {
					return;
				}

				// If there are students attending the courses in this faculty
				if (DBHandler.getNumberOfCourses(faculty) > 0) {
					if (JOptionPane.showConfirmDialog(managementFrame,
							"You can't delete a faculty that has courses!\nDo you wish to delete all the courses and students attending these courses in the "
									+ faculty + " faculty?",
							"Student Management System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						if (DBHandler.deleteFacultyCourses(faculty)) {
							JOptionPane.showMessageDialog(managementFrame,
									"The courses in " + faculty + " faculty have been deleted successfully!", "Success",
									JOptionPane.INFORMATION_MESSAGE);

							if (DBHandler.deleteFaculty(faculty)) {
								JOptionPane.showMessageDialog(managementFrame,
										"The " + faculty + " faculty has been deleted successfully!", "Success",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nTry Again!",
										"Error", JOptionPane.ERROR_MESSAGE);
							}

						} else {
							JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nTry Again!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					if (DBHandler.deleteFaculty(faculty)) {
						JOptionPane.showMessageDialog(managementFrame,
								"The " + faculty + " faculty has been deleted successfully!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nTry Again!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				updateCourses();
			}
		});

		deleteFacultyButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deleteFacultyButton.setBounds(10, 300, 220, 30);
		studentPanel.add(deleteFacultyButton);

		// Button that allows to delete a course
		JButton deleteCourseButton = new JButton("Delete Course");
		deleteCourseButton.setName("deleteCourseButton");

		// Actions to perform when "Delete Course" button clicked
		deleteCourseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				table.clearSelection();

				String course = (String) JOptionPane.showInputDialog(null, "Students Management System",
						"Choose the course to delete", JOptionPane.QUESTION_MESSAGE, null, DBHandler.getCourses(),
						DBHandler.getCourses()[0]);

				// If no course has been selected
				if (course == null) {
					return;
				}

				// If there are students attending the course
				if (DBHandler.getNumberOfAttendees(DBHandler.getCoursesTable(), course) > 0) {
					if (JOptionPane.showConfirmDialog(managementFrame,
							"You can't delete a course that is attended by students!\nDo you wish to delete all the students attending the "
									+ course + " course?",
							"Student Management System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						if (DBHandler.deleteCourseAttendees(course)) {
							JOptionPane.showMessageDialog(managementFrame,
									"The students attending " + course + " course have been deleted successfully!",
									"Success", JOptionPane.INFORMATION_MESSAGE);

							if (DBHandler.deleteCourse(course)) {
								JOptionPane.showMessageDialog(managementFrame,
										"The " + course + " course has been deleted successfully!", "Success",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nTry Again!",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nTry Again!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					if (DBHandler.deleteCourse(course)) {
						JOptionPane.showMessageDialog(managementFrame,
								"The " + course + " course has been deleted successfully!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(managementFrame, "Something went wrong!\nTry Again!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				updateCourses();
			}
		});

		deleteCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deleteCourseButton.setBounds(10, 340, 220, 30);
		studentPanel.add(deleteCourseButton);
	}
}
