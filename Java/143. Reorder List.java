// 143. Reorder List
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        // 分三步
        // find the middle node in the linkedlist
        ListNode mid = findMid(head);

        // reverse the sub-list whose head is the next node of the middle node
        ListNode head2 = reverse(mid.next);
        // 注意断链！
        mid.next = null;

        // merge left and right sub-list
        reorder(head, head2);
    }

    // 典型的找链表中点
    private ListNode findMid(ListNode head) {
        ListNode p1 = head;
        ListNode p2 = head.next;
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }
        return p1;
    }

    // 典型的反转链表
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;

        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }

        return pre;
    }

    private void reorder(ListNode h1, ListNode h2) {
        int index = 0;
        ListNode dummy = new ListNode(-1);
        while (h1 != null && h2 != null) {
            if (index % 2 == 0) {
                dummy.next = h1;
                h1 = h1.next;
            } else {
                dummy.next = h2;
                h2 = h2.next;
            }
            index++;
            dummy = dummy.next;
        }
        if (h1 != null) {
            dummy.next = h1;
        }
        if (h2 != null) {
            dummy.next = h2;
        }
    }
}