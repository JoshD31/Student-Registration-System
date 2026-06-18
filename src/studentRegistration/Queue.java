package studentRegistration;

public class Queue<T> {
	
	private class Node {
        T data;
        Node next;
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
	
	private Node rear;//points to last node in Q
    private int size;
    private static final int maxWaitlist = 10;
    //constructor
    public Queue() {
        rear = null;
        size = 0;
    }
    //returns true if Q has no elements
    public boolean isEmpty() {
        return rear == null;
    }
    //returns true if Q is full
    public boolean isFull() {
        return size >= maxWaitlist;
    }
    //returns # of elements in Q
    public int size() {
        return size;
    }
    //adds new element to back of Q
    public void enQ(T data) {
    	if(isFull()) {
    		System.out.println ("Waitlist is full. Cannot add. " + data);
    		return;
    	}
    	Node newNode = new Node(data);
    	if(isEmpty()) {
    		newNode.next = newNode;//points to itself if its the first element
    		rear = newNode;
    		} 
    	else { 
    		newNode.next = rear.next;
    		rear.next = newNode;
    		rear = newNode;
    		}
    	size++;
    }
    //removes the front element of the Q and returns it
    public T deQ() {
    	if(isEmpty()){
    		System.out.println ("The list is empty.");
    		return null;
    	}
    	Node front = rear.next;
        	if (rear == front) {
            rear = null;
        } else {
            rear.next = front.next;
        	}
        size--;
        return front.data;
    }
    //returns front element without removing from Q
    //thought to use it when looking for next in line of waitlist but didnt implement yet. cant hurt to leave it
    public T peek() {
	  if(isEmpty()) {
		  System.out.println ("the list is empty");
		  return null;
	  }
	  return rear.next.data;
  } 
    
    public String toCourseCSV(String courseName) {
        if (isEmpty()) return "";
        String result = "";
        Node curr = rear.next;
        do {
            Student student = (Student) curr.data;
            result += courseName + "," + student.getFirstName() + "," + 
                     student.getLastName() + "," + student.getIDnum() + "\n";
            curr = curr.next;
        } while (curr != rear.next);
        return result;
    }
  
@Override
  public String toString() {
      if (isEmpty()) {
    	  return "Waitlist is empty";
      }
      String result = "";
      Node curr = rear.next;
      do {
          result += curr.data + "\n";
          curr = curr.next;
      } while (curr != rear.next);
      return result;
  }  

}








