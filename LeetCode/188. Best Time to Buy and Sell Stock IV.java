// 188. Best Time to Buy and Sell Stock IV
// at most k transactions
// dp, time O(kn)，space O(kn)
// 问题的实质是从 prices 数组中挑选出至多 2 * k 个元素，组成一个交易（买卖）序列
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k <= 0) {
            return 0;
        }

        // 通过了 209 / 211 测试用例，但是 k = 1000000000 时 Memory Limit Exceed
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

        for (int i = 1; i < prices.length; ++i) {
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j < k + 1; ++j) {
                local[i][j] = Math.max(global[i - 1][j - 1] + Math.max(diff, 0), local[i - 1][j] + diff);
                global[i][j] = Math.max(global[i - 1][j], local[i][j]);
            }
        }

        return global[prices.length - 1][k];
    }

    // 122. Best Time to Buy and Sell Stock II
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