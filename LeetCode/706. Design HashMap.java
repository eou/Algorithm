// 706. Design HashMap
class MyHashMap {
    // brute-force，用两个数组，因为给了可能数字是1000000以内的非负数，也可以用int[] anArray = new int[1000000];
    List<Integer> keys, values;

    /** Initialize your data structure here. */
    public MyHashMap() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        // 注意put可能需要覆盖
        for (int i = 0; i < keys.size(); ++i) {
            if (keys.get(i) == key) {
                values.set(i, value);
                return;
            }
        }
        keys.add(key);
        values.add(value);
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map
     * contains no mapping for the key
     */
    public int get(int key) {
        for (int i = 0; i < keys.size(); ++i) {
            if (keys.get(i) == key) {
                return values.get(i);
            }
        }
        return -1;
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping
     * for the key
     */
    public void remove(int key) {
        for (int i = 0; i < keys.size(); ++i) {
            if (keys.get(i) == key) {
                keys.remove(i);
                values.remove(i);
            }
        }
    }
}

class MyHashMap {
    int[] anArray;
    int bucketSize = 1000000;

    public MyHashMap() {
        anArray = new int[bucketSize];
        for (int i = 0; i < bucketSize; i++) {
            anArray[i] = -1;
        }
    }

    public void put(int key, int value) {
        anArray[key] = value;

    }

    public int get(int key) {
        return anArray[key];
    }

    public void remove(int key) {
        if (get(key) != -1) {
            anArray[key] = -1;
        }
    }
}

class MyHashMap {
    // LeetCode Discuss上的一个HashMap的标准实现
    class Bucket {
        final ListNode head = new ListNode(-1, -1);
    }

    // 每个Bucket上连一串ListNode，是相同的key散列得到
    class ListNode {
        int key, val;
        ListNode next;

        ListNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    
    // 每个位置初始化时候，连一个ListNode head
    final Bucket[] buckets = new Bucket[10000];

    int idx(int key) {
        return Integer.hashCode(key) % buckets.length;
    }
    
    // 注意find是找目标节点前一个节点
    ListNode find(Bucket bucket, int key) {
        ListNode node = bucket.head, prev = null;
        while (node != null && node.key != key) {
            prev = node;
            node = node.next;
        }
        return prev;
    }

    public void put(int key, int value) {
        int i = idx(key);

        if (buckets[i] == null) {
            buckets[i] = new Bucket();
        }
        ListNode prev = find(buckets[i], key);

        if (prev.next == null) {
            prev.next = new ListNode(key, value);
        } else {
            prev.next.val = value;
        }
    }

    public int get(int key) {
        int i = idx(key);

        if (buckets[i] == null) {
            return -1;
        }
        ListNode node = find(buckets[i], key);

        return node.next == null ? -1 : node.next.val;
    }

    public void remove(int key) {
        int i = idx(key);

        if (buckets[i] == null) {
            return;
        }
        ListNode prev = find(buckets[i], key);
        
        if (prev.next == null) {
            return;
        }
        prev.next = prev.next.next;
    }
}