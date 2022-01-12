package sms;

/**
 * The class that holds and manages the information about students
 * 
 * @author Artiom
 *
 */
public class Student {
	/**
	 * The name of the student
	 */
	private String name;
	/**
	 * The surname of the student
	 */
	private String surname;
	/**
	 * The age of the student
	 */
	private short age;
	/**
	 * The course that the student attends
	 */
	private String course;
	/**
	 * The year when the student started the course
	 */
	private short gradeYear;
	/**
	 * The unique id of the student
	 */
	int id;
	/**
	 * The counter that is increased by one everytime a new instance is created,
	 * used for ID system
	 */
	static int counter;

	/**
	 * Counter is set to 0 initially
	 */
	static {
		counter = 0;
	}

	/**
	 * Default constructor
	 */
	Student() {
		// The unique id of the student is set to value of the counter
		this.id = counter;
		// The counter after that is increased by one
		counter++;
	}

	/**
	 * @return The name of the student
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param The name to set to the student
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The surname of the student
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param The surname to set to the student
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return The age of the student
	 */
	public short getAge() {
		return age;
	}

	/**
	 * @param The age to set to the student
	 */
	public void setAge(short age) {
		this.age = age;
	}

	/**
	 * @return The course that the student attends
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @param The course to set to the student
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * @return The id of the student
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The year when student started the course
	 */
	public short getGradeYear() {
		return gradeYear;
	}

	/**
	 * @param The year when student started attending the course
	 */
	public void setGradeYear(short gradeYear) {
		this.gradeYear = gradeYear;
	}
}
