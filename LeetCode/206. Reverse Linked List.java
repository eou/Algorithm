// 206. Reverse Linked List
class Solution {
    // 最简单的遍历反转，需要三个指针，不需要判空或者其他特殊情况
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null, cur = head, nxt = cur.next;
        while (cur != null) {
            cur.next = pre;
            pre = cur;
            cur = nxt;
            if (nxt != null) {
                nxt = nxt.next;
            }
        }

        return pre;
    }
}

class Solution {
    // 递归版本，也可以写成尾递归，但就跟普通的循环遍历没什么区别了，虽然可能有性能优化
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
            
        ListNode h = reverseList(head.next);
        // 不用循环去找反转后的尾部节点，因为就是head.next
        // ListNode h2 = h;
        // while (h.next != null) {
        //     h = h.next;
        // }
        // h.next = head;
        
        // 反转指针 head -> head.next => head <- head.next
        head.next.next = head;
        // 由于指针已经回指，head.next必须置空，否则会形成双向链表
        head.next = null;
        return h;
    }
}