// 139. Word Break
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        Set<String> set = new HashSet<>();
        set.addAll(wordDict);

        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                dp[i] = dp[j] && set.contains(s.substring(j, i));
                if(dp[i]) break;
            }
        }
        return dp[s.length()];
    }
}

class Solution {
    public int wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return false;
        }

        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            dp[i] = 0;
            for (int j = i; j > 0; j--) {
                if (dp[i - j] == 0) {
                    continue;
                }
                String word = s.substring(i - j, i);
                if (wordDict.contains(word)) {
                    dp[i] = dp[i - j] + 1;
                    break;
                }

            }
        }
        
        return Math.max(0, dp[s.length()] - 2);
    }
}