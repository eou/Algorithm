// 138. Copy List with Random Pointer
public class Solution {
    // 暴力解法就是用 map 存储 点和边的关系
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

public class Solution {
    // 在原链表中添加新结点，连好指针后然后拆分两个链表
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