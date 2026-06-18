package studentRegistration;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static RegistrationSystem system = new RegistrationSystem();//only need one RegistrationSystem

    public static void main(String[] args) {
        int choice = 0;
        while (choice != 7) {
            printMainMenu();
            choice = getInput("Enter choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> deleteStudent();
                case 3 -> searchStudent();
                case 4 -> viewStudentInfo();
                case 5 -> displayRoster();
                case 6 -> manageCourses();
                case 7 -> quit();
                default -> System.out.println("Invalid option. Please enter 1-7.");
            }
        }
        scanner.close();
    }
    
    static void printMainMenu() {
        System.out.println("\n Student Registration Menu ");
        System.out.println("1. Add student to MainRoster");
        System.out.println("2. Delete student from MainRoster");
        System.out.println("3. Search for student");
        System.out.println("4. View student info");
        System.out.println("5. Display roster");
        System.out.println("6. Manage courses");
        System.out.println("7. Quit");
    }
    //user enters student info and it gets added to roster
    static void addStudent() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        system.addStudentToRoster(firstName, lastName);
    }
    //user enters student info and all courses will be dropped before deleting student and moving them to inactive list
    static void deleteStudent() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter ID number: ");
        String IDnum = scanner.nextLine().trim();
        System.out.println("This will drop all of " + firstName + " " + lastName + " courses and move them to the inactive list.");
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        //confirmation
        if (confirm.equals("yes")) {
            system.deleteStudentFromRoster(firstName, lastName, IDnum);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    //searches for student by name and ID
    static void searchStudent() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter ID number: ");
        String IDnum = scanner.nextLine().trim();
        system.searchStudent(firstName, lastName, IDnum);
    }
    //prints students info
    static void viewStudentInfo() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter ID number: ");
        String IDnum = scanner.nextLine().trim();
        system.displayStudentInfo(firstName, lastName, IDnum);
    }
    
    static void displayRoster() {
        system.displayMainRoster();
        system.displayInactiveList();
    }
    //opens course management menu
    static void manageCourses() {
        int choice = 0;
        while (choice != 4) {
            printCourseMenu();
            choice = getInput("Enter choice: ");
            switch (choice) {
                case 1 -> addStudentToCourse();
                case 2 -> dropStudentFromCourse();
                case 3 -> displayCourse();
                case 4 -> System.out.println("Returning to main menu");
                default -> System.out.println("Invalid option. Please enter 1-4.");
            }
        }
    }
    
    static void printCourseMenu() {
        System.out.println("\n Course Management ");
        System.out.println("1. Add student to a course");
        System.out.println("2. Drop student from a course");
        System.out.println("3. Display a course");
        System.out.println("4. Back to main menu");
    }

    static void addStudentToCourse() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter ID number: ");
        String IDnum = scanner.nextLine().trim();
        system.displayCourses();
        int courseIndex = getInput("Select course number: ");
        system.addStudentToCourse(firstName, lastName, IDnum, courseIndex);
    }

    static void dropStudentFromCourse() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter ID number: ");
        String IDnum = scanner.nextLine().trim();
        system.displayCourses();
        int courseIndex = getInput("Select course number: ");
        system.dropStudentFromCourse(firstName, lastName, IDnum, courseIndex);
    }

    static void displayCourse() {
        system.displayCourses();
        int courseIndex = getInput("Select course number: ");
        system.displayCourse(courseIndex);
    }
    //data saved before exiting program
    static void quit() {
        system.saveData();
        System.out.println("Goodbye!");
    }
    //reads users input for menu selection
    static int getInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}