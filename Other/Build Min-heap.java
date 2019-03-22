public class MinHeap {
    // 建堆复杂度为 O(N), 插入和删除元素都是 O(logN)
    private int[] heap;
    private int size;
    private int maxSize;

    public MinHeap (int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        heap = new int[this.maxSize + 1];
        heap[0] = Integer.MIN_VALUE;
    }

    public void insert (int e) {
        heap[++size] = e;
        int cur = size;
        while (heap[cur] < heap[parent(cur)]) {
            swap(cur, parent(cur));
            cur = parent(cur);
        }
    }

    public void minHeapify() {
        for (int i = size / 2; i >= 1; --i) {
            heapify(i);
        }
    }

    public void heapify(int i) {
        if (!leaf(i)) {
            if (heap[i] > heap[leftChild(i)]) {
                swap(i, leftChild(i));
                heapify(leftChild(i));
            } else if (heap[i] > heap[rightChild(i)]) {
                swap(i, rightChild(i));
                heapify(rightChild(i));
            }
        }
    }

    public int remove() {
        int e = heap[1];
        heap[1] = heap[size--];
        heapify(1);
        return e;
    }

    private void swap (int i, int j) {
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private int parent (int i) {
        return i / 2;
    }

    private int leftChild (int i) {
        return 2 * i;
    }

    private int rightChild (int i) {
        return 2 * i + 1;
    }

    private boolean leaf (int i) {
        return i >= size / 2 && i <= size;
    }

    public void print() {
        for (int i = 1; i <= size / 2; ++i) {
            System.out.print(" PARENT : " + heap[i] 
                                + " LEFT CHILD : " + heap[2 * i] 
                                + " RIGHT CHILD :" + heap[2 * i + 1]); 
            System.out.println(); 
        }
    }

    public static void main (String[] args) {
        System.out.println("The Min Heap is ");
        MinHeap minHeap = new MinHeap(15);
        minHeap.insert(5);
        minHeap.insert(3);
        minHeap.insert(17);
        minHeap.insert(10);
        minHeap.insert(84);
        minHeap.insert(19);
        minHeap.insert(6);
        minHeap.insert(22);
        minHeap.insert(9);
        minHeap.minHeapify();

        minHeap.print();
        System.out.println("The Min val is " + minHeap.remove());
    }
}