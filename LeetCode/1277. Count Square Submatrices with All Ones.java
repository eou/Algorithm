// 1277. Count Square Submatrices with All Ones
// DP
// dp[i][j][k], if a square exists, whose side length is k, bottom right point is (i, j)
class Solution {
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int len = Math.min(m, n);

        boolean[][][] dp = new boolean[m][n][len];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0] = (matrix[i][j] == 1);
                res += dp[i][j][0] ? 1 : 0;
            }
        }
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                for (int k = 1; k < len; k++) {
                    dp[i][j][k] = (matrix[i][j] == 1 && dp[i - 1][j][k - 1] && dp[i][j - 1][k - 1] && dp[i - 1][j - 1] [k - 1]);
                    if (dp[i][j][k]) {
                        res++;
                    }
                }
            }
        }

        return res;
    }
}

// dp[i][j], largest side length of the square, whose bottom right point is (i, j)
class Solution {
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int res = 0;

        for (int i = 0; i < m; i++) {
            res += dp[i][0] = matrix[i][0];
        }
        
        for (int j = 0; j < n; j++) {
            res += dp[0][j] = matrix[0][j];
        }
        
        // deduplicate
        if (matrix[0][0] == 1) {
            res--;
        }
        
        // dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    res += dp[i][j];
                }
            }
        }

        return res;
    }
}