// 409. Longest Palindrome
class Solution {
    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        int len = 0;
        boolean odd = false;
        for(Integer i : map.values()) {
            if(i % 2 == 0) {
                len += i;
            } else {
                odd = true;
                len += (i - 1);
            }
        }
        
        if(odd) {
            len += 1;
        }
        return len;
    }
}

class Solution {
    public int longestPalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) {
                // find a pair
                set.remove(c);
            } else {
                set.add(c);
            }
        }

        int odd = set.size();
        return s.length() - (odd == 0 ? 0 : odd - 1);
    }
}