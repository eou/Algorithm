// 387. First Unique Character in a String
// space O(1), time O(n)
class Solution {
    public int firstUniqChar(String s) {
        int res = s.length();
        for (char c = 'a'; c <= 'z'; c++) {
            int i = s.indexOf(c);
            if (i != -1 && i == s.lastIndexOf(c)) {
                res = Math.min(res, i);
            }
        }
        return res == s.length() ? -1 : res;
    }
}

class Solution {
    // time O(n), space O(1)
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
    // brute force time O(n^2)，space O(1)
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
