// 174. Dungeon Game
// dp 必须反向，因为 dp[i][j] 与后面的结果相关，而与前面的结果无关
// dp[i][j - 1] / dp[i - 1][j] 无法推出 dp[i][j], dp[i][j] 必须根据 dp[i + 1][j] / dp[i][j + 1] 判断
// dfs with memo
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        // [],[[]]
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }

        // memo[i][j] record minmum init health start from (i, j) to rescue princess
        int[][] memo = new int[dungeon.length][dungeon[0].length];
        return dfs(dungeon, memo, 0, 0);
    }

    private int dfs(int[][] dungeon, int[][] memo, int x, int y) {
        if (memo[x][y] != 0) {
            return memo[x][y];
        }
        
        if (x == dungeon.length - 1 && y == dungeon[0].length - 1) {
            memo[x][y] = dungeon[x][y] < 0 ? -dungeon[x][y] + 1 : 1;
            return memo[x][y];
        }
        
        int minInitHealth = Integer.MAX_VALUE;
        // go down
        if (x < dungeon.length - 1) {
            int down = dfs(dungeon, memo, x + 1, y);
            minInitHealth = Math.min(minInitHealth, down);
        }
        
        // go right
        if (y < dungeon[0].length - 1) {
            int right = dfs(dungeon, memo, x, y + 1);
            minInitHealth = Math.min(minInitHealth, right);
        }
        
        // If minInitHealth is 6, dungeon[x][y] if 10, then minInitHealth should be updated to 1
        if (dungeon[x][y] >= minInitHealth) {
            minInitHealth = 1;
        } else {
            // If minInitHealth is 6, dungeon[x][y] is 3 or -3, then minInitHealth should be updated to 3 or 9
            minInitHealth = minInitHealth - dungeon[x][y];
        }
        
        memo[x][y] = minInitHealth;
        return minInitHealth;
    }
}

// dp, from bottom-right to (0,0)
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }

        int m = dungeon.length;
        int n = dungeon[0].length;

        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }

        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(dp[m - 1][j + 1] - dungeon[m - 1][j], 1);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                int down = Math.max(dp[i + 1][j] - dungeon[i][j], 1);
                int right = Math.max(dp[i][j + 1] - dungeon[i][j], 1);
                dp[i][j] = Math.min(right, down);
            }
        }

        return dp[0][0];
    }
}

// simpler
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }

        int m = dungeon.length;
        int n = dungeon[0].length;

        // dp[i][j] represents the min hp needed at position (i, j)
        // Add dummy row and column at bottom and right side
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        // for bottom-right position
        dp[m][n - 1] = 1;
        dp[m - 1][n] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = need <= 0 ? 1 : need;
            }
        }
        return dp[0][0];
    }
}