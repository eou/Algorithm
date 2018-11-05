// 159. Longest Substring with At Most Two Distinct Characters
// sliding window
class Solution {
    // 始终维护map.size() == 2
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        
        int len = 0;        
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0, flag = 0;
        while(right < s.length()) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);
            // 一旦 map.size() > 2 说明有超过2个的字母存在
            if(map.size() > 2) {
                flag++;
            }
            right++;
            
            while(flag > 0) {
                char t = s.charAt(left);
                map.put(t, map.get(t) - 1);
                // 一旦left移动到某个字符不存在，就删除map中这一项
                if(map.get(t) == 0) {
                    flag--;
                    map.remove(t);
                }
                left++;
            }
            len = Math.max(len, right - left);
        }
        
        return len;
    }
}

class Solution {
    // 还可以提高可读性，这个版本更好一些
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = 0;
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0, distinct = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);
            // 当刚添加的字符只有1个的时候，说明这是新字符
            if (map.get(c) == 1) {
                distinct++;
            }
            right++;

            // 添加的新字符大于2就要移动left删除一个
            while (distinct > 2) {
                char t = s.charAt(left);
                map.put(t, map.get(t) - 1);
                if (map.get(t) == 0) {
                    distinct--;
                }
                left++;
            }
            len = Math.max(len, right - left);
        }

        return len;
    }
}