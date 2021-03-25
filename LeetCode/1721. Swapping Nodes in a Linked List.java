// 1721. Swapping Nodes in a Linked List
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode kth = dummy, lastKth = dummy;
        for (int i = 0; i < k; i++) {
            if (kth == null) {
                return head;
            }
            kth = kth.next;
        }
        
        ListNode first = head, second = kth;
        while (second.next != null) {
            first = first.next;
            second = second.next;
        }
        
        lastKth = first;
        
        // swap
        int tmp = kth.val;
        kth.val = lastKth.val;
        lastKth.val = tmp;
        
        return dummy.next;
    }
}

// One pass
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        ListNode n1 = null, n2 = null;

        for (var ptr = head; ptr != null; ptr = ptr.next) {
            n2 = n2 == null ? null : n2.next;
            k--;
            if (k == 0) {
                n1 = ptr;
                n2 = head;
            }
        }

        var tmp = n1.val;
        n1.val = n2.val;
        n2.val = tmp;

        return head;
    }
}