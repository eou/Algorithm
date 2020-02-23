// 460. LFU Cache
class LFUCache {
    // Double Linked List Node
    class Node{
        public int key, val, freq;
        public Node prev, next;
        public Node(int key,int val){
            this.key = key;
            this.val = val;
            this.freq = 0;
        }
    }
    
    // A double linkedlist with specific frequency
    class DLinkedList{
        public int freq;
        public Node head, tail;
        public DLinkedList(int freq){
            this.freq =  freq;
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }
        
        // add node after head node
        public void add(Node node){
            Node nxt = head.next;
            head.next = node;
            node.prev = head;
            node.next = nxt;
            nxt.prev = node;
        }
        
        public boolean isEmpty(){
            return head.next == tail;
        }
        
        public void delete(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        
        // delete last node
        public Node pop() {
            if (isEmpty()) {
                return null;
            }
            
            Node node = tail.prev;
            delete(node);
            return node;
        }
    }
    
    public int capacity;
    public int size;
    public int minFreq; // for remove lfu node
    // cross
    public HashMap<Integer, Node> keyMap;
    public HashMap<Integer, DLinkedList> freqMap;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = minFreq = 0;
        keyMap = new HashMap<>();
        freqMap = new HashMap<>();
        freqMap.put(0, new DLinkedList(0));
    }
    
    // frequency + 1
    private void updateFreq(Node node) {
        int freq = node.freq;
        freqMap.get(freq).delete(node);
        node.freq++;
        
        DLinkedList dlist = freqMap.containsKey(node.freq) ? freqMap.get(node.freq) : new DLinkedList(node.freq);
        dlist.add(node);
        freqMap.put(node.freq, dlist);
        // freqMap.computeIfAbsent(node.freq, k->new DLinkedList(node.freq)).add(node);
        
        // update minimum frequency
        while (freqMap.get(minFreq).isEmpty()) {
            minFreq++;
        }
    }
    
    public int get(int key) {
        if(!keyMap.containsKey(key)) {
            return -1;
        }
        
        // O(1)
        Node node = keyMap.get(key);
        updateFreq(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        
        // already exist
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            node.val = value;
            updateFreq(node);
            return;
        }
        
        if (size >= capacity) {
            Node lfuNode = freqMap.get(minFreq).pop();
            keyMap.remove(lfuNode.key);
            size--;
        }
        
        // add new node
        Node node = new Node(key, value);
        // frequency is 0, never used
        freqMap.get(0).add(node);
        keyMap.put(key,node);
        minFreq = 0;
        size++;
    }
}