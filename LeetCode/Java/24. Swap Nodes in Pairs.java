// 24. Swap Nodes in Pairs
class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p1 = head, p2 = head.next, p3 = head.next.next;
        p2.next = p1;
        ListNode nextHead = swapPairs(p3);
        p1.next = nextHead;
        return p2;
    }
}

class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;
        while (current.next != null && current.next.next != null) {
            ListNode first = current.next;
            ListNode second = current.next.next;
            // 1->2->3->4
            // 1,2->3->4
            first.next = second.next;
            // dummy->2
            current.next = second;
            // dummy->2->1->3->4
            current.next.next = first;
            // dummy->2->1->current,3->4
            current = current.next.next;
        }
        return dummy.next;
    }
}