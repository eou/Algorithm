// 410. Split Array Largest Sum
class Solution {
    public int splitArray(int[] nums, int m) {
        int max = nums[0], sum = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }

        int left = max, right = sum;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canSplit(nums, mid, m)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public boolean canSplit(int[] nums, int sum, int m) {
        int cur = 0;
        for (int num : nums) {
            cur += num;
            if (cur > sum) {
                m--;
                cur = num;
            }
            if (m <= 0) {
                return false;
            }
        }
        return true;
    }
}

// DFS with memoization
class Solution {
    public Integer[][] memo;
    public long[] sum;      // integer overflow

    public int splitArray(int[] nums, int m) {
        memo = new Integer[nums.length][m + 1];
        sum = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sum[i] = (i > 0 ? sum[i - 1] : 0) + nums[i];
        }
        return (int) dfs(nums, nums.length - 1, m);
    }

    public long dfs(int[] nums, int len, int m) {
        if (m == 1) {
            return sum[len];
        }
        if (m > len + 1) {
            return Long.MAX_VALUE;
        }
        if (memo[len][m] != null) {
            return (long) memo[len][m];
        }

        long res = Long.MAX_VALUE;
        for (int i = 0; i <= len; i++) {
            // memo[i][m] => memo[len][m]
            res = Math.min(res, Math.max(dfs(nums, i, m - 1), (sum[len] - sum[i])));
        }
        memo[len][m] = (int) res;
        return res;
    }
}

// DFS => DP
class Solution {
    public int splitArray(int[] nums, int m) {
        Long[][] dp = new Long[nums.length][m + 1];     // Long, not long
        long[] sum = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sum[i] = (i > 0 ? sum[i - 1] : 0) + nums[i];
        }

        // len = 1
        for (int i = 0; i < nums.length; i++) {
            dp[i][1] = sum[i];
        }
        // dp[i][j]: min of largest sum of splitting nums[0] ~ nums[i] into j groups.
        for (int i = 2; i <= m; i++) {
            for (int j = i - 1; j < nums.length; j++) {
                for (int k = 0; k < j; k++) {
                    long largest = dp[k][i - 1] != null ? Math.max(dp[k][i - 1], sum[j] - sum[k]) : Long.MAX_VALUE;
                    dp[j][i] = dp[j][i] != null ? Math.min(dp[j][i], largest) : largest;
                }
            }
        }

        long res = dp[nums.length - 1][m];
        return (int) res;
    }
}