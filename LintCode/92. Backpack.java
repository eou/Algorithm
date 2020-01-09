// 92. Backpack
// 0-1 knapsack problem
// Given n items with size Ai, an integer m denotes the size of a backpack. How full you can fill this backpack?
// 2d dp
public class Solution {
    public int backPack(int m, int[] A) {
        // dp[i][j] means the maximum quantity we can get given i items, j volume
        int[][] dp = new int[A.length + 1][m + 1];
        // dp[i][0] = 0
        // dp[0][j] = 0
        for (int i = 1; i < A.length + 1; i++) {
            // decide when to add item A[i - 1]
            for (int j = 1; j < m + 1; j++) {
                if (j >= A[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + A[i - 1]);
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
    public int backPack(int m, int[] A) {
        // dp[i][j] means the maximum quantity we can get given i items, j volume
        int[] dp = new int[m + 1];
        // dp[i][0] = 0
        // dp[0][j] = 0
        for (int i = 1; i < A.length + 1; i++) {
            // decide when to add item A[i - 1]
            // reverse scan!
            for (int j = m; j > 0; j--) {
                if (j >= A[i - 1]) {
                    dp[j] = Math.max(dp[j], dp[j - A[i - 1]] + A[i - 1]);
                }
            }
        }
        return dp[m];
    }
}

// another dp, can also optimize space
public class Solution {
    public int backPack(int m, int[] A) {
        // dp[i][j] means if we can filled j volume with i items
        boolean[][] dp = new boolean[A.length + 1][m + 1];

        for (int i = 0; i <= A.length; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= m; j++) {
                if (j >= A[i - 1]) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - A[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // check start from biggest volume
        for (int j = m; j >= 0; j--) {
            if (dp[A.length][j]) {
                return j;
            }
        }

        return 0;
    }
}