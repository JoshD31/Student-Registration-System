package studentRegistration;

public class Student implements Comparable <Student>{
private String firstName;
private String lastName;
private String IDnum;

//constructors
public Student (String firstName, String lastName, String IDnum) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.IDnum = IDnum;
}
//getters
public String getFirstName() {return firstName;}
public String getLastName() {return lastName;}
public String getIDnum() {return IDnum;}
//setters
public void setFirstName(String firstName) {this.firstName = firstName;}
public void setLastName(String lastName) {this.lastName = lastName;}
public void setIDnum(String IDnum) {this.IDnum = IDnum;}

@Override
//Two students are equal if they share the same ID, first, and last name
public boolean equals(Object obj) {
	if (this == obj) return true;
	if (!(obj instanceof Student)) return false; //not a student
	Student other = (Student) obj;
	return this.IDnum.equals(other.IDnum)&& this.lastName.equalsIgnoreCase(other.lastName) && this.firstName.equalsIgnoreCase(other.firstName);
	//ignores upper case
	}
@Override
//Sort alphabetically by last name, then first name
public int compareTo(Student other) {
	int lastNameComparison = this.lastName.compareTo(other.lastName);
    	if (lastNameComparison != 0) {
    		return lastNameComparison; 
    		}
    	return this.firstName.compareTo(other.firstName);
	}
@Override 
public String toString() {
	return "Name: " + lastName + "\t" + firstName + "\tID: " + IDnum;
}

}
