// 72. Edit Distance
// 《编程之美》3.3 计算字符串的相似度
// for transforming s[1...i] to t[1...j]
// if k operations are needed to transform s[1...i] to t[1...j-1], so just add t[j] to s[1...i]: k + 1
// if k operations are needed to transform s[1...i-1] to t[1...j], so just delete s[i]: k + 1
// if k operations are needed to transform s[1...i-1] to t[1...j-1], so just replace s[i] with t[j] (s[i] != t[j]) or do nothing (s[i] == t[j]): k + 0/1
class Solution {
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        // dp[i][j]: the edit distance between word1[1...i] and word2[1...j]
        int[][] dp = new int[n + 1][m + 1];

        for(int i = 0; i < m + 1; i++) {
            dp[0][i] = i;
        }
        for(int i = 0; i < n + 1; i++) {
            dp[i][0] = i;
        }

        for(int i = 1; i < n + 1; i++) {
            for(int j = 1; j < m + 1; j++) {
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[n][m];
    }
}