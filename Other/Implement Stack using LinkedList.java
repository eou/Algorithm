/**
 * Implement Stack using LinkedList.java
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int v) {
        val = v;
    }
}

class Mystack {
    ListNode head;
    public Mystack() {
        head = new ListNode(-1);
    }

    public void push(int x) {
        ListNode tmp = head.next;
        ListNode node = new ListNode(x);
        head.next = node;
        node.next = tmp;
    }

    public int pop() {
        ListNode tmp = head.next;
        head.next = head.next.next;
        return tmp.val;
    }

    public int top() {
        return head.next.val;
    }

    public boolean empty() {
        return head.next == null;
    }

    public static void main(String[] args) {
        Mystack stack = new Mystack();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 12; i++) {
            System.out.println(stack.empty());
            if (!stack.empty()) {
                System.out.println(stack.top());
                System.out.println(stack.pop());
            }
        }
    }
}