package studentRegistration;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class RegistrationSystem {

    private SortedLinkedList<Student> mainRoster;
    private InactiveList inactiveList;
    private ArrayList<Course> courses;
    private int studentCount;  // never decrements
    private int activeCount;   // tracks current active students in mainRoster

    //constructor
    public RegistrationSystem() {
        mainRoster = new SortedLinkedList<>();
        inactiveList = new InactiveList();
        courses = new ArrayList<>();
        studentCount = 0;
        activeCount = 0;
        preloadCourses();
        loadData();
    }

   
    // Startup
    private void preloadCourses() {
        courses.add(new Course("CS101", "0101"));
        courses.add(new Course("CS220", "0220"));
        courses.add(new Course("CS313", "0313"));
    }

   
    // ID Generation
    private String generateID() {
        return String.format("S%04d", studentCount);
    }


    // MainRoster Operations
    
    //creates new student, assigns an ID, and add to main roster
    public void addStudentToRoster(String firstName, String lastName) {
        studentCount++;
        activeCount++;
        String assignedID = generateID();
        StudentRecord newStudent = new StudentRecord(firstName, lastName, assignedID);
        mainRoster.insert(newStudent);
        System.out.println(firstName + " " + lastName + " added to roster.");
        System.out.println("Assigned ID: " + assignedID + " :Total active students: " + activeCount);
    }
    //drops all the courses from a student and then moves to inactive list
    public void deleteStudentFromRoster(String firstName, String lastName, String IDnum) {
        StudentRecord found = findInRoster(firstName, lastName, IDnum);
        if (found == null) {
            System.out.println(firstName + " " + lastName + " ID: " + IDnum + " not found in roster.");
            return;
        }
 
        // Copy the list first, can't iterate and modify the same ArrayList
        ArrayList<String> enrolledCourses = new ArrayList<>(found.getCourse());
        for (String courseName : enrolledCourses) {
            for (Course course : courses) {
                if (course.getCourseName().equals(courseName)) {
                    course.deleteStudent(found);//this will also handle moving the next in line in the waitlist
                    break;
                }
            }
        }
        //all courses dropped, now moved to inactive list
        mainRoster.delete(found);
        activeCount--;
        inactiveList.add(found);
        System.out.println(firstName + " " + lastName + " removed from roster and moved to inactive list.");
    }
    //searches main roster for student by name and ID
    public void searchStudent(String firstName, String lastName, String IDnum) {
        StudentRecord target = new StudentRecord(firstName, lastName, IDnum);
        if (mainRoster.search(target)) {
            System.out.println(firstName + " " + lastName + " ID: " + IDnum + " found in roster.");
            return;
        } 
        if (inactiveList.search(target)) {
        	System.out.println(firstName + " " + lastName + " ID: " + IDnum + " found in inactive list.");
        	return;
        }
        System.out.println (firstName + " " + lastName + " ID: " + IDnum + " not found in system.");
    }
    //prints all the students in the main roster in order
    public void displayMainRoster() {
        System.out.println("\n Main Roster: " + activeCount + " students ");
        System.out.println(mainRoster);
    }
    //prints all students in inactive list
    public void displayInactiveList() {
        inactiveList.display();
    }
    //prints all the selected students info
    public void displayStudentInfo(String firstName, String lastName, String IDnum) {
        StudentRecord found = findInRoster(firstName, lastName, IDnum);
        if (found != null) {
            System.out.println("\n Student Info " + found);
        } else {
            System.out.println(firstName + " " + lastName + " (ID: " + IDnum + ") not found in roster.");
        }
    }

 
    // Course Operations
    
    //prints available courses
    public void displayCourses() {
        System.out.println("\n Available Courses ");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName() + " ID:" + courses.get(i).getCourseID());
        }
    }
    //returns course object at selected index
    public Course selectCourse(int index) {
        if (index < 1 || index > courses.size()) {
            System.out.println("Invalid course selection.");
            return null;
        }
        return courses.get(index - 1);
    }
    //adds student to course and moves them out of inactive list if trying to reactivate them
    public void addStudentToCourse(String firstName, String lastName, String IDnum, int courseIndex) {
        StudentRecord found = findInRoster(firstName, lastName, IDnum);
        if (found == null) { // if not in main roster check inactive list
            StudentRecord inactive = inactiveList.find(new StudentRecord(firstName, lastName, IDnum));
            if (inactive != null) {
                //move student back to main roster automatically
                inactiveList.remove(inactive);
                mainRoster.insert(inactive);
                activeCount++;
                System.out.println(firstName + " " + lastName + " reactivated and moved back to main roster.");
                found = inactive;
            } else {
                System.out.println(firstName + " " + lastName + " not found in roster or inactive list. Please add them first.");
                return;
            }
        }

        Course course = selectCourse(courseIndex);//= null if user enters invalid option
        if (course == null) return;
        course.addStudent(found);
    }
    //drops student from a course and moves them to inactive list if they have no courses left
    public void dropStudentFromCourse(String firstName, String lastName, String IDnum, int courseIndex) {
        StudentRecord found = findInRoster(firstName, lastName, IDnum);
        if (found == null) {
            System.out.println(firstName + " " + lastName + " not found in main roster.");
            return;
        }
        Course course = selectCourse(courseIndex);//= null if user enters invalid option
        if (course == null) {
        	return;
        }
        course.deleteStudent(found);
        //if no courses remain then move to inactive list
        if (found.getCourse().isEmpty()) {
            mainRoster.delete(found);
            activeCount--;
            inactiveList.add(found);
            System.out.println(firstName + " " + lastName + " has no remaining courses and has been moved to the inactive list.");
        }
    }
    //prints course info including roster and waitlist
    public void displayCourse(int courseIndex) {
        Course course = selectCourse(courseIndex);
        if (course != null) System.out.println(course);
    }

    
    // File I/O
    //saves data
    public void saveData() {
        saveMainRoster();
        saveWaitlist();
        saveInactiveList();
        saveCount();
        System.out.println("Data saved successfully.");
    }
    //writes main roster to file in csv format
    private void saveMainRoster() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("mainRoster.txt"))) {
            writer.print(mainRoster.toCSV());
        } catch (IOException e) {
            System.out.println("Error saving main roster: " + e.getMessage());
        }
    }
    //writes inactive list to file in csv format
    private void saveInactiveList() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("inactiveList.txt"))) {
            writer.print(inactiveList.toCSV());
        } catch (IOException e) {
            System.out.println("Error saving inactive list: " + e.getMessage());
        }
    }
    
    private void saveWaitlist() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("waitlists.txt"))) {
            for (Course course : courses) {
                String waitlistData = course.getWaitlistAsCSV();
                if (!waitlistData.isEmpty()) {
                    writer.print(waitlistData);
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving waitlists: " + e.getMessage());
        }
    }
    
    //saves student count so ID stays unique after reloading the program
    private void saveCount() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("count.txt"))) {
            writer.println(studentCount);
        } catch (IOException e) {
            System.out.println("Error saving count: " + e.getMessage());
        }
    }
    //loads data from the files
    private void loadData() {
        loadCount();
        loadMainRoster();
        loadWaitlists();
        loadInactiveList();
    }
    //loads student count from count file
    private void loadCount() {
        File file = new File("count.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) studentCount = Integer.parseInt(line.trim());
        } catch (IOException e) {
            System.out.println("Error loading count: " + e.getMessage());
        }
    }
    //loads students from file and restores their course enrollments
    private void loadMainRoster() {
        File file = new File("mainRoster.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");//split by comma so parts[0] = firstname, parts[1]=lastName
                if (parts.length >= 3) {
                    StudentRecord student = new StudentRecord(
                        parts[0].trim(), parts[1].trim(), parts[2].trim());
                    mainRoster.insert(student);
                    activeCount++;
                    //anything past index 2 is a course name
                    for (int i = 3; i < parts.length; i++) {
                        String courseName = parts[i].trim();
                        for (Course course : courses) {
                            if (course.getCourseName().equals(courseName)) {
                                course.addStudent(student);//adds student back to roster/waitlist on reload
                                break;
                            }
                        }
                    }                   
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading main roster: " + e.getMessage());
        }
    }
    //loads inactive students from file
    private void loadInactiveList() {
        File file = new File("inactiveList.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    StudentRecord student = new StudentRecord(
                        parts[0].trim(), parts[1].trim(), parts[2].trim());
                    inactiveList.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inactive list: " + e.getMessage());
        }
    }
    
    private void loadWaitlists() {
        File file = new File("waitlists.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String courseName = parts[0].trim();
                    String firstName  = parts[1].trim();
                    String lastName   = parts[2].trim();
                    String IDnum      = parts[3].trim();

                    // Find the actual StudentRecord object already in the main roster
                    StudentRecord student = findInRoster(firstName, lastName, IDnum);
                    if (student == null) {
                        student = inactiveList.find(new StudentRecord(firstName, lastName, IDnum));
                    }
                    if (student == null) {
                    	continue;
                    }

                    // Find the matching course and enqueue
                    for (Course course : courses) {
                        if (course.getCourseName().equals(courseName)) {
                            course.addToWaitlist(student);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading waitlists: " + e.getMessage());
        }
    }
    
    // Helper
    //finds and returns StudentRecord object from the main roster or null if not found
    public StudentRecord findInRoster(String firstName, String lastName, String IDnum) {
        StudentRecord temp = new StudentRecord(firstName, lastName, IDnum);
        return (StudentRecord) mainRoster.get(temp);
    }
}


