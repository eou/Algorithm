// 148. Sort List
class Solution {
    public ListNode sortList(ListNode head) {
        head = mergeSort(head);
        head = quickSort(head);
        return head;
    }

    private ListNode findMid(ListNode head) {
        // !!! fast = head will cause infinite loop since mid will be closer to end
        // 注意这里 fast = head; 也能找中点，但是 mid 偏后，会在主函数中无限循环
        // 如 [4, 2], head.val = 4, mid.val = 2, mid.next = null; 并不能断链
        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    private ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = findMid(head);
        ListNode right = mergeSort(mid.next);
        mid.next = null;
        ListNode left = mergeSort(head);

        return merge(left, right);
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(-1);
        ListNode ptr = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                ptr.next = left;
                left = left.next;
            } else {
                ptr.next = right;
                right = right.next;
            }
            ptr = ptr.next;
        }

        if (left != null) {
            ptr.next = left;
        }

        if (right != null) {
            ptr.next = right;
        }

        return dummy.next;
    }

    private ListNode quickSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode midNode = findMid(head);
        ListNode leftDummy = new ListNode(-1);
        ListNode rightDummy = new ListNode(-1);
        ListNode midDummy = new ListNode(-1);
        ListNode left = leftDummy, right = rightDummy, mid = midDummy, ptr = head;
        while (ptr != null) {
            if (ptr.val < midNode.val) {
                left.next = ptr;
                left = left.next;
            } else if (ptr.val > midNode.val) {
                right.next = ptr;
                right = right.next;
            } else {
                // in order to decompose mid node
                mid.next = ptr;
                mid = mid.next;
            }
            ptr = ptr.next;
        }

        left.next = null;
        right.next = null;
        mid.next = null;

        ListNode leftHead = quickSort(leftDummy.next);
        ListNode rightHead = quickSort(rightDummy.next);

        return concat(leftHead, midDummy.next, rightHead);
    }

    private ListNode concat(ListNode leftHead, ListNode midHead, ListNode rightHead) {
        ListNode dummy = new ListNode(-1);
        ListNode ptr = dummy;

        ptr.next = leftHead;
        ptr = tail(ptr);
        ptr.next = midHead;
        ptr = tail(ptr);
        ptr.next = rightHead;

        return dummy.next;
    }

    private ListNode tail(ListNode head) {
        if (head == null) {
            return null;
        }

        while (head.next != null) {
            head = head.next;
        }
        return head;
    }
}
