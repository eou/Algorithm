// 340. Longest Substring with At Most K Distinct Characters
// sliding window
// 与159是一样的
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        
        int len = 0;        
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0, distinct = 0;
        while(right < s.length()) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);
            if(map.get(c) == 1) {
                distinct++;
            }
            right++;
            
            while(distinct > k) {
                char t = s.charAt(left);
                map.put(t, map.get(t) - 1);
                if(map.get(t) == 0) {
                    distinct--;
                }
                left++;
            }
            len = Math.max(len, right - left);
        }
        
        return len;
    }
}
