// 239. Sliding Window Maximum
// brute-force, O(n^2)
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }
        
        int[] results = new int[nums.length - k + 1];
        
        for (int i = k - 1; i < nums.length; ++i) {
            int max = nums[i];
            for (int j = 1; j < k; ++j) {
                max = Math.max(nums[i - j], max);  
            }
            results[i - k + 1] = max;
        }
        
        return results;
    }
}

// max-heap，复杂度O(nk)，可以用hashheap达到O(nlogk)
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int[] results = new int[nums.length - k + 1];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < nums.length; i++) {
            // remove old element
            if (i >= k) {
                pq.remove(nums[i - k]);
            }
            // add new element
            pq.offer(nums[i]);
            // get the maximum one
            if (i >= k - 1) {
                results[i - k + 1] = pq.peek();
            }
        }

        return results;
    }
}

// Deque, O(n), keep order inside
// 每个元素最多进入弹出一次队列
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] results = new int[nums.length - k + 1];
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        // store index
        // keep index increasing ordered, value increasing ordered
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            // remove i - kth element
            if (!queue.isEmpty() && queue.peek() == i - k) {
                queue.poll();
            }

            // keep value increasing
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }

            queue.offer(i);

            if (i >= k - 1) {
                results[i - k + 1] = nums[queue.peek()];
            }
        }

        return results;
    }
}