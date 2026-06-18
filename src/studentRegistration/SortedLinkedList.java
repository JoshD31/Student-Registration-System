package studentRegistration;

public class SortedLinkedList<T extends Comparable<T>> {
	//decided to make it generic type for flexibility
	private class Node {
		T data;
		Node next;
		Node(T data){
			this.data = data;
			this.next = null;
			}
		}

	private Node head; //dummy node
	public SortedLinkedList() {
		head = new Node(null);
	}
	
	public boolean isEmpty() {
		return head.next == null;
	}
	//inserts in sorted order
	public void insert(T x) {
		Node curr = head;
		while (curr.next != null && curr.next.data.compareTo(x) < 0) {
			curr = curr.next;
		}
		Node newNode = new Node(x);
		newNode.next = curr.next;
		curr.next = newNode;
	}
@Override	
	public String toString() {
		if (isEmpty()) {
			return "List is empty";
			}
		String result = "";
		Node curr = head.next;
			while(curr != null) {
			result += curr.data + "\n";
			curr = curr.next;
		}
		return result;
	}
	//returns true if element is found in list
	public boolean search(T x) {
		Node curr = head.next;//start after dummy node
		while(curr != null) {
		if (curr.data.equals(x)) {
			return true;
			}
			curr = curr.next;
		}
		return false;
	}
	//removes element from list
	public void delete(T x) {
		Node curr = head;
		while(curr.next != null) {
			if(curr.next.data.equals(x)) {
			curr.next = curr.next.next;
			return;
			}
			curr = curr.next;
		}
		System.out.println(x + " Not found");
	}
	
	//returns the actual object from the list that matches, or null if not found
	public T get(T x) {
	    Node curr = head.next;
	    while (curr != null) {
	        if (curr.data.equals(x)) return curr.data;
	        curr = curr.next;
	    }
	    return null; // not found
	}
	// similar to studentRecord but casts to StudentRecord to include course data
	public String toCSV() {
        if (isEmpty()) return "";
        String result = "";
        Node curr = head.next;
        while (curr != null) {
            StudentRecord s = (StudentRecord) curr.data;
            result += s.toCSV() + "\n";
            curr = curr.next;
        }
        return result;
    }
	
}

