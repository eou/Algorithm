// 146. LRU Cache
// 标准版本，HashMap + DoubleLinkedList
// 为什么使用 Doubly linkd list 而不使用 Linked list的原因是：当访问一个节点后，我们需要将他从原来的list中删除，然后将它插入到头节点处，删除过程中需要将其前后节点连接起来，单链表访问目标节点的前后节点会很慢
class LRUCache {
    class Node {
        int key, value;
        Node pre, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    // add node to the head: head -> node
    private void add(Node node) {
        node.pre = head;
        node.next = head.next;

        head.next = node;
        node.next.pre = node;
    }

    // 双向链表便于删除元素
    private void remove(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void moveToHead(Node node) {
        this.remove(node);
        this.add(node);
    }

    // Map m = Collections.synchronizedMap(map);
    private Map<Integer, Node> map = new HashMap<>();
    private int size = 0;
    private int capacity = 0;
    private Node head, tail;

    public LRUCache(int capacity) {
        size = 0;
        this.capacity = capacity;

        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }

        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node); // 注意更新某个值也要移动到表头
        } else {
            node = new Node(key, value);
            map.put(key, node);
            add(node);
            size++;

            if (size > capacity) {
                this.map.remove(tail.pre.key);
                tail.pre.pre.next = tail;
                tail.pre = tail.pre.pre;
                size--;
            }
        }
    }
}

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
  
// follow up, thread safe
// https://aaronice.gitbooks.io/lintcode/data_structure/lru_cache.html
class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V>  
{  
    private final int maxCapacity;  
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;  
    private final Lock lock = new ReentrantLock();  
  
    public LRULinkedHashMap(int maxCapacity)  
    {  
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);  
        this.maxCapacity = maxCapacity;  
    }  
  
    @Override  
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest)  
    {  
        return size() > maxCapacity;  
    }  
  
    @Override  
    public V get(Object key)  
    {  
        try {  
            lock.lock();  
            return super.get(key);  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
  
    @Override  
    public V put(K key, V value)  
    {  
        try {  
            lock.lock();  
            return super.put(key, value);  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
}  