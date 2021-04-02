// 122. Best Time to Buy and Sell Stock II
// Unlimit transactions
// Add all increasing subarray sum
class Solution {
    public int maxProfit(int[] prices) {
        int res = 0, cur = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] <= prices[i + 1]) {
                // increasing
                cur += prices[i + 1] - prices[i];
            } else {
                // decreasing
                res += cur;
                cur = 0;
            }
        }

        if (cur > 0) {
            res += cur;
        }

        return res;
    }
}

// just keep track of ascending part of the stock
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int profit = 0;
        for (int i = 0; i < prices.length - 1; ++i) {
            if (prices[i] < prices[i + 1]) {
                profit += (prices[i + 1] - prices[i]);
            }
        }
        
        return profit;
    }
}

// DP
class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];

        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }
}

// Rolling array DP
class Solution {
    public int maxProfit(int[] prices) {
        int hold = -prices[0], notHold = 0;

        for (int price : prices) {
            int preNotHold = notHold;
            notHold = Math.max(notHold, hold + price);
            hold = Math.max(hold, preNotHold - price);
        }

        return notHold;
    }
}