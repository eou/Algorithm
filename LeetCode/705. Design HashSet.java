// 705. Design HashSet
// 此题和 706. Design HashMap 没什么区别
class MyHashSet {

    boolean[] array;

    /** Initialize your data structure here. */
    public MyHashSet() {
        array = new boolean[1000005];   // 初始化的时候可以申请 1000 个 boolean ArrayList，需要的时候再扩充
    }

    public void add(int key) {
        array[key] = true;
    }

    public void remove(int key) {
        array[key] = false;
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        return array[key];
    }
}


