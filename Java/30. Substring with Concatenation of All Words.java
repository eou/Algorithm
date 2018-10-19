// 30. Substring with Concatenation of All Words
// sliding window
// 不同之处在于是按照单词长度移动窗口，而不是一个字符一个字符移动
// 比较良心的是单词长度都相同
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> results = new ArrayList<>();
        if(words.length == 0 || words[0].length() == 0 || s.length() < words[0].length()) {
            return results;
        }
        int len = words[0].length();
        if(s.length() < len * words.length) {
            return results;
        }
        
        Map<String, Integer> map = new HashMap<>();
        for(String t : words) {
            map.put(t, map.getOrDefault(t, 0) + 1);
        }
        
        Map<String, Integer> tmp = new HashMap<>();
        // 起始点的移动只需要从 0 ~ len - 1
        for(int start = 0; start < len; start++) {
            // 注意不能用 tmp = map，是shallow copy
            tmp.putAll(map);
            int left = start, right = start, cnt = tmp.size();
            while(right <= s.length() - len) {
                String p = s.substring(right, right + len);
                if(tmp.containsKey(p)) {
                    tmp.put(p, tmp.get(p) - 1);
                    if(tmp.get(p) == 0) {
                        cnt--;
                    }
                }
                right += len;

                while(cnt == 0) {
                    if(right - left == words.length * len) {
                        results.add(left);
                    }
                    p = s.substring(left, left + len);
                    if(tmp.containsKey(p)) {
                        tmp.put(p, tmp.get(p) + 1);
                        if(tmp.get(p) > 0) {
                            cnt++;
                        }
                    }
                    left += len;
                }
            }
            tmp.clear();
        }
        
        return results;
    }
}