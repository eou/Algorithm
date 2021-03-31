// 122. Best Time to Buy and Sell Stock II
// multiple transactions
// add all increasing subarray sum
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