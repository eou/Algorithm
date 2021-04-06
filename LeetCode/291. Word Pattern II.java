// 291. Word Pattern II
// DFS, O(N * C(M, N)), O(N) time to validate each possiblity
class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();
        return dfs(pattern, str, map, 0, 0);
    }
    
    public boolean dfs(String pattern, String str, Map<Character, String> map, int p, int s) {
        if (s == str.length() && p == pattern.length()) {
            return true;
        }
        
        // partial match
        if (s == str.length() || p == pattern.length()) {
            return false;
        }
        
        char c = pattern.charAt(p);
        if (map.containsKey(c)) {
            String matchStr = map.get(c);
            if (!str.substring(s).startsWith(matchStr)) {
                return false;
            } else {
                return dfs(pattern, str, map, p + 1, s + matchStr.length());
            }
        } else {
            // try all possibilities
            for (int i = s + 1; i <= str.length(); i++) {
                // this step can use Set get better performance
                if (!map.containsValue(str.substring(s, i))) {
                    map.put(c, str.substring(s, i));
                    if (dfs(pattern, str, map, p + 1, i)) {
                        return true;
                    }
                    map.remove(c);
                }
            }
            return false;
        }
    }
}