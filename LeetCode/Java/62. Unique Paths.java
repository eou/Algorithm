// 62. Unique Paths
class Solution {
    public int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[0][0] = 1;
                } else if (i == 0) {
                    dp[i][j] += dp[i][j - 1];
                } else if (j == 0) {
                    dp[i][j] += dp[i - 1][j];
                } else {
                    dp[i][j] += (dp[i][j - 1] + dp[i - 1][j]);
                }
            }
        }
        
        return dp[m - 1][n - 1];
    }
}

// C(m + n, m) = (m + n)! / (m! * n!)
// D R R R D R R R
class Solution {
    public int uniquePaths(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }
            
        m--;
        n--;
        // Swap, so that m is the bigger number
        // can use bitwise swap
        if (m < n) {
            m = m + n;
            n = m - n;
            m = m - n;
        }

        long res = 1;
        // Instead of taking factorial, keep on multiply & divide
        for (int i = m + 1, j = 1; i <= m + n; i++, j++) {
            res *= i;
            res /= j;
        }

        return (int) res;
    }
}