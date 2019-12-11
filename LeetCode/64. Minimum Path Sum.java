// 64. Minimum Path Sum
class Solution {
    public int minPathSum(int[][] grid) {
        // dp[i][j] means minimum sum of all numbers in the path from grid[0][0] to grid[i][j]
        int[][] dp = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else {
                    int top = i - 1 >= 0 ? dp[i - 1][j] : Integer.MAX_VALUE;
                    int left = j - 1 >= 0 ? dp[i][j - 1] : Integer.MAX_VALUE;
                    dp[i][j] = grid[i][j] + Math.min(top, left);
                }
            }
        }
        return dp[grid.length - 1][grid[0].length - 1];
    }
}

class Solution {
    // only need to memorize left and top value
    public int minPathSum(int[][] grid) {
        int[] topDp = new int[grid[0].length];
        Arrays.fill(topDp, Integer.MAX_VALUE);
        int cur = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            int left = Integer.MAX_VALUE;
            for (int j = 0; j < grid[i].length; j++) {
                if (i == 0 && j == 0) {
                    cur = grid[i][j];
                } else {
                    int top = topDp[j];
                    cur = grid[i][j] + Math.min(top, left);
                }
                left = cur;
                topDp[j] = cur;
            }
        }
        return cur;
    }
}

class Solution {
    public int minPathSum(int[][] grid) {
        int[] dp = new int[grid[0].length];
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--) {
                if (i == grid.length - 1 && j != grid[0].length - 1)
                    dp[j] = grid[i][j] + dp[j + 1];
                else if (j == grid[0].length - 1 && i != grid.length - 1)
                    dp[j] = grid[i][j] + dp[j];
                else if (j != grid[0].length - 1 && i != grid.length - 1)
                    dp[j] = grid[i][j] + Math.min(dp[j], dp[j + 1]);
                else
                    dp[j] = grid[i][j];
            }
        }
        return dp[0];
    }
}
