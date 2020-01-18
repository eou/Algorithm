// 564. Combination Sum IV
// Given an integer array nums with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.
// The different sequences are counted as different combinations.
// Coin Change
// DFS, TLE
public class Solution {
    public int backPackVI(int[] nums, int target) {
        if (target == 0) {
            return 1;
        }

        int res = 0;
        // each time consider all possible num
        for (int num : nums) {
            if (target >= num) {
                res += backPackVI(nums, target - num);
            }
        }
        return res;
    }
}

// DFS with memo
public class Solution {
    public int backPackVI(int[] nums, int target) {
        Integer[] memo = new Integer[target + 1];
        return dfs(nums, target, memo);
    }

    public int dfs(int[] nums, int target, Integer[] memo) {
        if (target == 0) {
            return 1;
        }

        if (memo[target] != null) {
            return memo[target];
        }

        int res = 0;
        for (int num : nums) {
            if (num <= target) {
                res += dfs(nums, target - num, memo);
            }
        }
        memo[target] = res;
        return res;
    }
}

// dp, similar with backpack IV
public class Solution {
    public int backPackVI(int[] nums, int target) {
        // dp[j] means filling j size backpack using all nums
        int[] dp = new int[target + 1];
        dp[0] = 1;
        // backpack IV
        // for (int i = 0; i < nums.length; i++) {
        //     for (int j = nums[i]; j <= target; j++) {
        //         dp[j] += dp[j - nums[i]];
        //     }
        // }
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i - nums[j] >= 0) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }
}