// 61. Rotate List
class Solution {
    // 其实是找规律题，找到位置断链，然后首尾相连
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }
        int length = 0;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        length++;
        int step = k % length;
        if (step == 0) {
            return head;
        } else {
            ListNode pos = head;
            for (int i = 0; i < length - step - 1; i++) {
                pos = pos.next;
            }
            ListNode newHead = pos.next;
            pos.next = null;
            tail.next = head;
            return newHead;
        }
    }
}