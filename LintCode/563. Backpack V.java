// 563. Backpack V
// leetcode 518. Coin Change 2 follow up
public class Solution {
    public int backPackV(int[] nums, int target) {
        Long[][] memo = new Long[target + 1][nums.length + 1];
        return (int)dfs(target, nums, 0, memo);
    }
    
    public long dfs(int target, int[] nums, int index, Long[][] memo) {
        // exit
        if (target == 0) {
            return 1;
        } else if (index >= nums.length) {
            return 0;
        }

        // memoization
        if (memo[target][index] != null) {
            return memo[target][index];
        }

        long res = 0;
        // can only use current item once 
        if (nums[index] <= target) {
            // take
            res += dfs(target - nums[index], nums, index + 1, memo);
            // not take
            res += dfs(target, nums, index + 1, memo);
        } else {
            res += dfs(target, nums, index + 1, memo);
        }

        memo[target][index] = res;
        return res;
    }
}

// DP
public class Solution {
    public int backPackV(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            // reverse scan
            // make sure dp[j - nums[i]] must not take nums[i] before dp[j]
            // for (int j = nums[i]; j <= target; j++) {
            for (int j = target; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}

// DP, optimized
// current sum cannot be larget than nums[0] +...+ nums[i]
public class Solution {
    public int backPackV(int[] nums, int target) {
        int prefix = 0;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            prefix += nums[i];
            // reverse scan
            // make sure dp[j - nums[i]] must not take nums[i] before dp[j]
            // for (int j = nums[i]; j <= target; j++) {
            for (int j = Math.min(target, prefix); j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}
