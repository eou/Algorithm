// 256. Paint House
class Solution {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        // dp[i][j] means the minimum cost of painting house i with color j, j = 0, 1, 2
        int[][] dp = new int[costs.length][3];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0) {
                    dp[i][j] = costs[i][j];
                } else {
                    dp[i][j] = Math.min(
                        // -1 % 3 == -1 ????
                        dp[i - 1][(j - 1 + 3) % 3], dp[i - 1][(j + 1 + 3) % 3]
                    ) + costs[i][j];
                }
            }
        }
        
        return Math.min(dp[costs.length - 1][0], Math.min(dp[costs.length - 1][1], dp[costs.length - 1][2]));
    }
}

class Solution {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        for (int i = 1; i < costs.length; i++) {
            costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
            costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] += Math.min(costs[i - 1][1], costs[i - 1][0]);
        }

        int n = costs.length - 1;
        return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
    }
}
