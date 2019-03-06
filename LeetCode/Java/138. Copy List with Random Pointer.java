// 138. Copy List with Random Pointer
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/
class Solution {
    // brute force, use map to store the connection between original node and copy node
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        
        Node dummy = new Node();
        Node ptr2 = dummy;          // dummy.next is the head of clone list
        Node ptr1 = head;           // original list
        
        while (ptr1 != null) {
            // copy next node
            Node copyNode = null;
            if (map.containsKey(ptr1)) {
                copyNode = map.get(ptr1);
            } else {
                copyNode = new Node(ptr1.val);
                map.put(ptr1, copyNode);
            }
            
            ptr2.next = copyNode;
            
            // copy rondom linked node
            if (ptr1.random != null) {
                if (map.containsKey(ptr1.random)) {
                    copyNode.random = map.get(ptr1.random);
                } else {
                    Node copyRandom = new Node(ptr1.random.val);
                    copyNode.random = copyRandom;
                    map.put(ptr1.random, copyRandom);
                }
            }
            
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }
        
        return dummy.next;
        
    }
}

class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return head;
        }
        
        copyNode(head);
        copyRandom(head);
        return splitCopyList(head);
    }
    
    // n1 -> c1 -> n2 -> c2 -> n3 -> c3 -> ...
    public void copyNode(Node head) {
        Node ptr = head;
        while (ptr != null) {
            Node copy = new Node(ptr.val);
            copy.next = ptr.next;
            ptr.next = copy;
            ptr = copy.next;
        }
    }
    
    public void copyRandom(Node head) {
        Node ptr = head;
        while (ptr != null) {
            Node copy = ptr.next;
            if (ptr.random != null) {
                copy.random = ptr.random.next;
            }
            ptr = ptr.next.next;
        }
    }
    
    public Node splitCopyList(Node head) {
        Node dummy = new Node();
        dummy.next = head.next;
        
        Node ptr = head;
        // unhitch the links
        while (ptr != null) {
            Node copy = ptr.next;
            Node nxt = copy.next;
            if (nxt != null) {
                copy.next = nxt.next;
            }
            
            ptr.next = nxt;
            ptr = ptr.next;
        }
        
        return dummy.next;
    }
}

class Solution {
    // 题目老版本的写法，暴力解法就是用 map 存储 点和边的关系
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null) {
            return head;
        }
        
        // 用 map 建立原链表结点和新链表结点一一对应的关系
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        // dummy 指向新链表头结点
        RandomListNode dummy = new RandomListNode(-1);
        RandomListNode pre = dummy;
        RandomListNode newNode;

        // cur 是原链表遍历指针
        RandomListNode cur = head;
        while(cur != null) {
            if(map.containsKey(cur)) {
                newNode = map.get(cur);
            } else {
                newNode = new RandomListNode(cur.label);
                map.put(cur, newNode);
            }
            pre.next = newNode;
            
            // 这里是 next 和 random 结点同时建立；也可以先建立 next 结点，再循环一遍建立 random 结点 
            if(cur.random != null) {
                if(map.containsKey(cur.random)) {
                    newNode.random = map.get(cur.random);
                } else {
                    newNode.random = new RandomListNode(cur.random.label);
                    map.put(cur.random, newNode.random);
                }
            }
            
            pre = pre.next;
            cur = cur.next;
        }
        
        return dummy.next;
    }
}

class Solution {
    // 题目老版本的写法，在原链表中添加新结点，连好指针后然后拆分两个链表
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return head;
        }
        insertCopyNode(head);
        copyRandom(head);
        return splitList(head);
    }

    private void insertCopyNode(RandomListNode head) {
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode newNode = new RandomListNode(cur.label);
            newNode.next = cur.next;
            newNode.random = cur.random;
            cur.next = newNode;
            cur = cur.next.next;
        }
    }

    private void copyRandom(RandomListNode head) {
        RandomListNode cur = head;
        while (cur != null) {
            if (cur.next.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
    }

    private RandomListNode splitList(RandomListNode head) {
        RandomListNode dummy = head.next;
        RandomListNode cur = head;
        while (cur != null && cur.next != null) {
            RandomListNode tmp = cur.next;
            cur.next = cur.next.next;
            cur = cur.next;
            // 要访问tmp.next.next, 注意tmp.next是否为空指针
            if (tmp.next != null) {
                tmp.next = tmp.next.next;
            }
        }
        return dummy;
    }
}