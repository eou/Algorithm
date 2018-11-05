// 3. Longest Substring Without Repeating Characters
// sliding window
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = 0;
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0, repeat = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);
            if (map.get(c) > 1) {
                repeat++;
            }
            right++;

            // 一旦出现重复元素，repeat == 1, 就会进入循环中不断丢弃left直到repeat == 0
            while (repeat > 0) {
                char t = s.charAt(left);
                // 注意如何判断此字符是重复的
                // if (map.get(t) > 1) {
                //     repeat--;
                // }
                // map.put(t, map.get(t) - 1);
                map.put(t, map.get(t) - 1);
                if (map.get(t) == 1) {
                    repeat--;
                }
                left++;
            }
            len = Math.max(len, right - left);
        }

        return len;
    }
}