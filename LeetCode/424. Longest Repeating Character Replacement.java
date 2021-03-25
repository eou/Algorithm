// 424. Longest Repeating Character Replacement
// Sliding window
class Solution {
    public int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int[] alpha = new int[26];
        int left = 0, right = 0, res = 0, cnt = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            alpha[c - 'A']++;
            // We might encounter various chars but we only need to record the highest frenquency one
            cnt = Math.max(cnt, alpha[c - 'A']);
            // if we can't keep all chars same in the sliding window, we need to move left pointer
            while (cnt + k < right - left + 1) {
                alpha[s.charAt(left) - 'A']--;
                left++;
            }
            res = Math.max(res, right - left + 1);
            right++;
        }
        
        return res;
    }
}