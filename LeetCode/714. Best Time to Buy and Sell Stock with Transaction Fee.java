// 714. Best Time to Buy and Sell Stock with Transaction Fee
// DP
class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int[][] dp = new int[prices.length][2];
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }
}

// rolling array
class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int hold = -prices[0], notHold = 0;
        for (int price : prices) {
            int preNotHold = notHold;
            notHold = Math.max(notHold, hold + price - fee);
            hold = Math.max(hold, preNotHold - price);
        }

        return notHold;
    }
}