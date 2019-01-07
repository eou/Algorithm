// 5. Longest Palindromic Substring
// hdu 3068: http://acm.hdu.edu.cn/showproblem.php?pid=3068 必须使用 Manacher's algorithm 才能过
class Solution {
    // 从某个字符往两边扩展，时间复杂度为 O(n^2)
    int len = 0;
    int start = 0;
    public String longestPalindrome(String s) {
        if(s.length() < 2) {
            return s;
        }
        
        for(int i = 0; i < s.length() - 1; i++) {
            isPalindrome(s, i, i);
            isPalindrome(s, i, i + 1);
        }
        
        return s.substring(start, start + len);
    }
    
    public void isPalindrome(String s, int left, int right) {
        while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        
        if(right - left - 1 > len) {
            len = right - left - 1;
            start = left + 1;
        }
    }
}

class Solution {
    public String longestPalindrome(String s) {
        String result = "";
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = s.length() - 1; i >= 0; i--) {
            for(int j = i; j < s.length(); j++) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i < 3 || dp[i + 1][j - 1]);
                if(dp[i][j] && (result.length() == 0 || j - i + 1 > result.length())) {
                    result = s.substring(i, j + 1);
                }
            }
        }
        
        return result;
    }
}

class Solution {
    // Manacher's algorithm，时间复杂度为 O(n)
    // 算法解释见 https://blog.csdn.net/xingyeyongheng/article/details/9310555
    // 算法时间复杂度分析见 https://www.zhihu.com/question/30226229
    // 简言之，即比较次数的多少是和 center + longest 回文串最右边位置 有关的，也就是说复杂度是和回文串最右边位置 从 0 位置移动到 n-1 的位置次数是线性相关的， max 从 0 移动到 n-1 位置最多为 O(n) 次，因此算法的时间复杂度是 O(n) 
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        // abc => #a#b#c#
        String str = generateString(s);

        int[] palindrome = new int[str.length()];
        int center = 0, longest = 1;
        palindrome[0] = 1;
        for (int i = 1; i < str.length(); i++) {
            int len = 1;

            // 核心部分
            if (center + longest > i) {
                int mirrorOfI = center - (i - center);
                len = Math.min(palindrome[mirrorOfI], center + longest - i);
            }

            // 普通匹配回文串
            while (i + len < str.length() && i - len >= 0) { // 保证不越界
                if (str.charAt(i - len) != str.charAt(i + len)) {
                    break;
                }
                len++;
            }

            if (len > longest) {
                longest = len;
                center = i;
            }

            palindrome[i] = len;
        }

        longest = longest - 1; // remove the extra #
        int start = (center - 1) / 2 - (longest - 1) / 2;
        return s.substring(start, start + longest);
    }

    private String generateString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append('#');
            sb.append(s.charAt(i));
        }
        sb.append('#');

        return sb.toString();
    }
}