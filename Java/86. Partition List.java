// 86. Partition List
class Solution {
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }

        // find the first node which is greater than or equal to x
        ListNode pos = head;
        ListNode pre = null;
        while (pos != null) {
            if (pos.val < x) {
                pre = pos;
                pos = pos.next;
            } else {
                break;
            }
        }

        if (pos == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 改变链表总共需要4个辅助结点
        ListNode dummy2 = dummy;
        if (pre != null) {
            dummy2 = pre;
        }

        // find the node which is less than x in the remaining list nodes
        while (pos != null) {
            if (pos.val >= x) {
                pre = pos;
                pos = pos.next;
            } else {
                ListNode tmp = dummy2.next;
                pre.next = pos.next;
                dummy2.next = pos;
                pos.next = tmp;
                dummy2 = pos;
                pos = pre.next;
            }
        }

        return dummy.next;
    }
}

class Solution {
    // LeetCode上最简便的方法是将链表分为两部分，然后连接
    public ListNode partition(ListNode head, int x) {
        ListNode smallerHead = new ListNode(0), biggerHead = new ListNode(0);
        ListNode smaller = smallerHead, bigger = biggerHead, pos = head;
        while (pos != null) {
            if (pos.val < x) {
                smaller.next = pos;
                smaller = smaller.next;
            } else {
                bigger.next = pos;
                bigger = bigger.next;
            }
            pos = pos.next;
        }
        // no need for extra check because of fake heads
        smaller.next = biggerHead.next;
        bigger.next = null;

        return smallerHead.next;
    }
}