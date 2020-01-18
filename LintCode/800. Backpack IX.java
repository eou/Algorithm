// 800. Backpack IX
/*
Description
You have a total of n thousand yuan, hoping to apply for a university abroad.
The application is required to pay a certain fee.
Give the cost of each university application and the probability of getting the University's offer,
and the number of university is m. If the economy allows, you can apply for multiple universities.
Find the highest probability of receiving at least one offer.
Notice
0<=n<=10000,0<=m<=10000
Example
Given:
n = 10
prices = [4,4,5]
probability = [0.1,0.2,0.3]
Return:0.440
Tags
Backpack Dynamic Programming
 */

/**
 * 转换为一个 offer 都没有收到的最小概率 Complementary event
 */
public class Solution {
    /**
     * @param n:           Your money
     * @param prices:      Cost of each university application
     * @param probability: Probability of getting the University's offer
     * @return: the highest probability
     */
    public double backpackIX(int n, int[] prices, double[] probability) {
        if (probability == null || probability.length == 0) {
            return 0.0;
        }

        double[] dp = new double[n + 1];
        Arrays.fill(dp, 1.0); // init

        // Complementary event
        for (int i = 0; i < probability.length; i++) {
            probability[i] = 1 - probability[i];
        }

        // 0 - 1 knapsack
        for (int i = 0; i < probability.length; i++) {
            // Gurantee the money is enough to pay pricees[i] fee
            for (int j = n; j >= prices[i]; j--) {
                dp[j] = Math.min(dp[j], dp[j - prices[i]] * probability[i]);
            }
        }
        return 1 - dp[n];
    }
}

public class Solution {
    public double backpackIX(int n, int[] prices, double[] probability) {
        // write your code here
        // 计算一个offer都收不到的概率，然后减掉
        // 每个offer都有收或不收两种选择，0-1背包
        // 确定状态：dp[i][j] = 在i所学校中，花费j元，一个offer都收不到的最小概率
        // 转移方程：dp[i][j] = min(dp[i - 1][j], dp[i - 1][j - prices[i]] * (1 -
        // probability[i])) 不申请当前学校vs申请当前学校
        // 初始条件&边界情况：dp[0][0] = 1.0, j == 0, dp = 1.0, i == 0, dp = 1.0
        double[][] dp = new double[prices.length + 1][n + 1];

        for (int i = 0; i <= prices.length; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 1.0;
                else {
                    dp[i][j] = dp[i - 1][j]; // 不申请当前学校
                    if (j >= prices[i - 1]) { // 可申请当前学校
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - prices[i - 1]] * (1 - probability[i - 1]));
                    }
                }
            }
        }

        return 1 - dp[prices.length][n];
    }
}
