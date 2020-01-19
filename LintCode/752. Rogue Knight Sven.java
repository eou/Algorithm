// 752. Rogue Knight Sven
// DFS, TLE
public class Solution {
    /**
     * @param n: the max identifier of planet.
     * @param m: gold coins that Sven has.
     * @param limit: the max difference.
     * @param cost: the number of gold coins that reaching the planet j through the portal costs.
     * @return: return the number of ways he can reach the planet n through the portal.
     */
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        return dfs(0, n, m, limit, cost);
    }
    
    public long dfs(int cur, int n, int m, int limit, int[] cost) {
        if (cur == n) {
            return 1; // find 1 possibility
        }
        
        int res = 0;
        for (int i = 1; i <= limit; i++) {
            // go to next planet cur + i
            if (cur + i <= n && m - cost[cur + i] >= 0) {
                res += dfs(cur + i, n, m - cost[cur + i], limit, cost);
            }
        }
        
        return res;
    }
}

// DFS + memo
public class Solution {
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        Long[][] memo = new Long[n + 1][m + 1];
        return dfs(0, n, m, limit, cost, memo);
    }
    
    public long dfs(int cur, int n, int m, int limit, int[] cost, Long[][] memo) {
        if (memo[cur][m] != null) {
            return memo[cur][m];
        }
        
        // exit
        if (cur == n) {
            return 1; // find 1 possibility
        }
        
        long res = 0;
        for (int i = 1; i <= limit; i++) {
            // go to next planet cur + i
            if (cur + i <= n && m - cost[cur + i] >= 0) {
                res += dfs(cur + i, n, m - cost[cur + i], limit, cost, memo);
            }
        }
        
        memo[cur][m] = res;
        return res;
    }
}

// DP
// DP1's table is prefix sum of DP2, same transition function !!!
// 1. dp[i][j] means reach planet i using at most j coins (cost <= j)
public class Solution {
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        // dp[i][j] means number of ways reaching planet i (i+1th) using at most j gold coins
        long[][] dp = new long[n + 1][m + 1];
        
        // dp[0][j] = 1
        // dp[i][0] = ? cost[i] might be 0 or not 0
        for (int i = 0; i <= m; i++) {
            dp[0][i] = 1;
        }
        
        // dp[i][j] = sum of dp[x][y], x >= i - limit,y = j - cost[i], jump from x
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                // enough coins
                if (j - cost[i] >= 0) {
                    // start from previous planet
                    for (int k = Math.max(0, i - limit); k < i; k++) {
                        dp[i][j] += dp[k][j - cost[i]];
                    }
                }
            }
        }
        
        return dp[n][m];
    }
}

// 2. dp[i][j] means reach planet i using exactly j coins (cost = j)
public class Solution {
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        // dp[i][j] means number of ways reaching planet i (i+1th) using exactly j gold coins
        long[][] dp = new long[n + 1][m + 1];
        
        dp[0][0] = 1; // exactly !!!
        
        // dp[i][j] = sum of dp[x][y], x >= i - limit,y = j - cost[i], jump from x
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                // enough coins
                if (j - cost[i] >= 0) {
                    // start from previous planet
                    // this loop can be optimized using prefix sum array
                    for (int k = Math.max(0, i - limit); k < i; k++) {
                        dp[i][j] += dp[k][j - cost[i]];
                    }
                }
            }
        }

        // add all possiblities which can reach planet n
        long res = 0;
        for (int i = 0; i <= m; i++) {
            res += dp[n][i];
        }
        return res;
    }
}

// 2.' use prefix sum array optimize time
// prefix[i][j]表示第j列中从第0行到第i行的和
// dp[i][j]是第j + cost[i]列的第i-limit到第i-1行的和
public class Solution {
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        // dp[i][j] means number of ways reaching planet i (i+1th) using exactly j gold coins
        long[][] dp = new long[n + 1][m + 1];
        long[][] prefix = new long[n + 1][m + 1];
        
        dp[0][0] = 1; // exactly !!!
        prefix[0][0] = 1;
        
        // dp[i][j] = sum of dp[x][y], x >= i - limit,y = j - cost[i], jump from x
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                prefix[i][j] = prefix[i - 1][j];
                // enough coins
                if (j - cost[i] >= 0) {
                    int k = Math.max(0, i - limit);
                    if (k == 0) {
                        dp[i][j] = prefix[i - 1][j - cost[i]];
                    } else {
                        dp[i][j] = prefix[i - 1][j - cost[i]] - prefix[k - 1][j - cost[i]];
                    }
                    
                    prefix[i][j] += dp[i][j];
                }
            }
        }

        // add all possiblities which can reach planet n
        long res = 0;
        for (int i = 0; i <= m; i++) {
            res += dp[n][i];
        }
        return res;
    }
}

// 3. dp[i][j] means reach planet i with j coins remaining (rest = j)
public class Solution {
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        long[][] dp = new long[n + 1][m + 1];

        dp[0][m] = 1; // start from planet 0, remaining coins are m
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = Math.max(0, i - limit); k < i; k++) {
                    if (j + cost[i] <= m) {
                        dp[i][j] += dp[k][j + cost[i]];
                    }
                }
            }
        }

        long res = 0;
        for (int i = 0; i <= m; i++) {
            res += dp[n][i];
        }
        return res;
    }
}

// 3.' 观察 dp[i][j] 最里层循环 dp[i][j] += dp[k][j + cost[i]]; 用空间换时间  min(0, i - limit) <= k <= i - 1
// dp[i][j] = sum of dp[x...i - 1][j + cost[i]], x could be 0 or i - limit
public class Solution {
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        long[][] dp = new long[n + 1][m + 1];
        // use space trade time
        long [][] prefix = new long[n + 1][m + 1]; // prefix can be O(n), use rolling array

        dp[0][m] = 1; // start from planet 0, remaining coins are m
        prefix[0][m] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                prefix[i][j] = prefix[i - 1][j];
                if (j + cost[i] <= m) {
                    int k = Math.max(0, i - limit);
                    if (k == 0) {
                        dp[i][j] = prefix[i - 1][j + cost[i]];
                    } else {
                        // need remove prefix[k - 1][j + cost[i]] from last calculation
                        dp[i][j] = prefix[i - 1][j + cost[i]] - prefix[k - 1][j + cost[i]];
                    }

                    prefix[i][j] += dp[i][j];
                }
            }
        }

        long res = 0;
        for (int i = 0; i <= m; i++) {
            res += dp[n][i];
        }
        return res;
    }
}
