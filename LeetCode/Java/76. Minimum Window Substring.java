// 76. Minimum Window Substring
// sliding window 典型题目，时间复杂度为 O(n)，虽然有两个循环嵌套，但是每个字符最多遍历两次
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

class Solution {
    public String minWindow(String s, String t) {
        if (t == null || t.length() == 0 || s == null || s.length() == 0) {
            return "";
        }

        String res = "";
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // move end pointer every loop
        // only move start pointer when any start chars are unneccessary
        int start = 0, end = 0;
        int len = 0;
        while (end < s.length()) {
            // move end pointer
            char c_end = s.charAt(end);
            if (map.containsKey(c_end)) {
                map.put(c_end, map.get(c_end) - 1);
                // only when chars are neccessary, we count it as effective
                if (map.get(c_end) >= 0) {
                    len++;
                }
            }
            end++;

            // move start pointer, to narrow down the length
            while (start < end) {
                char c_start = s.charAt(start);
                if (!map.containsKey(c_start)) {
                    start++;
                } else {
                    // have redundant chars
                    if (map.get(c_start) < 0) {
                        map.put(c_start, map.get(c_start) + 1);
                        start++;
                    } else {
                        // meet some neccessary chars, not skip
                        break;
                    }
                }
            }

            // update substring
            if (len >= t.length()) {
                if (res == "" || res.length() > end - start) {
                    res = s.substring(start, end);
                }
            }
            // System.out.println(start);
            // System.out.println(end);
        }

        return res;
    }
}