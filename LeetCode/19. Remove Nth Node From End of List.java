// 19. Remove Nth Node From End of List
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        ListNode first = dummy;
        ListNode second = dummy;
        dummy.next = head;
        
        for(int i = 0; i <= n; i++) {
            second = second.next;
        }
        while(second != null) {
            first = first.next;
            second = second.next;
        }
        first.next = first.next.next;
        
        return dummy.next;
    }
}