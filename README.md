# Student Registration System

A Java console application for managing student enrollment built using custom data structures.

## Data Structures
- **SortedLinkedList** — generic sorted linked list with a dummy head node, used for the main roster and inactive list
- **Queue** — circular linked list implementation used for course waitlists

## Features
- Add and remove students from the main roster
- Auto-generated unique student IDs
- Enroll students in courses with automatic waitlist management
- Dropping all courses automatically moves a student to an inactive list
- Inactive students can be reactivated by enrolling them in a course
- Data persists between sessions via text files

## Classes
| Class | Description |
|-------|-------------|
| `Student` | Base class storing name and ID |
| `StudentRecord` | Extends Student, tracks course enrollments |
| `SortedLinkedList` | Custom generic sorted linked list |
| `Queue` | Custom circular linked list queue |
| `Course` | Manages roster and waitlist for a course |
| `InactiveList` | Stores students removed from the main roster |
| `RegistrationSystem` | Core logic for all operations |
| `Main` | Handles user input and menu navigation |

## How to Run
1. Clone the repository
2. Open in Eclipse or any Java IDE
3. Run `Main.java`

## Notes
- Max roster size per course is 10 students
- Max waitlist size per course is 10 students
- Data is saved automatically on exit to `mainRoster.txt`, `waitlists.txt`, `inactiveList.txt`, and `count.txt`
