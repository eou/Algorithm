// 123. Best Time to Buy and Sell Stock III
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int[] left = new int[prices.length];    // left[i] means max profit before day i
        int[] right = new int[prices.length];   // right[i] means max profit after day i
        
        int valley = prices[0];
        for (int i = 0; i < prices.length; ++i) {
            valley = valley > prices[i] ? prices[i] : valley;
            if (i > 0) {
                left[i] = Math.max(left[i - 1], prices[i] - valley);
            }
        }
        
        int peak = prices[prices.length - 1];
        for (int i = prices.length - 1; i >= 0; --i) {
            peak = peak < prices[i] ? prices[i] : peak;
            if (i < prices.length - 1) {
                right[i] = Math.max(right[i + 1], peak - prices[i]);
            }
        }
        
        int profit = 0;
        for (int i = 0; i < prices.length; ++i) {
            profit = Math.max(profit, left[i] + right[i]);
        }
        
        return profit;
    }
}