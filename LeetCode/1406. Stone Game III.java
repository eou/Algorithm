// 1406. Stone Game III
// https://leetcode-cn.com/problems/stone-game-iii/solution/ling-he-dui-shou-cai-qu-zui-you-de-fen-zui-shao-sh/

// 1 <= values.length <= 50000
// -1000 <= values[i] <= 1000
// DP, dp[i] means highest score current player can get starting from ith choice
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int[] dp = new int[50010];
        int sum = 0;
        for (int i = stoneValue.length - 1; i >= 0; i--) {
            dp[i] = -10000;
            sum += stoneValue[i];
            for (int j = 1; j <= 3; j++) {
                dp[i] = Math.max(dp[i], sum - dp[i + j]);
            }
        }

        if(sum - dp[0] == dp[0]) {
            return "Tie";
        } else if (sum - dp[0] > dp[0]) {
            // After Alice's first choice, Bob get more
            return "Bob";
        }
        return "Alice";
    }
}

// DFS with memo, check Alice's final optimal scores
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int diff = dfs(stoneValue, 0, 1, new Integer[stoneValue.length][2]);

        if (diff > 0) {
            return "Alice";
        } else if (diff < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }

    private int dfs(int[] stoneValue, int start, int player, Integer[][] dp) {
        if (start >= stoneValue.length) {
            return 0;
        }

        if (dp[start][player] != null) {
            return dp[start][player];
        }

        int res = player == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        int score = 0;
        for (int i = start; i < Math.min(stoneValue.length, start + 3); i++) {
            if (player == 1) {
                // Alice
                score += stoneValue[i];
                res = Math.max(res, score + dfs(stoneValue, i + 1, 0, dp));
            } else {
                // Bob
                score -= stoneValue[i];
                res = Math.min(res, score + dfs(stoneValue, i + 1, 1, dp));
            }
        }
        return dp[start][player] = res;
    }
}