// 92. Reverse Linked List II
// 两种写法，一种是就地反转指针，一种是把后面节点往前移动
class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode first = dummy, cur = dummy, pre = dummy;
        for (int i = 0; i < m; ++i) {
            pre = cur;
            cur = cur.next;
        }

        first = pre;
        
        pre = cur;
        cur = cur.next; // to the next node of the mth node
        for (int i = 0; i < n - m; ++i) {
            ListNode nxt = cur.next;
            pre.next = nxt;
            cur.next = first.next;
            first.next = cur;
            cur = nxt;
        }

        return dummy.next;
    }
}

class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode cur = dummy, pre = dummy;
        for (int i = 0; i < m; ++i) {
            pre = cur;
            cur = cur.next;
        }

        //   * -> 5 -> 3 -> NULL
        // pre   cur
        // first last
        //       pre  cur
        ListNode first = pre;
        ListNode last = cur;

        pre = cur;
        cur = cur.next; // to the next node of the mth node
        for (int i = 0; i < n - m; ++i) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }

        first.next = pre;
        last.next = cur;

        return dummy.next;
    }
}

class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // 前面几个特殊判断对于AC来说非必要
        if (head == null) {
            return null;
        }
        if(m <= 0 || n <= m) {
            return head;
        }
        
        // find the start and end position
        ListNode n0 = null, n1, nk, nk_1;
        n1 = head;
        for(int i = 1; i < m; i++) {
            n0 = n1;
            n1 = n1.next;
        }
        nk = n1;
        // 其实提前找 nk 和 nk_1 是非必要的，可以边遍历边反转
        for(int i = 1; i <= n - m; i++) {
            nk = nk.next;
        }
        nk_1 = nk.next;
        
        // swap the nodes
        ListNode pre = null;
        ListNode cur = n1;
        while(cur != nk_1) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        
        // connect the remaining endpoints
        n1.next = nk_1;
        // if n0 node is not null, which means the head of the linkedlist has not changed
        if(n0 != null) {
            n0.next = nk;
            return head;
        }
        return nk;
    }
}

class Solution {
    // 上面代码的改进版本，用 dummy node 的典型例子
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        for (int i = 1; i < m; i++) {
            pre = pre.next;
        }

        // 最后连接头尾结点需要2个固定指针n0, n1
        ListNode n0, n1, cur, post;
        cur = pre.next;
        post = cur.next;
        n0 = pre;
        n1 = cur;

        // 反转操作需要3个指针，前中后
        for (int i = m; i < n; i++) {
            if (post == null) {
                return null;
            }
            ListNode tmp = post.next;
            post.next = cur;
            cur = post;
            post = tmp;
        }
        n1.next = post;
        n0.next = cur;

        return dummy.next;
    }
}

class Solution {
    // LeetCode上的另一种精简写法，但是结点交换过程不直观
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // dummy-> 1 -> 2 -> 3 -> 4 -> 5
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;
        }

        ListNode start = pre.next;
        ListNode then = start.next;

        for (int i = 0; i < n - m; i++) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }

        return dummy.next;
    }
}
