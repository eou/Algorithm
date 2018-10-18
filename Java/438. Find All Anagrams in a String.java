// 438. Find All Anagrams in a String
// 类似字符串匹配，但是匹配的是anagram同型异构体，不需要考虑每个字符顺序
class Solution {
    // 暴力匹配，O(m*n)
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> results = new ArrayList<>();
        if (s == null || s.length() < p.length()) {
            return results;
        }

        int[] chars = new int[26];
        for (Character c : p.toCharArray()) {
            chars[c - 'a']++;
        }

        int[] tmp = new int[26];
        boolean success = true;
        for (int start = 0; start <= s.length() - p.length(); start++) {
            success = true;
            System.arraycopy(chars, 0, tmp, 0, chars.length);
            for (int i = start; i < start + p.length(); i++) {
                if (tmp[s.charAt(i) - 'a'] > 0) {
                    tmp[s.charAt(i) - 'a']--;
                } else {
                    success = false;
                    break;
                }
            }
            if (success == true) {
                results.add(start);
            }
        }

        return results;
    }
}

class Solution {
    // sliding window 初始版本，先初始化两个数组然后一前一后一增一减，O(n)
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> results = new ArrayList<>();
        if (s == null || s.length() < p.length()) {
            return results;
        }

        // 初始化两个数组
        int[] chars = new int[26];
        int[] charp = new int[26];
        for (int i = 0; i < p.length(); i++) {
            chars[s.charAt(i) - 'a']++;
            charp[p.charAt(i) - 'a']++;
        }
        if (Arrays.equals(chars, charp)) {
            results.add(0);
        }

        for (int i = p.length(); i < s.length(); i++) {
            chars[s.charAt(i - p.length()) - 'a']--;
            chars[s.charAt(i) - 'a']++;
            if (Arrays.equals(chars, charp)) {
                results.add(i - p.length() + 1);
            }
        }

        return results;
    }
}

class Solution {
    // sliding window的普适版本
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> results = new ArrayList<>();
        if (s == null || s.length() < p.length()) {
            return results;
        }

        int[] chars = new int[26];
        for (Character c : p.toCharArray()) {
            chars[c - 'a']++;
        }

        // 在移动window的时候不断更新diff，当diff为0说明找到anagram
        int left = 0, right = 0, diff = p.length();
        while (right < s.length()) {
            // 说明这个字母在p中
            if (chars[s.charAt(right) - 'a'] > 0) {
                diff--;
            }
            chars[s.charAt(right) - 'a']--;
            right++;

            if (diff == 0) {
                results.add(left);
            }

            // 每一个长度等于p的substring都要移动一下左指针
            if (right - left == p.length()) {
                if (chars[s.charAt(left) - 'a'] >= 0) {
                    diff++;
                }
                chars[s.charAt(left) - 'a']++;
                left++;
            }
        }

        return results;
    }
}