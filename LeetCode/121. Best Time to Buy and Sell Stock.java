// 121. Best Time to Buy and Sell Stock
// At most 1 transaction
// 53. Maximum Subarray 
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int min = prices[0], res = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            res = Math.max(res, prices[i] - min);
        }
        return res;
    }
}

// DP
class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[0][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }
}

// Rolling array DP
class Solution {
    public int maxProfit(int[] prices) {
        int hold = -prices[0], notHold = 0;
        for (int price : prices) {
            notHold = Math.max(notHold, hold + price);
            hold = Math.max(hold, -price);
        }

        return notHold;
    }
}