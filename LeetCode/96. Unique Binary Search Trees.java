// 96. Unique Binary Search Trees
class Solution {
    public int numTrees(int n) {
        // left subtree * right subtree
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        // root i, left [0, i - 1], right [i + 1, n]
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += (dp[i - j - 1] * dp[j]);
            }
        }
        return dp[n];
    }
}