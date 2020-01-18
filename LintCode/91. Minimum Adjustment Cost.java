// 91. Minimum Adjustment Cost
public class Solution { 
    public int MinAdjustmentCost(List<Integer> A, int target) {
        // each number is less than 100 no matter how it changed!!!!
        // dp[i][j] means minimum cost when first ith number is j
        int[][] dp = new int[A.size() + 1][100 + 1];
        
        // init
        // dp[1][j]
        for (int i = 1; i <= 100; i++) {
            dp[1][i] = Math.abs(A.get(0) - i);
        }
        
        for (int i = 2; i <= A.size(); i++) {
            for (int j = 1; j <= 100; j++) {
                // dp[i][j]
                dp[i][j] = Integer.MAX_VALUE;
                // try all dp[i - 1][k]
                for (int k = 1; k <= 100; k++) {
                    if (Math.abs(k - j) <= target && dp[i - 1][k] != Integer.MAX_VALUE) {
                        // try this k
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + Math.abs(A.get(i - 1) - j));
                    }
                }
            }
        }
        
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= 100; i++) {
            res = Math.min(res, dp[A.size()][i]);
        }
        return res;
    }
}