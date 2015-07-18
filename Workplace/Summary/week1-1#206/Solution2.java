package p206;

/*
 * Solution 2: store every data in the list into program
 */


public class Solution2 {

	   public ListNode reverseList(ListNode head) {

		//Step 1: process the head
    	if(head == null || head.next == null)			return head;
		
    	//initialize variables
		ListNode preNode = head;
		ListNode currNode = head.next;
		ListNode nextNode = null;
		
		//iterative loop
		while(currNode.next!=null){
			currNode.next = preNode;
			preNode = currNode;
			currNode = nextNode;
			nextNode = currNode.next;
		}
		currNode.next = preNode;
		
		//process the tail
		head.next = null;
		
		return currNode;
	}

}
