// 198. House Robber
class Solution {
    // 直接 DFS 会 TLE
    public int rob(int[] nums) {
        return helper(nums, nums.length - 1);
    }

    private int helper(int[] nums, int i) {
        if(i < 0) {
            return 0;
        }

        return Math.max(helper(nums, i - 2) + nums[i], helper(nums, i - 1));
    }
}

class Solution {
    public int[] memo;

    public int rob(int[] nums) {
        memo = new int[nums.length];
        Arrays.fill(memo, -1);
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // DFS
        return dfs(nums, 0);
    }

    public int dfs(int[] nums, int i) {
        if (i == nums.length - 1) {
            return nums[i];
        }
        if (i == nums.length - 2) {
            return Math.max(nums[i], nums[i + 1]);
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int res = Math.max(dfs(nums, i + 1), dfs(nums, i + 2) + nums[i]);
        memo[i] = res;
        return res;
    }
}

class Solution {
    // DFS with memoization
    public int rob(int[] nums) {
        Integer[] memo = new Integer[nums.length + 1];
        return helper(nums, nums.length - 1, memo);
    }

    private int helper(int[] nums, int i, Integer[] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i] != null) {
            return memo[i];
        }

        int result = Math.max(helper(nums, i - 2, memo) + nums[i], helper(nums, i - 1, memo));
        memo[i] = result;
        return result;
    }
}

class Solution {
    // DP 版本
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for(int i = 1; i < nums.length; i++) {
            dp[i + 1] = Math.max(dp[i], dp[i - 1] + nums[i]);
        }

        return dp[nums.length];
    }
}

class Solution {
    // 空间复杂度为 O(1)
    public int rob(int[] nums) {
        // prev => curr => next
        // next = max(prev + num, curr)
        int prev = 0, curr = 0;
        for (int num : nums) {
            int next = Math.max(prev + num, curr);
            prev = curr;
            curr = next;
        }
        return curr;
    }
}
