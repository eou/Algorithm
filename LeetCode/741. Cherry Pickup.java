// 741. Cherry Pickup
// https://blog.csdn.net/luke2834/article/details/79365645
// https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-741-cherry-pickup
// assume 2 paths have same direction, from top-left to bottom-right
// thus 3 variables, use rolling array can ignore 1 variable, compress space
// dp(t, x1, x2) = grid(x1, t - x1) + 
//                 (x1 == x2 ? 0 : grid(x2, t - x2)) + 
//                  max(dp(t - 1, x1, x2), dp(t - 1, x1, x2 - 1), dp(t - 1, x1 - 1, x2), dp(t - 1, x1 - 1, x2 - 1))

class Solution {
    public int cherryPickup(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        dp[0][0] = grid[0][0];
        
        // on step x, path 1 is on the position (i, x - i), path 2 is on the position (j, x - j)
        for (int step = 1; step < grid.length * grid.length - 1; step++) {
            // i is x-coordinate for path 1
            for (int i = Math.min(grid.length - 1, step); i >= 0 && step - i <= grid.length - 1; i--) {
                // j is x-coordinate for path 2
                for (int j = Math.min(grid.length - 1, step); j >= 0 && step - j <= grid.length - 1; j--) {
                    // dp[i, j] = dp(t - 1, i, j)
                    // cannot get to this cell
                    if (grid[i][step - i] == -1 || grid[j][step - j] == -1) {
                        dp[i][j] = -1;
                        continue;
                    }
                    
                    // at first time, this dp[i][j] is from last loop
                    if (step - i > 0 && j > 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                    }
                    
                    if (i > 0) {
                        if (j > 0) {
                            dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1]);
                        }
                        if (step - j > 0) {
                            dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                        }
                    }
                    
                    // all paths are blocked
                    if (dp[i][j] == -1) {
                        continue;
                    }
                    
                    if (i == j) {
                        dp[i][j] += grid[i][step - i];
                    } else {
                        dp[i][j] += grid[i][step - i] + grid[j][step - j];
                    }
                    // dp[i, j] = dp(t, i, j)
                }
            }
        }
        
        return dp[grid.length - 1][grid.length - 1] == -1 ? 0 : dp[grid.length - 1][grid.length - 1];
        // return Math.max(dp[grid.length - 1][grid.length - 1], 0); // faster???
    }
}

// Key observation: (0,0) to (n-1, n-1) to (0, 0) is the same as (n-1, n-1) to (0, 0) twice
// Two people starting from (n-1, n-1) and go to (0, 0).
// They move one step (left or up) at a time simultaneously. And pick up the cherry within the grid (if there is one).
// if they ended up at the same grid with a cherry. Only one of them can pick up it.
// x1, y1, x2 to represent a state y2 can be computed: y2 = x1 + y1 – x2
// dp(x1, y1, x2) computes the max cherries if start from {(x1, y1), (x2, y2)} to (0, 0), which is a recursive function.

// dfs with memo, TLE, 31/56
class Solution {
    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        int[][][] memo = new int[N][N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        // start from bottom right
        return Math.max(0, dfs(grid, memo, N - 1, N - 1, N - 1));
    }

    private int dfs(int[][] grid, int[][][] memo, int x1, int y1, int x2) {
        int y2 = x1 + y1 - x2;

        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
            return -1;
        }

        if (grid[x1][y1] == -1 || grid[x2][y2] == -1) {
            return -1;
        }

        // start point
        if (x1 == 0 && y1 == 0) {
            return grid[x1][y1];
        }

        // use memo
        if (memo[x1][y1][x2] != -1) {
            return memo[x1][y1][x2];
        }

        // start calculation
        // 4 directions
        memo[x1][y1][x2] = Math.max(
            Math.max(dfs(grid, memo, x1 - 1, y1, x2 - 1), dfs(grid, memo, x1, y1 - 1, x2)),
            Math.max(dfs(grid, memo, x1, y1 - 1, x2 - 1), dfs(grid, memo, x1 - 1, y1, x2))
        );

        // if still -1, this cell cannot be reached
        if (memo[x1][y1][x2] != -1) {
            memo[x1][y1][x2] += grid[x1][y1];
            if (x1 != x2) {
                memo[x1][y1][x2] += grid[x2][y2];
            }
        }

        return memo[x1][y1][x2];
    }
}