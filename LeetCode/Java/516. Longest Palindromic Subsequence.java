// 516. Longest Palindromic Subsequence
class Solution {
    // Brute force DFS, 会 TLE
    public int longestPalindromeSubseq(String s) {
        return helper(0, s.length() - 1, s);
    }

    private int helper(int start, int end, String s) {
        if(start == end) {
            return 1;
        }
        if(start > end) {
            return 0;
        }

        if(s.charAt(start) == s.charAt(end)) {
            return helper(start + 1, end - 1, s) + 2;
        } else {
            return Math.max(helper(start + 1, end, s), helper(start, end - 1, s));
        }
    }
}

class Solution {
    // DFS with memoization
    public int longestPalindromeSubseq(String s) {
        int[][] memo = new int[s.length()][s.length()];
        return helper(0, s.length() - 1, s, memo);
    }

    private int helper(int start, int end, String s, int[][] memo) {
        if(start == end) {
            return 1;
        }
        if(start > end) {
            return 0;
        }
        if(memo[start][end] != 0) {
            return memo[start][end];
        }

        if(s.charAt(start) == s.charAt(end)) {
            memo[start][end] = helper(start + 1, end - 1, s, memo) + 2;
        } else {
            memo[start][end] = Math.max(helper(start + 1, end, s, memo), helper(start, end - 1, s, memo));
        }
        return memo[start][end];
    }
}

class Solution {
    // DP
    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length() + 1][s.length() + 1];
        
        for(int i = 0; i < s.length(); i++) {
            dp[1][i] = 1;
        }
        for(int i = 2; i <= s.length(); i++) {
            for(int j = 0; j < s.length() - i + 1; j++) {
                if(s.charAt(j) == s.charAt(i + j - 1)) {
                    dp[i][j] = dp[i - 2][j + 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[[i - 1][j + 1]]);
                }
            }
        }

        return dp[s.length()][0];
    }
}