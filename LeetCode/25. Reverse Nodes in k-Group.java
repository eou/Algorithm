// 25. Reverse Nodes in k-Group
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // 设置一个 “假”结点 作为头结点的前一个结点
        // 当链表结构发生变化，基本都需要 dummy node
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode pre = dummy;
        while (true) {
            pre = reverseKNode(pre, k);
            if (pre == null) {
                break;
            }
        }
        
        // 实际上此时 dummy 的下一个结点就是链表第 k 个结点
        return dummy.next;
    }
    
    // head 是当前要反转的链表头结点的前一个结点
    private ListNode reverseKNode(ListNode head, int k) {
        // 首先寻找第 k 个节点
        ListNode nk = head;
        for (int i = 0; i < k; ++i) {
            if (nk == null) {
                return null;
            }
            nk = nk.next;
        }
        // 别忘了判断第 k 个结点是否为空
        if (nk == null) {
            return null;
        }
        
        ListNode nextHead = nk.next, n1 = head.next;
        
        // 开始反转链表
        // head, n1, ..., nk, nextHead, ...
        ListNode pre = null, cur = n1;
        while (cur != nextHead) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        
        // 反转之后 nk 为头结点，n1 为尾结点
        head.next = nk;
        n1.next = nextHead;
        // n1是下一个要反转的链表头结点的前一个结点
        // head, nk, ..., n1, nextHead, ...
        return n1;
    }
}

// Recursion
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode nextHead = head;

        for (int i = 0; i < k; i++) {
            if (nextHead == null) {
                return head;
            }
            nextHead = nextHead.next;
        }

        nextHead = reverseKGroup(nextHead, k);

        ListNode pre = null, cur = head;
        // pre, cur (head), nxt, ..., nextHead
        for (int i = 0; i < k; i++) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }

        head.next = nextHead;
        return pre;
    }
}

class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        int len = 0;
        for (ListNode ptr = head; ptr != null; ++len, ptr = ptr.next)
            ;

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        for (ListNode first = dummy, pre = head; len >= k; len -= k) {
            for (int i = 1; i < k; ++i) {
                // add the cur node into the head of this part of linkedlist
                ListNode cur = pre.next;
                ListNode nxt = cur.next;
                // last two operations cannot change order
                //  ? -> 3 -> 2 -> 1 -> 4 -> 5  =>  ? -> 4 -> 3 -> 2 -> 1 -> 5 : related with 5 nodes, 3 links changed
                // first           pre cur  nxt    first cur            pre nxt
                pre.next = nxt;
                cur.next = first.next;
                first.next = cur;
            }

            first = pre; // the dummy node before next part
            pre = pre.next; // the head node of next part
        }

        return dummy.next;
    }
}