// 44. Wildcard Matching
class Solution {
    // 时间复杂度O(S*P)
    // 最坏时间复杂度测试样例：input: "bbbbbbbbbbbb" pattern: "*bbbb"
    public boolean isMatch(String s, String p) {
        int i = 0, j = 0, starIndex = -1, starMatch = 0;
        while (i < s.length()) {
            if (j < p.length() && (p.charAt(j) == '?' || s.charAt(i) == p.charAt(j))) {
                i++;
                j++;
            // 如果模式串中遇到了*，则记录下此时模式串和匹配串的指针位置，以便以后使用，也许用得上，也许用不上
            } else if (j < p.length() && p.charAt(j) == '*') {
                starIndex = j;
                starMatch = i;
                j++;
            // 此时正常无法匹配，但如果之前模式串出现过*，就从*的位置开始匹配
            // 注意starMatch会一直往后变化，但是starIndex在遇到新的*之前是不会变化的，模式串的j一直处在*的后一位
            } else if (starIndex != -1) {
                j = starIndex + 1;
                i = starMatch + 1;
                starMatch++;
            } else {
                return false;
            }
        }

        // 如果匹配串已经匹配结束，但是模式串仍有多余字符
        while (j < p.length() && p.charAt(j) == '*') {
            j++;
        }

        return j == p.length();
    }
}

class Solution {
    // DP版本，使用二维DP数组，类似10. Regular Expression Matching，但更简单
    // Bottom-up 方式不需要递归
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[s.length()][p.length()] = true;

        for (int i = p.length() - 1; i >= 0; i--) {
            if (p.charAt(i) != '*') {
                break;
            } else {
                dp[s.length()][i] = true;
            }
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                    dp[i][j] = dp[i + 1][j + 1];
                } else if (p.charAt(j) == '*') {
                    dp[i][j] = (dp[i + 1][j] || dp[i][j + 1]);
                }
                // else 不用处理，因为数组默认值是false
            }
        }

        return dp[0][0];
    }
}