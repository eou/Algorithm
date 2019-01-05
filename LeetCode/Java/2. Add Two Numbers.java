// 2. Add Two Numbers
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode ptr1 = l1, ptr2 = l2, cur = dummy;
        int carry = 0;
        while (ptr1 != null || ptr2 != null) {
            int x = (ptr1 != null) ? ptr1.val : 0;
            int y = (ptr2 != null) ? ptr2.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);

            cur = cur.next;
            if (ptr1 != null) {
                ptr1 = ptr1.next;
            }
            if (ptr2 != null) {
                ptr2 = ptr2.next;
            }
        }
        if (carry == 1) {
            cur.next = new ListNode(carry);
        }

        return dummy.next;
    }
}