// 97. Interleaving String
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if(s1.length() + s2.length() != s3.length()) {
            return false;
        }

        // dp[i][j] means if we can form s3.substring(i + j - 1) by the interleaving of s1.substring(0, i) and s2.substring(0, j) 
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        for(int i = 0; i <= s1.length(); i++) {
            for(int j = 0; j <= s2.length(); j++) {
                if(i + j > 0) {
                    dp[i][j] = false;
                    if (i > 0 && s1.charAt(i - 1) == s3.charAt(i + j - 1)) {
                        dp[i][j] |= dp[i - 1][j];
                    }
                    if (j > 0 && s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
                        dp[i][j] |= dp[i][j - 1];
                    }
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
}

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int[][] memo = new int[s1.length()][s2.length()];
        // 注意二维数组不能直接使用 arrays.fill
        for (int[] m : memo) {
            Arrays.fill(m, -1);
        }

        return helper(s1, 0, s2, 0, s3, 0, memo);
    }

    private boolean helper(String s1, int i, String s2, int j, String s3, int k, int[][] memo) {
        if (i == s1.length()) {
            return s2.substring(j).equals(s3.substring(k));
        }
        if (j == s2.length()) {
            return s1.substring(i).equals(s3.substring(k));
        }
        if (memo[i][j] != -1) {
            return memo[i][j] == 1 ? true : false;
        }
        boolean result = false;
        if ((s3.charAt(k) == s1.charAt(i) && helper(s1, i + 1, s2, j, s3, k + 1, memo))
                || (s3.charAt(k) == s2.charAt(j) && helper(s1, i, s2, j + 1, s3, k + 1, memo))) {
            result = true;
        }
        memo[i][j] = result ? 1 : 0;
        return result;
    }
}

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        // 用对象 Boolean数组 就具有三种状态，不需要用 int 的 -1，0，1 代替
        Boolean[][] memo = new Boolean[s1.length()][s2.length()];
        return helper(s1, 0, s2, 0, s3, 0, memo);
    }

    private boolean helper(String s1, int i, String s2, int j, String s3, int k, Boolean[][] memo) {
        if (i == s1.length()) {
            return s2.substring(j).equals(s3.substring(k));
        }
        if (j == s2.length()) {
            return s1.substring(i).equals(s3.substring(k));
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        boolean result = false;
        if ((s3.charAt(k) == s1.charAt(i) && helper(s1, i + 1, s2, j, s3, k + 1, memo))
                || (s3.charAt(k) == s2.charAt(j) && helper(s1, i, s2, j + 1, s3, k + 1, memo))) {
            result = true;
        }
        memo[i][j] = result;
        return result;
    }
}