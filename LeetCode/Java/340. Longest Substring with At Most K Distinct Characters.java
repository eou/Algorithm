// 340. Longest Substring with At Most K Distinct Characters
// sliding window
// 与159是一样的
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s.length() == 0 || k == 0) {
            return 0;
        }

        int left = 0;
        int right = 0;
        int len = 1;
        Map<Character, Integer> map = new HashMap<>();
        while (right < s.length()) {
            map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);
            if (map.size() > k) { // 这个 if-else 可以省略
                while (map.size() > k) {
                    map.put(s.charAt(left), map.get(s.charAt(left)) - 1);
                    if (map.get(s.charAt(left)) == 0) {
                        map.remove(s.charAt(left));
                    }
                    left++;

                }
            } else {
                len = Math.max(len, right - left + 1);
            }
            right++;
        }

        return len;
    }
}

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

class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0, len = 0;
        while (right < s.length()) {
            // add current char into map
            map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);
            right += 1;
            // remove 3rd (if exist) distinct char in map
            while (map.size() > k) {
                map.put(s.charAt(left), map.get(s.charAt(left)) - 1);
                if (map.get(s.charAt(left)) == 0) {
                    map.remove(s.charAt(left));
                }
                left += 1;
            }
            len = Math.max(len, right - left);
        }
        return len;
    }
}