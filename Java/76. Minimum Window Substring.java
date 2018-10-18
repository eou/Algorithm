// 76. Minimum Window Substring
// sliding window 典型题目
class Solution {
    public String minWindow(String s, String t) {
        if (s == null || s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> map = new HashMap<>();
        for (Character c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        String r = "";
        int left = 0, right = 0, cnt = map.size();
        while (right < s.length()) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    cnt--;
                }
            }
            right++;

            while (cnt == 0) {
                // 这里条件因题不同
                if (r == "") {
                    r = s.substring(left, right);
                } else {
                    r = (right - left < r.length()) ? s.substring(left, right) : r;
                }
                
                c = s.charAt(left);
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                    if (map.get(c) > 0) {
                        cnt++;
                    }
                }
                left++;
            }
        }

        return r;
    }
}