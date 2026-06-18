//package studentRegistration;
//
//public class Test {
//
//	public static void main(String[] args) {
//		Student S1 = new Student("Mary", "Doe", "D000123");
//		Student S2 = new Student("John", "Smith", "S000124");
//        Student S3 = new Student("John", "Smith", "S000134");
//        Student S4 = new Student("Mary", "Smith", "S000145");
//        Student S5 = new Student("Mary", "Doe",   "D000123");
//        
//        System.out.println("equals() Tests");
//        System.out.println("s1.equals(s5) [should be true]:  " + S1.equals(S5));//same IDnum
//        System.out.println("s1.equals(s2) [should be false]: " + S1.equals(S2));
//        System.out.println("s2.equals(s3) [should be false]: " + S2.equals(S3));//different IDnum
//
//        System.out.println("\ncompareTo() Tests");
//        //negative and positive values are from comparing ASCII value of each letter in names
//        System.out.println("Doe vs Smith [should be negative]: " + S1.compareTo(S2));
//        System.out.println("Smith vs Doe [should be positive]: " + S2.compareTo(S1));
//        System.out.println("John vs Mary [should be positive]: " + S4.compareTo(S3));
//        System.out.println("s2 vs s3 same last+first [should be 0]: " + S2.compareTo(S3)); // Same name, different ID
//
//        System.out.println("\nStudentRecord Test");
//        StudentRecord sr = new StudentRecord("Mary", "Smith", "S000145");
//        sr.addCourse("CS313");
//        sr.addCourse("CS220");
//        sr.addCourse("Mus201");
//        sr.addCourse("CS313"); // Duplicate — should warn
//        sr.dropCourse("CS220");
//        System.out.println(sr);
//
//	}
//
//}
package studentRegistration;

public class Test {

    public static void main(String[] args) {

        // =====================
        // Original Student tests
        // =====================
        Student S1 = new Student("Mary", "Doe", "D000123");
        Student S2 = new Student("John", "Smith", "S000124");
        Student S3 = new Student("John", "Smith", "S000134");
        Student S4 = new Student("Mary", "Smith", "S000145");
        Student S5 = new Student("Mary", "Doe",   "D000123");

        System.out.println("=== equals() Tests ===");
        System.out.println("s1.equals(s5) [should be true]:  " + S1.equals(S5));
        System.out.println("s1.equals(s2) [should be false]: " + S1.equals(S2));
        System.out.println("s2.equals(s3) [should be false]: " + S2.equals(S3));

        System.out.println("\n=== compareTo() Tests ===");
        System.out.println("Doe vs Smith [should be negative]: " + S1.compareTo(S2));
        System.out.println("Smith vs Doe [should be positive]: " + S2.compareTo(S1));
        System.out.println("John vs Mary [should be positive]: " + S4.compareTo(S3));
        System.out.println("s2 vs s3 same last+first [should be 0]: " + S2.compareTo(S3));

        System.out.println("\n=== StudentRecord Tests ===");
        StudentRecord sr = new StudentRecord("Mary", "Smith", "S000145");
        sr.addCourse("CS313");
        sr.addCourse("CS220");
        sr.addCourse("Mus201");
        sr.addCourse("CS313"); // duplicate — should warn
        sr.dropCourse("CS220");
        sr.dropCourse("Eng101"); // not in schedule — should warn
        System.out.println(sr);

        // =====================
        // SortedLinkedList tests
        // =====================
        System.out.println("\n=== SortedLinkedList Tests ===");
        SortedLinkedList<Student> list = new SortedLinkedList<>();

        System.out.println("isEmpty on new list [should be true]: " + list.isEmpty());

        list.insert(S2); // John Smith
        list.insert(S1); // Mary Doe
        list.insert(S4); // Mary Smith

        System.out.println("\nList after inserting John Smith, Mary Doe, Mary Smith [should be sorted by last name]:");
        System.out.println(list);

        System.out.println("search for Mary Doe [should be true]: " + list.search(S1));
        System.out.println("search for S3 (not inserted) [should be false]: " + list.search(S3));

        list.delete(S2); // delete John Smith
        System.out.println("List after deleting John Smith:");
        System.out.println(list);

        list.delete(S3); // not in list — should print not found
        System.out.println("isEmpty after deletions [should be false]: " + list.isEmpty());

        // =====================
        // Queue tests
        // =====================
        System.out.println("\n=== Queue Tests ===");
        Queue<StudentRecord> queue = new Queue<>();

        System.out.println("isEmpty on new queue [should be true]: " + queue.isEmpty());

        StudentRecord qa = new StudentRecord("Alice", "Brown", "B000001");
        StudentRecord qb = new StudentRecord("Bob", "Green", "G000002");
        StudentRecord qc = new StudentRecord("Carol", "White", "W000003");

        queue.enQ(qa);
        queue.enQ(qb);
        queue.enQ(qc);

        System.out.println("Queue after enqueuing Alice, Bob, Carol:");
        System.out.println(queue);

        System.out.println("peek [should be Alice]: " + queue.peek());
        System.out.println("dequeue [should be Alice]: " + queue.deQ());
        System.out.println("Queue after dequeue:");
        System.out.println(queue);

        System.out.println("isFull [should be false]: " + queue.isFull());

        // =====================
        // Course tests
        // =====================
        System.out.println("\n=== Course Tests ===");
        Course cs313 = new Course("CS313", "0313");

        // Add 10 students to fill the roster
        StudentRecord[] students = new StudentRecord[12];
        String[] firstNames = {"Alice","Bob","Carol","David","Eve","Frank","Grace","Hank","Ivy","Jack","Karen","Leo"};
        String[] lastNames  = {"Adams","Baker","Clark","Davis","Evans","Foster","Green","Hill","Irwin","Jones","King","Lee"};

        for (int i = 0; i < 12; i++) {
            students[i] = new StudentRecord(firstNames[i], lastNames[i], "ID" + i);
        }

        // Add 10 — should fill roster
        System.out.println("\n-- Adding 10 students to fill roster --");
        for (int i = 0; i < 10; i++) {
            cs313.addStudent(students[i]);
        }

        // Add 2 more — should go to waitlist
        System.out.println("\n-- Adding 2 more (should go to waitlist) --");
        cs313.addStudent(students[10]);
        cs313.addStudent(students[11]);

        System.out.println("\nCourse toString:");
        System.out.println(cs313);

        // Drop a student — waitlist student should auto-enroll
        System.out.println("\n-- Dropping Alice Adams (waitlist student should move in) --");
        cs313.deleteStudent(students[0]);
        System.out.println(cs313);

        // Try deleting someone not in course
        System.out.println("\n-- Trying to delete someone not in course --");
        cs313.deleteStudent(students[0]); // already removed
    }
}