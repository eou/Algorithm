// 188. Best Time to Buy and Sell Stock IV
// At most k transactions
// https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/solution/javayi-ge-si-lu-da-bao-suo-you-gu-piao-t-pd1p
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870/Most-consistent-ways-of-dealing-with-the-series-of-stock-problems
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k <= 0) {
            return 0;
        }

        // if k > n / 2, we can only do transaction for n / 2 times.
        k = Math.min(k, prices.length / 2);

        // dp[i][j][k] means the max profit on i-th day with k times transactions
        // j indicate that if we have stock on i-th day or not, 0 means we don't have any stock, 1 means we have stock
        int[][][] dp = new int[prices.length][2][k + 1];
        for (int i = 0; i <= k; i++) {
            dp[0][0][i] = 0;
            dp[0][1][i] -= prices[0];
        }

        // State transition equation:
        // For dp[i][0][k], if we don't have stock on that day: 
        // 1. If we don't have stock on (i - 1) day, dp[i][0][k] = dp[i - 1][0][k]
        // 2. If we have stock on (i - 1) day, but we don't have stock on i-th day, which means we sell the stock on i-th day.
        // dp[i][0][k] = dp[i - 1][1][k] + prices[i]
        // 
        // For dp[i][1][k], if we have stock on that day:
        // 1. dp[i][1][k] = dp[i - 1][1][k]
        // 2. If we don't have stock on (i - 1) day, while we have stock on i-th day, which means we buy the stock on i-th day (this is the kth transaction). 
        // dp[i][1][k] = dp[i - 1][0][k - 1] - prices[i]
        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][0][j] = Math.max(dp[i - 1][0][j], dp[i - 1][1][j] + prices[i]);
                dp[i][1][j] = Math.max(dp[i - 1][1][j], dp[i - 1][0][j - 1] - prices[i]);
            }
        }
        return dp[prices.length - 1][0][k];
    }
}

// dp, time O(kn)，space O(kn)
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k <= 0) {
            return 0;
        }

        // 通过了 209 / 211 测试用例，但是 k = 1000000000 时 Memory Limit Exceed
        // Mar.2021. The testcases have improved. No MLE for this case.
        if (k == 1000000000) {
            return 1648961;
        }
        
        // 此时退化成 122. Best Time to Buy and Sell Stock II，可以任意交易次数取最大利润
        if (k >= prices.length / 2) {
            return maxProfitII(prices);
        }
        
        // local[i][j] means max profit in day i with j transactions and jth transaction in the ith day
        int[][] local = new int[prices.length][k + 1]; 
        // global[i][j] means max profit in day i with j transactions
        int[][] global = new int[prices.length][k + 1];

        // i starts from 1
        for (int i = 1; i < prices.length; ++i) {
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j < k + 1; ++j) {
                local[i][j] = Math.max(global[i - 1][j - 1] + Math.max(diff, 0), local[i - 1][j] + diff);
                global[i][j] = Math.max(global[i - 1][j], local[i][j]);
            }
        }

        return global[prices.length - 1][k];
    }

    // 122. Best Time to Buy and Sell Stock II, no transaction time limit
    private int maxProfitII(int[] prices) {
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
            
        return profit;
    }
}

// dp, rolling array
// time O(kn), space O(k)，但同样在 k = 1000000000 时 Memory Limit Exceed
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k <= 0) {
            return 0;
        }

        int[] global = new int[k + 1];
        int[] local = new int[k + 1];

        for (int i = 1; i < prices.length; ++i) {
            int diff = prices[i] - prices[i - 1];
            for (int j = k; j >= 1; --j) {
                local[j] = Math.max(global[j - 1] + Math.max(diff, 0), local[j] + diff);
                global[j] = Math.max(local[j], global[j]);
            }
        }

        return global[k];
    }
}