// 70. Climbing Stairs
class Solution {
    // 最基本的dp问题，关系为dp[i] = dp[i - 1] + dp[i - 2]
    public int climbStairs(int n) {
        int dp[] = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
}

class Solution {
    // 暴力递归版本
    public int climbStairs(int n) {
        climb_Stairs(0, n);
    }

    public int climb_Stairs(int i, int n) {
        if (i > n) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        return climb_Stairs(i + 1, n) + climb_Stairs(i + 2, n);
    }
}

class Solution {
    // 其实步数是一个 Fibonacci sequence，所以可以用公式求，时间复杂度为O(logn)，因为pow函数复杂度为此
    public int climbStairs(int n) {
        double sqrt5 = Math.sqrt(5);
        double fibn = Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1);
        return (int) (fibn / sqrt5);
    }
}