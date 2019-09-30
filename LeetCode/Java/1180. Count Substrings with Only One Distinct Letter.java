// 1180. Count Substrings with Only One Distinct Letter
class Solution {
    public int countLetters(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                res += (checkValidity(s.substring(i, j)) ? 1 : 0);
            }
        }
        
        return res;
    }
    
    public boolean checkValidity(String s) {
        char c1 = s.charAt(0);
        for (Character c : s.toCharArray()) {
            if (c1 != c) {
                return false;
            }
        }
        return true;
    }
}

class Solution {
    public int countLetters(String s) {
        // dp[i] means number of substrings with only one distinct letter ending with
        // s[i]
        int[] dp = new int[s.length()];
        dp[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }
        }

        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += dp[i];
        }
        return res;
    }
}