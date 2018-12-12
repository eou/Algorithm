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
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int prev = 0;
        int curr = 0;
        for (int n : nums) {
            int tmp = curr;
            curr = Math.max(prev + n, curr);
            prev = tmp;
        }

        return curr;
    }
}