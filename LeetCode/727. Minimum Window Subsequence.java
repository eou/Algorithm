// 727. Minimum Window Subsequence
// The length of S will be in the range [1, 20000].
// The length of T will be in the range [1, 100].
// DP, O(n^2)
class Solution {
    private static int MAX = 20200;
    public String minWindow(String S, String T) {
        int ns = S.length(), nt = T.length();

        // minimum W length for S(1..i) and T(1..j)
        int[][] dp = new int[ns + 1][nt + 1];

        // S(0..0), impossible
        for (int j = 1; j <= nt; j++) {
            // impossible signal and avoid overflow
            dp[0][j] = MAX;
        }

        for (int i = 1; i <= ns; i++) {
            for (int j = 1; j <= nt; j++) {
                if (S.charAt(i - 1) != T.charAt(j - 1)) {
                    // W can only include T(1..j - 1)
                    dp[i][j] = dp[i - 1][j] + 1;
                } else {
                    // S(1..i-1) may match T(j-1) or not
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + 1;
                }
            }
        }

        int minlen = MAX, res = -1;
        // search all possiblities which include the whole T
        for (int i = 1; i <= ns; i++) {
            if (dp[i][nt] < minlen) {
                minlen = dp[i][nt]; 
                res = i;
            }
        }
        
        if (minlen >= MAX) {
            return "";
        }

        return S.substring(res - minlen, res);
    }
}

// Two pointer
class Solution {
    public String minWindow(String S, String T) {
        char[] s = S.toCharArray(), t = T.toCharArray();
        
        // move pointer, length, start/end pointer, minimum length of W
        int is = 0, it = 0, ns = s.length, nt = t.length, start = -1, end = -1, minlen = ns;

        while (is < ns) {
            if (s[is] == t[it]) {
                // ready to compare next char in T
                it++;
                if (it == nt) {
                    // start update res
                    int end = is + 1;
                    
                    // it pointer will backward from ending point to the starting point
                    it--;
                    while (it >= 0) {
                        while (s[is] != t[it]) {
                            is--;
                        }
                        it--;
                    }

                    is++;  // start
                    it++;  // 0

                    // compare res, update res
                    if (end - is < minlen) {
                        minlen = end - is;
                        start = is;
                    }
                }
            }
            is++;
        }

        return start == -1 ? "" : S.substring(start, start + minlen);
    }
}