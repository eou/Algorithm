// 109. Convert Sorted List to Binary Search Tree
// 重点是将每段链表分为三部分而不是两部分，根节点和左右子树，注意断链
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return new TreeNode(head.val);
        }

        ListNode mid = findMid(head);
        TreeNode root = new TreeNode(mid.val);

        ListNode ptr = head;
        while (ptr.next != mid) {
            ptr = ptr.next;
        }
        // split first half;
        ptr.next = null;

        // split second half;
        ptr = mid.next;
        mid.next = null;

        TreeNode left = sortedListToBST(head);
        TreeNode right = sortedListToBST(ptr);
        root.left = left;
        root.right = right;
        return root;
    }

    private ListNode findMid(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }
}

class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null) {
            return null;
        }
        if(head.next == null) {
            return new TreeNode(head.val);
        }
        if(head.next.next == null) {
            TreeNode root = new TreeNode(head.val);
            root.right = new TreeNode(head.next.val);
            return root;
        }
        
        ListNode preMid = findPreMid(head);
        TreeNode root = new TreeNode(preMid.next.val);
        root.right = sortedListToBST(preMid.next.next);
        preMid.next.next = null;
        preMid.next = null;
        root.left = sortedListToBST(head);
        
        return root;
    }
    
    private ListNode findPreMid(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        
        ListNode preSlow = head;
        ListNode slow = head;
        ListNode fast = head.next;
        while(fast != null && fast.next != null) {
            preSlow = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return preSlow;
    }
}