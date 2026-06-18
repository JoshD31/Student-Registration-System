package studentRegistration;
import java.util.ArrayList;

public class StudentRecord extends Student {
	private ArrayList<String> courses;
	
	//constructor
	public StudentRecord(String firstName, String lastName, String IDnum) {
		super(firstName, lastName, IDnum);
		courses = new ArrayList<>();
	}
	 // Adds a course to the student's schedule if not already enrolled
	public void addCourse(String course) {
		if(!courses.contains(course)) {
			courses.add(course);
		} else { 
			System.out.println (course + " is already in schedule");
			}
		}
	//drop a course from the students schedule
	public void dropCourse(String course) {
		if (courses.remove(course)) {
			System.out.println (course + " removed");
		} else {
			System.out.println(course + " not in schedule");
		}
	}
	//gets courses a student is enrolled in
	public ArrayList<String> getCourse(){ return courses; 
	}
	//saves student record formatted for retrieval ex. adam,red,S0001,CS101
	public String toCSV() {
        String line = getFirstName() + "," + getLastName() + "," + getIDnum();
        for (String course : courses) {
            line += "," + course;
        }
        return line;
    }
	
@Override 
	public String toString() {
		return super.toString() + " Course: " + (courses.isEmpty() ? "none" : courses);
	}
	
}
