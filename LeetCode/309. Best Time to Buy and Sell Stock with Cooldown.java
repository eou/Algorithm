// 309. Best Time to Buy and Sell Stock with Cooldown
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75927/Share-my-thinking-process
// dp
class Solution {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }
        
        int n = prices.length;
        int[] buy = new int[n + 1];
        int[] sell = new int[n + 1];
        int[] cd = new int[n + 1];
        
        buy[0] = Integer.MIN_VALUE;

        for(int i = 1; i <= n; i++) {
            // buy on i - 1 day, or buy after cooldown
            buy[i] = Math.max(buy[i - 1], cd[i - 1] - prices[i - 1]);
            // sell on i - 1 day, or sell after buy
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i - 1]);
            
            cd[i] = Math.max(cd[i - 1], Math.max(buy[i - 1], sell[i - 1]));
        }
        
        return Math.max(buy[n], Math.max(sell[n], cd[n]));
    }
}

// use rest[i] = sell[i-1]:
// buy[i] = max(sell[i-2] - price, buy[i-1])
// sell[i] = max(buy[i-1] + price, sell[i-1])
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int sell = 0, prev_sell = 0, prev_buy = 0;
        int buy = Integer.MIN_VALUE;
        
        for (int price : prices) {
            prev_buy = buy;
            buy = Math.max(prev_buy, prev_sell - price);
            prev_sell = sell;
            sell = Math.max(prev_sell, prev_buy + price);
        }

        return sell;
    }
}