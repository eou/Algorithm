// 125. Backpack II
// 0-1 knapsack problem
// Given n items with size Ai and value Vi, and a backpack with size m. What's the maximum value can you put into the backpack?
// dp
public class Solution {
    public int backPackII(int m, int[] A, int[] V) {
        // dp[i][j] means maximum value from i items and j size
        int[][] dp = new int[A.length + 1][m + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= m; j++) {
                if (j >= A[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + V[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        
        return dp[A.length][m];
    }
}

// rolling array
public class Solution {
    public int backPackII(int m, int[] A, int[] V) {
        // dp[i][j] means maximum value from i items and j size
        int[] dp = new int[m + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = m; j > 0; j--) {
                if (j >= A[i - 1]) {
                    dp[j] = Math.max(dp[j], dp[j - A[i - 1]] + V[i - 1]);
                }
            }
        }

        return dp[m];
    }
}