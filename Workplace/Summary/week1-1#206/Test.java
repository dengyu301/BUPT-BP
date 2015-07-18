package p206;

public class Test {
	public static void readL(ListNode head){
		if(head != null){
			ListNode currentNode = head;
			while(currentNode.next!=null){
				System.out.print(currentNode.val+"  ");
				currentNode = currentNode.next;
			}
			System.out.println(currentNode.val);
		}
	}
	
	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		ListNode currentN = new ListNode(2);
		head.next = currentN;
		
		for(int i=3; i<10; i++){
			ListNode l = new ListNode(i);
			currentN.next = l;
			currentN = l;
		}

		System.out.println("begin old list");
		readL(head);
		ListNode newh =  new Solution3().reverseList(head);
		System.out.println("begin new list");
		readL(newh);
		
	}

}
