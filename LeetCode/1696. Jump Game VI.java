// 1696. Jump Game VI
// !!! Find the max value from dp[i-k ... i] 
// Heap, time O(NlogN), space O(N)
class Solution {
    public int maxResult(int[] nums, int k) {
        int len = nums.length;
        // max heap
        Queue<int[]> queue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        queue.offer(new int[] { nums[0], 0 });

        // for dp[i], we need to find max(dp[j]) + nums[i], i - k <= j < i
        int res = nums[0];
        for (int i = 1; i < len; i++) {
            while (i - queue.peek()[1] > k) {
                queue.poll();
            }
            res = queue.peek()[0] + nums[i];
            queue.offer(new int[] {res, i});
        }
        return res;
    }
}

// Monotonic queue, time O(N), space O(K)
class Solution {
    public int maxResult(int[] nums, int k) {
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{nums[0], 0});
        
        int res = nums[0];
        // q[1] strictly increases while q[0] strictly decreases
        for (int i = 1; i < nums.length; i++) {
            while (i - queue.peek()[1] > k) {
                queue.poll();
            }
            
            res = queue.peek()[0] + nums[i];
            
            // !!! If the last element is not larger than res, this element cannot be used in res loops. We can remove it.
            while (!queue.isEmpty() && res >= queue.getLast()[0]) {
                queue.pollLast();
            }
            
            queue.offer(new int[]{res, i});
        }
        
        return res;
    }
}


// DFS with memo, still TLE， 57/65
// T(n) = T(n - 1) + T(n - 2) + ... + T(n - k)
class Solution {
    private int[] memo;
    public int maxResult(int[] nums, int k) {
        memo = new int[nums.length];
        Arrays.fill(memo, Integer.MIN_VALUE);
        return dfs(nums, k, 0);
    }

    private int dfs(int[] nums, int k, int start) {
        if (start == nums.length - 1) {
            return nums[nums.length - 1];
        }

        if (memo[start] != Integer.MIN_VALUE) {
            return memo[start];
        }

        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= k && start + i < nums.length; i++) {
            res = Math.max(res, nums[start] + dfs(nums, k, start + i));
        }
        memo[start] = res;
        return res;
    }
}

// Bottom up DP, still TLE, 60/65
class Solution {
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            // except last position, every dp[i] must move 1 step from current position
            dp[i] = i + 1 < nums.length ? dp[i + 1] + nums[i] : nums[i];
            for (int j = 1; j <= k && i + j < nums.length; j++) {
                dp[i] = Math.max(dp[i], dp[i + j] + nums[i]);
            }
        }
        return dp[0];
    }
}

// DP
class Solution {
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MIN_VALUE);

        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            for (int j = Math.max(0, i - k); j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j]);
            }
            dp[i] += nums[i];
        }
        return dp[nums.length - 1];
    }
}