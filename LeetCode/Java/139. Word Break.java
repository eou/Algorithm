// 139. Word Break
class Solution {
    // 直接 DFS 会 TLE
    public boolean wordBreak(String s, List<String> wordDict) {
        for (int i = 0; i <= s.length(); i++) {
            if (wordDict.contains(s.substring(0, i)) && (i == s.length() || wordBreak(s.substring(i), wordDict))) {
                return true;
            }
        }

        return false;
    }
}

class Solution {
    // DFS with memoization，不需要二维数组，时间复杂度为 O(n^2)
    int[] memo;
    public boolean wordBreak(String s, List<String> wordDict) {
        memo = new int[s.length() + 1];

        helper(s, 0, s.length(), wordDict);
        return memo[0] == 1;
    }

    private int helper(String s, int start, int end, List<String> wordDict) {
        if (memo[start] != 0) {
            return memo[start];
        }

        for (int i = start; i <= end; i++) {
            if (wordDict.contains(s.substring(start, i)) && (i == end || helper(s, i, end, wordDict) == 1)) {
                memo[start] = 1;
                return 1;
            }
        }

        memo[start] = -1;
        return -1;
    }
}

class Solution {
    // DFS with memoization
    public boolean wordBreak(String s, List<String> wordDict) {
        return word_Break(s, new HashSet(wordDict), 0, new Boolean[s.length()]);
    }

    // 用 Boolean 可以保证初始化的时候是 null
    public boolean word_Break(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (start == s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && word_Break(s, wordDict, end, memo)) {
                return memo[start] = true;
            }
        }
        return memo[start] = false;
    }
}

class Solution {
    // DP，时间复杂度为 O(n^2)
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        Set<String> set = new HashSet<>();
        set.addAll(wordDict);

        dp[0] = true;
        // dp[end] = dp[start] && wordDict.contains(s.substring(start, end))
        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                dp[i] = dp[j] && set.contains(s.substring(j, i));
                if(dp[i]) {
                    break;
                }
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

class Solution {
    // BFS，时间复杂度为 O(n^2)
    public boolean wordBreak(String s, List<String> wordDict) {
        Deque<Integer> queue = new ArrayDeque<>();
        int[] visited = new int[s.length()];

        queue.add(0);
        while (!queue.isEmpty()) {
            int start = queue.remove();
            if (visited[start] == 0) {
                for (int i = start + 1; i <= s.length(); i++) {
                    if (wordDict.contains(s.substring(start, i))) {
                        queue.add(i);
                        if (i == s.length()) {
                            return true;
                        }
                    }
                }
                visited[start] = 1;
            }
        }
        return false;
    }
}