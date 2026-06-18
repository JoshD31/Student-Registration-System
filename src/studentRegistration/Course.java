package studentRegistration;

public class Course {
	
	private static final int maxRoster = 10;//change to smaller number for easier testing
	
	 private String courseName;
	 private String courseID;
	 private SortedLinkedList<Student> roster;
	 private Queue<Student> waitlist;
	 private int studentCount;
	 
	 //constructor
	 public Course(String courseName, String courseID) {
	        this.courseName = courseName;
	        this.courseID = courseID;
	        this.roster = new SortedLinkedList<>();
	        this.waitlist = new Queue<>();
	        this.studentCount = 0;
	    }
	 //getters
	 public String getCourseName() { return courseName; }
	 public String getCourseID()   { return courseID; }
	 public int getStudentCount()  { return studentCount; }
	 
	 //add student to roster if space is available otherwise moves to waitlist
	 public void addStudent(StudentRecord student) {
		 if(roster.search(student)) {
			 System.out.println ("Student already in course.");
			 return;
		 }
	        if (studentCount < maxRoster) {
	        	roster.insert(student);
	            student.addCourse(courseName);
	            studentCount++;
	            System.out.println(student.getFirstName() + " " + student.getLastName() + " added to " + courseName);
	        } else if (!waitlist.isFull()){
	        	waitlist.enQ(student);
	        	System.out.println(student.getFirstName() + " " + student.getLastName() + " added to waitlist for " + courseName);
	        } else {
	        	System.out.println("Cannot add " + student.getFirstName() + " " + student.getLastName() + ". Roster and waitlist are both full.");
	        }
	 }

	 //adds student directly to roster on reload — skips print statements
	 public void addStudentSilent(StudentRecord student) {
	     if (roster.search(student)) return;
	     if (studentCount < maxRoster) {
	         roster.insert(student);
	         studentCount++;
	     }
	 }

	 //removes student from roster and adds next in line in waitlist if there is anyone
	 public void deleteStudent(StudentRecord student) {
	        if (roster.search(student)) {
	            roster.delete(student);
	            student.dropCourse(courseName);
	            studentCount--;
	            System.out.println(student.getFirstName() + " " + student.getLastName() + " removed from " + courseName);
	            if (!waitlist.isEmpty()) {
	                StudentRecord nextInLine = (StudentRecord) waitlist.deQ();
	                roster.insert(nextInLine);
	                nextInLine.addCourse(courseName);
	                studentCount++;
	                System.out.println(nextInLine.getFirstName() + " " + nextInLine.getLastName() + " moved from waitlist into " + courseName);
	            }
	        } else {
	            System.out.println(student.getFirstName() + " " + student.getLastName() + " not found in " + courseName);
	        }
	    }
	 
	//adds student directly to waitlist (used when reloading program)
	 public void addToWaitlist(Student student) {
	     if (!waitlist.isFull()) {
	         waitlist.enQ(student);
	     } else {
	         System.out.println("Waitlist for " + courseName + " is full. Cannot add " + student.getFirstName() + " " + student.getLastName() + "\nerror on reload");
	     }
	 }
	 
	 public String getWaitlistAsCSV() {
		    if (waitlist.isEmpty()) return "";    
		    return waitlist.toCourseCSV(courseName);
		}
	 
	 @Override
	    public String toString() {
	        return "Course: " + courseName + " " + courseID + "\nStudents enrolled: " + studentCount + "\nRoster:\n" + roster + "\nWaitlist:\n" + waitlist;
	    }
}