// 61. Rotate List
class Solution {
    // 找规律题，找到位置断链，然后首尾相连
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        
        int len = 0;
        for (ListNode ptr = head; ptr != null; ptr = ptr.next, ++len);
        
        k %= len;
        if (k == 0) {
            return head;
        }
        
        // 1 -> 2 -> 3  ----- 4 -> 5 -> 6 -> 7 -> NULL
        // .        pre                     last
        ListNode pre = head, last = head;
        for (int i = 0; i < k; ++i) {
            last = last.next;
        }
        
        while(last.next != null) {
            pre = pre.next;
            last = last.next;
        }
        
        ListNode newHead = pre.next;
        pre.next = null;
        last.next = head;
        
        return newHead;
    }
}