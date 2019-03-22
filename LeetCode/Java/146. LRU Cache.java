// 146. LRU Cache
class LRUCache {
    // LinkedHashMap 这种以插入顺序保存的map 很容易实现 LRU
    private LinkedHashMap<Integer, Integer> map;
    public LRUCache(int capacity) {
        map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }
    
    public int get(int key) {
        return map.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        map.put(key, value);        
    }
}

class LRUCache {
    // 标准版本，HashMap + DoubleLinkedList
    class DoubleListNode {
        int key;
        int value;
        DoubleListNode pre;
        DoubleListNode next;

        DoubleListNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    // add node to the head: head -> node
    private void add(DoubleListNode node) {
        node.pre = head;
        node.next = head.next;

        head.next = node;
        node.next.pre = node;
    }

    // 双向链表便于删除元素
    private void remove(DoubleListNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void moveToHead(DoubleListNode node) {
        this.remove(node);
        this.add(node);
    }

    private Map<Integer, DoubleListNode> map = new HashMap<>();
    private int count = 0;
    private int capacity = 0;
    private DoubleListNode head, tail;

    public LRUCache(int capacity) {
        count = 0;
        this.capacity = capacity;

        head = new DoubleListNode(-1, -1);
        tail = new DoubleListNode(-1, -1);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        DoubleListNode node = map.get(key);
        if (node == null) {
            return -1;
        }

        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DoubleListNode node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node); // 注意更新某个值也要移动到表头
        } else {
            node = new DoubleListNode(key, value);
            map.put(key, node);
            add(node);
            count++;

            if (count > capacity) {
                this.map.remove(tail.pre.key);
                tail.pre.pre.next = tail;
                tail.pre = tail.pre.pre;
                count--;
            }
        }
    }
}
