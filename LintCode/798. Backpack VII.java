// 798. Backpack VII
// bounded knapsack problem (BKP)
// Assume that you havenyuan. There are many kinds of rice in the supermarket.
// Each kind of rice is bagged and must be purchased in the whole bag. Given
// theweight,priceandquantityof each type of rice, findthe maximum weightof rice
// that you can purchase.
public class Solution {
    /**
     * @param n:       the money of you
     * @param prices:  the price of rice[i]
     * @param weight:  the weight of rice[i]
     * @param amounts: the amount of rice[i]
     * @return: the maximum weight
     */
    public int backPackVII(int n, int[] prices, int[] weight, int[] amounts) {
        // dp[i][j] means from first ith prices, using j money, get maximum weight
        int[][] dp = new int[prices.length + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= prices.length; i++) {
            for (int j = 1; j <= n; j++) {
                // do not take item i
                dp[i][j] = dp[i - 1][j];
                // take item i
                // decide number of item i
                for (int k = 1; k <= amounts[i - 1]; k++) {
                    // enough money
                    if (j >= k * prices[i - 1]) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * prices[i - 1]] + k * weight[i - 1]);
                    }
                }
            }
        }
        return dp[prices.length][n];
    }
}