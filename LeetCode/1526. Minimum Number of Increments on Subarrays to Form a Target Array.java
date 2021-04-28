// 1526. Minimum Number of Increments on Subarrays to Form a Target Array
// !!! Difference array
class Solution {
    public int minNumberOperations(int[] target) {
        int n = target.length;
        if (n == 0) {
            return 0;
        }
        
        int[] dp = new int[n];
        dp[0] = target[0];
        for (int i = 1; i < n; i ++) {
            if (target[i] <= target[i - 1]) {
                dp[i] = dp[i - 1];
            } else {
                dp[i] = dp[i - 1] + target[i] - target[i-1];
            }
        }

        return dp[n - 1];
    }
}

// Simplified by dp version, also can be approved by difference array
class Solution {
    public int minNumberOperations(int[] target) {
        int n = target.length;
        int res = target[0];
        for (int i = 1; i < n; ++i) {
            // only sum up those differences greater than 0
            res += Math.max(target[i] - target[i - 1], 0);
        }
        return res;
    }
}