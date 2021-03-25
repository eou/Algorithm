// 142. Linked List Cycle II
// Floyd's cycle-finding algorithm
public class Solution {
    // a -> b -> c -> b
    // slow: a -> b -> c
    // fast: a -> b -> c -> b -> c
    // a -> b -> c == c -> b -> c
    // head: a -> b == slow: c -> b
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            } else {
                break;
            }
            if (slow == fast) {
                break;
            }
        }

        if (fast == null) {
            return null;
        }

        ListNode ptr1 = head, ptr2 = slow;
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return ptr1;
    }
}

public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        boolean cycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                cycle = true;
                break;
            }
        }
        if (!cycle) {
            return null;
        }

        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}

public class Solution {
    // I 的HashSet可以原封不动套用
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();

        ListNode node = head;
        while (node != null) {
            if (visited.contains(node)) {
                return node;
            }
            visited.add(node);
            node = node.next;
        }

        return null;
    }
}