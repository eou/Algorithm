// 639. Decode Ways II
// 跟91一个写法，只是多了不少细节判断
class Solution {
    // 注意取余必须在每一步加法都取余，而不是最后再取余
    int M = 1000000007;

    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 用 long 整数类型
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        if (s.charAt(0) != '0') {
            if (s.charAt(0) != '*') {
                dp[1] = 1;
            } else {
                dp[1] = 9;
            }
        }
        for (int i = 2; i <= s.length(); i++) {
            char first = s.charAt(i - 2);
            char second = s.charAt(i - 1);
            if (second != '0') {
                if (second != '*') {
                    dp[i] = (dp[i - 1] + dp[i]) % M;
                } else {
                    dp[i] = (dp[i - 1] * 9 + dp[i]) % M;
                }
            }
            
            if (first != '*' && second != '*') {
                int tmp = Integer.parseInt(s.substring(i - 2, i));
                if (first != '0') {
                    if (tmp >= 1 && tmp <= 26) {
                        dp[i] = (dp[i - 2] + dp[i]) % M;
                    }
                }
            } else if (first == '*' && second != '*') {
                int tmp = Integer.parseInt(s.substring(i - 1, i));
                if (tmp >= 0 && tmp <= 6) {
                    dp[i] = (dp[i - 2] * 2 + dp[i]) % M;
                } else {
                    dp[i] = (dp[i - 2] + dp[i]) % M;
                }

            } else if (first != '*' && second == '*') {
                int tmp = Integer.parseInt(s.substring(i - 2, i - 1));
                if (tmp == 1) {
                    dp[i] = (dp[i - 2] * 9 + dp[i]) % M;
                } else if (tmp == 2) {
                    dp[i] = (dp[i - 2] * 6 + dp[i]) % M;
                }
            } else {
                dp[i] = (dp[i - 2] * 15 + dp[i]) % M;
            }
        }

        return (int) (dp[s.length()]);
    }
}