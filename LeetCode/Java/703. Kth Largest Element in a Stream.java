// 703. Kth Largest Element in a Stream
// 最大堆会 TLE
class KthLargest {
    // max-heap
    PriorityQueue<Integer> pq;
    int[] tmp;

    public KthLargest(int k, int[] nums) {
        pq = new PriorityQueue<>(k, (a, b) -> {
            return b - a;
        });
        for (int num : nums) {
            pq.add(num);
        }
        tmp = new int[k - 1];
    }

    public int add(int val) {
        pq.add(val);
        for (int i = 0; i < tmp.length; ++i) {
            tmp[i] = pq.poll();
        }

        int result = pq.peek();
        for (int i = 0; i < tmp.length; ++i) {
            pq.add(tmp[i]);
        }

        return result;
    }
}

class KthLargest {
    // min-heap
    PriorityQueue<Integer> pq;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        pq = new PriorityQueue<>(k);
        for (int num : nums) {
            // note that using add function below
            add(num);
        }
    }

    public int add(int val) {
        if (pq.size() < k) {
            pq.offer(val);
        } else if (pq.peek() < val) {
            pq.poll();
            pq.offer(val);
        }

        return pq.peek();
    }
}
