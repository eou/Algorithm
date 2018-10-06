// 21. Merge Two Sorted Lists
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 这些判断不需要
        // if(l1 == null && l2 == null) {
        //     return null;
        // }
        // if(l1 == null) {
        //     return l2;
        // }
        // if(l2 == null) {
        //     return l1;
        // }
        
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                dummy.next = l1;
                l1 = l1.next;
            } else {
                dummy.next = l2;
                l2 = l2.next;
            }
            dummy = dummy.next;
        }
        if(l1 != null) {
            dummy.next = l1;
        }
        if(l2 != null) {
            dummy.next = l2;
        }
        
        return head.next;
    }
}

class Solution {
    // 还可以递归解决
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}