package studentRegistration;

public class InactiveList {

    private SortedLinkedList<Student> inactiveStudents;
    //constructor
    public InactiveList() {
        inactiveStudents = new SortedLinkedList<>();
    }
    //adds student to inactive list
    public void add(Student student) {
        inactiveStudents.insert(student);
        System.out.println(student.getFirstName() + " " + student.getLastName() + " moved to inactive list.");
    }
    //returns true if student is found in inactive list
    public boolean search(Student student) {
        return inactiveStudents.search(student);
    }

    // Returns the actual StudentRecord object from the inactive list, used to reactivate students from inactive list
    public StudentRecord find(Student student) {
        return (StudentRecord) inactiveStudents.get(student);
    }

    // Removes student from the inactive list
    public void remove(Student student) {
        inactiveStudents.delete(student);
    }
    //prints all inactive students
    public void display() {
        if (inactiveStudents.isEmpty()) {
            System.out.println("Inactive list is empty.");
        } else {
            System.out.println("\n Inactive Students ");
            System.out.println(inactiveStudents);
        }
    }
    // Returns the inactive list as a CSV string for saving to file
    public String toCSV() {
        return inactiveStudents.toCSV();
    }

    @Override
    public String toString() {
        return inactiveStudents.toString();
    }
}