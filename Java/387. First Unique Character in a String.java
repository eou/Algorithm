// 387. First Unique Character in a String
class Solution {
    // 时间复杂度 O(n)，空间复杂度 O(1)
    public int firstUniqChar(String s) {
        // This includes standard ASCII characters(0-127) and Extended ASCII characters(128-255).
        int[] freq = new int[256];
        for(char c : s.toCharArray()){
            freq[c - 'a']++;
        }
        for(int i = 0; i < s.length(); i++){
            if(freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}

class Solution {
    // 时间复杂度 O(n)，空间复杂度 O(n)
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                map.remove(s.charAt(i));
            } else {
                map.put(s.charAt(i), i);
                set.add(s.charAt(i));
            }
        }
        return map.size() == 0 ? -1 : map.entrySet().iterator().next().getValue();
    }
}

class Solution {
    // 暴力解法，时间复杂度 O(n^2)，无额外空间
    // 由于是返回下标，所以没法排序（O(nlogn)）后返回（下标会变化），如果是返回single character，可以排序
    public int firstUniqChar(String s) {
        for (int i = 0; i < s.length(); i++) {
            boolean hasDuplicate = false;
            for (int j = 0; j < s.length(); j++) {
                if (j == i) {
                    continue;
                }
                if (s.charAt(i) == s.charAt(j)) {
                    hasDuplicate = true;
                    break;
                }
            }
            if (hasDuplicate == false) {
                return i;
            }
        }

        return -1;
    }
}
