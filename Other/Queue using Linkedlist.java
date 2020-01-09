// Linked list node
class ListNode {
    int key;
    ListNode next;
    public ListNode(int key) {
        this.key = key;
        this.next = null;
    }
}

class Queue {
    ListNode head, tail;
    public Queue() {
        this.head = this.tail = null;
    }

    public void enqueue(int key) {
        ListNode cur = new ListNode(key);

        // empty queue
        if (this.head = null) {
            this.head = this.tail = cur;
            return;
        }

        this.tail.next = cur;
        this.tail = cur;
    }

    public ListNode dequeue() {
        // empty queue
        if (this.head = null) {
            return null;
        }

        ListNode cur = this.head;
        this.head = this.head.next;

        // queue become empty
        if (this.head == null) {
            this.tail = this.head;
        }
        
        cur.next = null;
        return cur;
    }
}