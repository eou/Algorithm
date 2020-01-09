// 440. Backpack III 
// unbounded knapsack problem (UKP)
public class Solution {
    public int backPackIII(int[] A, int[] V, int m) {
        if (A == null || A.length == 0 || V == null || V.length == 0 || m <= 0) {
            return 0;
        }

        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0; // 0 items to fill 0 weight, value = 0

        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= m; j++) {
                // add items repeatedly, k times
                for (int k = 0; k * A[i - 1] <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * A[i - 1]] + k * V[i - 1]);
                }
            }
        }

        return dp[A.length][m];
    }
}

// do not need k loop
public class Solution {
    public int backPackIII(int[] A, int[] V, int m) {
        if (A == null || A.length == 0 || V == null || V.length == 0 || m <= 0) {
            return 0;
        }
        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0; // 0 items to fill 0 weight, value = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= A[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - A[i - 1]] + V[i - 1]);
                }
            }
        }
        return dp[n][m];
    }
}

// rolling array
public class Solution {
    public int backPackIII(int[] A, int[] V, int m) {
        if (A == null || A.length == 0 || V == null || V.length == 0 || m <= 0) {
            return 0;
        }
        int n = A.length;
        int[] dp = new int[m + 1]; // DP on weight
        dp[0] = 0; // 0 items to fill 0 weight, value = 0

        for (int i = 1; i <= n; i++) {
            // 正序和逆序的区别就是，对于正序，j以前的状态是当前行的状态；对于逆序，j以前的状态是上一个行的状态
            for (int j = A[i - 1]; j <= m && j >= A[i - 1]; j++) {
                dp[j] = Math.max(dp[j], dp[j - A[i - 1]] + V[i - 1]);
            }
        }
        return dp[m];
    }
}
