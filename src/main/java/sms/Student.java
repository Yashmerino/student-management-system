package sms;

public class Student {
	private String name;
	private String surname;
	private short age;
	private String course;
	
	static int id;
	
	static {
		id = 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param The surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the age
	 */
	public short getAge() {
		return age;
	}

	/**
	 * @param The age to set
	 */
	public void setAge(short age) {
		this.age = age;
	}

	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @param The course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * @return the id
	 */
	public static int getId() {
		return id;
	}

	/**
	 * @param The id to set
	 */
	public static void setId(int id) {
		Student.id = id;
		id++;
	}
}
