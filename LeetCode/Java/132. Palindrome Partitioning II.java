// 132. Palindrome Partitioning II
class Solution {
    public int minCut(String s) {
        int dp[] = new int[s.length() + 1]; // dp[i] means minimum cut for s.substring(0, i)

        // dp array initialization
        for (int i = 0; i < dp.length; ++i) {
            dp[i] = i - 1;
        }

        for (int i = 0; i < dp.length; ++i) {
            // ...bab...
            for (int len = 0; i + len < s.length() && i - len >= 0; ++len) {
                if (s.charAt(i + len) == s.charAt(i - len)) {
                    dp[i + len + 1] = Math.min(dp[i + len + 1], dp[i - len] + 1);
                } else {
                    break;
                }
            }

            // ..bbaa...
            for (int len = 1; i + len < s.length() && i - len + 1 >= 0; ++len) {
                if (s.charAt(i + len) == s.charAt(i - len + 1)) {
                    dp[i + len + 1] = Math.min(dp[i + len + 1], dp[i - len + 1] + 1);
                } else {
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}

class Solution {
    public int minCut(String s) {
        int dp[] = new int[s.length() + 1]; // dp[i] means minimum cut for s.substring(0, i)
        boolean[][] palindrome = new boolean[s.length() + 1][s.length() + 1];

        for (int i = 0; i < s.length(); ++i) {
            int min = i;
            for (int j = 0; j <= i; ++j) {
                if (s.charAt(i) == s.charAt(j) && ((j + 1 > i - 1) || palindrome[j + 1][i - 1])) {
                    palindrome[j][i] = true;
                    min = j == 0 ? 0 : Math.min(min, dp[j - 1] + 1);
                }
            }

            dp[i] = min;
        }

        return dp[s.length() - 1];
    }
}