package p206;
import java.util.*;

/*
 * Solution 1: store every data in the list into program
 */

public class Solution1 {

	    public ListNode reverseList(ListNode head) {
	 	
	    	//Step 1: process the head
	    	if(head == null)			return head;
	    	
	    	//initiate variables
			ListNode currentNode = head;
			List<Integer> nodelist = new ArrayList<Integer>();
			
			//Step 2: walk through the given linked list and store into a local Arraylist (nodelist)
			while(currentNode.next!=null){
				nodelist.add(currentNode.val);
				currentNode = currentNode.next;
			}
			nodelist.add(currentNode.val);
			
			//Step 3: Reassign the given linked list using nodelist data from the end to the beginning
			currentNode = head;
			int totall = nodelist.size();
			int k = 1;
			
			while(k<=totall){
				currentNode.val = nodelist.get(totall-k);
				currentNode = currentNode.next;
				k++;
			}
			
			return head;
	    }


	
	
}
