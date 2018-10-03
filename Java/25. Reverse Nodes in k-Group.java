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
        
        ListNode nextHead = nk.next;
        ListNode n1 = head.next;
        
        // 开始反转链表
        ListNode pre = null;
        ListNode cur = n1;
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
        return n1;
    }
}