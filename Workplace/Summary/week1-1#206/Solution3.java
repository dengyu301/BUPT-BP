package p206;

/*
 * Solution 3: recursive method
 */

public class Solution3 {
	
	   public ListNode reverseList(ListNode head) {

		//Step 1: process the head
		if(head == null || head.next == null)			return head;
		
	    //Step 2: call reverseList method (itself)
        ListNode tail = reverseList(head.next);
        
        //Step 3: process pointer: set the input node's next node's pointer to the input node 
        head.next.next = head;
        head.next = null;
        
        return tail; 
	}
}
