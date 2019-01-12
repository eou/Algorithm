// 55. Jump Game
class Solution {
    // DFS，时间复杂度为 O(2^n)，会 TLE
    public boolean canJump(int[] nums) {
        return helper(nums, 0);
    }

    private boolean helper(int[] nums, int start) {
        if(start == nums.length - 1) {
            return true;
        }

        int furthest = Math.min(nums[start] + start, nums.length - 1);
        for(int next = start + 1; next <= furthest; next++) {
            if(helper(nums, next)) {
                return true;
            }
        }

        return false;
    }
}

class Solution {
    // DFS with memoization，top down，时间复杂度为 O(n^2)
    public boolean canJump(int[] nums) {
        Boolean[] jump = new Boolean[nums.length];
        jump[jump.length - 1] = true;
        return helper(nums, 0, jump);
    }

    private boolean helper(int[] nums, int start, Boolean[] jump) {
        if(jump[start] != null) {
            return jump[start];
        }

        int furthest = Math.min(nums[start] + start, nums.length - 1);
        for (int next = start + 1; next <= furthest; next++) {
            if (helper(nums, next, jump)) {
                jump[start] = true;
                return true;
            }
        }

        jump[start] = false;
        return false;
    }
}

class Solution {
    // DP 版本，bottom up，时间复杂度为 O(n^2)
    public boolean canJump(int[] nums) {
        Boolean[] dp = new Boolean[nums.length];
        dp[nums.length - 1] = true;

        for (int i = nums.length - 2; i >= 0; i--) {
            int furthest = Math.min(nums[i] + i, nums.length - 1);
            for (int j = i + 1; j <= furthest; j++) {
                if (dp[j] != null && dp[j] == true) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0] != null && dp[0] == true;
    }
}

class Solution {
    // 贪心版本，时间复杂度为 O(n)
    public boolean canJump(int[] nums) {
        int furthest = nums[0];
        for(int i = 1; i < nums.length && furthest >= i; i++) {
            furthest = Math.max(furthest, nums[i] + i);
        }

        return furthest >= (nums.length - 1) ? true : false; // 这里注意是不小于
    }
}