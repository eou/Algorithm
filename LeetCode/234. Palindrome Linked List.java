// 234. Palindrome Linked List
// 链表最容易想到的关于判断对称的操作应该就是反转
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 注意一下奇偶不同情况的指针指向
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast.next != null) {
                fast = fast.next;
            }
        }

        reverseLinkedList(slow);

        ListNode right = fast;
        ListNode left = head;
        while (right.next != null) {
            if (right.val == left.val) {
                right = right.next;
                left = left.next;
            } else {
                return false;
            }
        }
        return true;
    }

    private void reverseLinkedList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode nextNode = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nextNode;
        }
    }
}