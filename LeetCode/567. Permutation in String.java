// 567. Permutation in String
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        // Map<Character, Integer> map = new HashMap<>();
        // for (char c : s1.toCharArray()) {
        //     map.put(c, map.getOrDefault(c, 0) + 1);
        // }
        // use array with 26 positions is enought
        int[] letter = new int[26];
        for (char c : s1.toCharArray()) {
            letter[c - 'a']++;
        }
        for (int i = 0; i < s2.length(); i++) {
            if (letter[s2.charAt(i) - 'a'] != 0) {
                if (checkString(letter, s1, s2, i)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean checkString(int[] letter, String s1, String s2, int start) {
        int[] curLetter = Arrays.copyOf(letter, letter.length);
        for (int i = start; i < s2.length() && i < start + s1.length(); i++) {
            if (curLetter[s2.charAt(i) - 'a'] > 0) {
                curLetter[s2.charAt(i) - 'a']--;
            } else {
                return false;
            }
        }
        for (int i = 0; i < 26; i++) {
            if (curLetter[i] > 0) {
                return false;
            }
        }
        return true;
    }
}

// sliding window
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }
        
        int[] letter = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            letter[s1.charAt(i) - 'a']++;
            letter[s2.charAt(i) - 'a']--;
        }
        if (allZero(letter)) {
            return true;
        }
        
        for (int i = s1.length(); i < s2.length(); i++) {
            letter[s2.charAt(i) - 'a']--;
            letter[s2.charAt(i - s1.length()) - 'a']++;
                if (allZero(letter)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean allZero(int[] letter) {
        for (int i : letter) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}