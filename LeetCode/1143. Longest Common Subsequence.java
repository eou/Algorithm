// 1143. Longest Common Subsequence
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        // longest common subsequence in text1(0, i) and text2(0, j)
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[text1.length()][text2.length()];
    }
}

// 可以用滚动数组节省空间