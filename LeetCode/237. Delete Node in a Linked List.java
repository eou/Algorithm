// 237. Delete Node in a Linked List
class Solution {
    public void deleteNode(ListNode node) {
        ListNode pre = node, nxt = node.next;
        while (nxt != null) {
            node.val = nxt.val;
            pre = node;
            node = node.next;
            nxt = nxt.next;
        }
        
        pre.next = null;
    }
}

class Solution {
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}