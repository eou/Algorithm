// 121. Best Time to Buy and Sell Stock
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int min = prices[0];
        int profit = 0;
        for (int i = 0; i < prices.length; ++i) {
            min = min > prices[i] ? prices[i] : min;
            if (prices[i] > min) {
                profit = profit < prices[i] - min ? prices[i] - min : profit;
            }
        }

        return profit;
    }
}

class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int min = prices[0];
        int max = 0;
        for (int price : prices) {
            max = Math.max(max, price - min);
            min = Math.min(min, price);
        }
        return max;
    }
}
