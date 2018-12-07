// 5. Longest Palindromic Substring
class Solution {
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