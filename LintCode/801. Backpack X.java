// 801. Backpack X.java
/*
Description
You have a total of n yuan. Merchant has three merchandises and their prices are 150 yuan,
250 yuan and 350 yuan. And the number of these merchandises can be considered as infinite.
After the purchase of goods need to be the remaining money to the businessman as a tip,
finding the minimum tip for the merchant.
Example
Given: n = 900
Return: 0

dp[i]表示：当金额为 j 时，最多可以花掉多少钱。
因此结果为：n - dp[n]
 */
public class Solution {
    /**
     * @param n: the money you have
     * @return: the minimum money you have to give
     */
    public int backPackX(int n) {
        final int[] PRICES = new int[]{150, 250, 350};
        int[] dp = new int[n + 1];
        // Initialize
        for (int i = 0; i * PRICES[0] <= n; i++) {
            dp[i * PRICES[0]] = i * PRICES[0];
        }

        // Function
        for (int i = 1; i < 3; i++) {
            // Gurantee the enough is enough to pay PRICES[i]
            for (int j = PRICES[i]; j <= n; j++) {
                dp[j] = Math.max(dp[j], dp[j - PRICES[i]] + PRICES[i]);
            }
        }
        // Answer
        return n - dp[n];
    }
}