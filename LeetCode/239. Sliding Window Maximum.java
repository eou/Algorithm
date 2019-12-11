// 239. Sliding Window Maximum
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

class Solution {
    // 最通俗易懂的版本：优先队列，复杂度O(nk)，可以用hashheap达到O(nlogk)
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] results = new int[nums.length - k + 1];
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        Queue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < nums.length; i++) {
            // 队列去除最早的元素，相当于窗口前进一位
            if (i >= k) {
                pq.remove(nums[i - k]);
            }
            // 加入一个新元素，然后内部自动排序
            pq.offer(nums[i]);
            // 选择最大的元素
            if (i >= k - 1) {
                results[i - k + 1] = pq.peek();
            }
        }

        return results;
    }
}

class Solution {
    // 使用双端队列维持有序数列
    // 由于每个元素最多进入弹出一次队列所以复杂度可以达到O(n)
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] results = new int[nums.length - k + 1];
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        // 队列只存储下标
        // 注意 Deque 的各种方法与普通 Queue 不太一样
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            // 每次处理队首和队尾元素使得新元素进入队列后保持有序
            // 队首最大元素不能是第 i - k 个元素，否则删除
            if (!queue.isEmpty() && queue.peek() == i - k) {
                queue.poll();
            }

            // 从队尾新进入的元素前面的元素都必须比其大，否则删除
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }

            queue.offerLast(i);

            if (i >= k - 1) {
                results[i - k + 1] = nums[queue.peek()];
            }
        }

        return results;
    }
}