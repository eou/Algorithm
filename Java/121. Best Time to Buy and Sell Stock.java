// 121. Best Time to Buy and Sell Stock
class Solution {
    public int maxProfit(int prices[]) {
        int max = 0;
        if (prices.length < 2) {
            return 0;
        }
        int minIndex = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[minIndex]) {
                minIndex = i;
            }
            max = prices[i] - prices[minIndex] > max ? prices[i] - prices[minIndex] : max;
        }
        return max;
    }
}
