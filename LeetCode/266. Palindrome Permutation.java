// 266. Palindrome Permutation
class Solution {
    // 时间复杂度为 O(n)，空间复杂度为 O(128) = O(1)
    public boolean canPermutePalindrome(String s) {
        int[] letter = new int[128];
        for(char c : s.toCharArray()) {
            letter[(int)(c)]++;
        }
        
        int odd = 0;
        for(int i : letter) {
            if(i % 2 == 1) {
                odd++;
            }
        }
        
        if(odd > 1) {
            return false;
        }
        
        return true;
    }
}

public class Solution {
    // 另一种形式
    public boolean canPermutePalindrome(String s) {
        int[] map = new int[128];
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            map[s.charAt(i)]++;
            if (map[s.charAt(i)] % 2 == 0) {
                count--;
            } else {
                count++;
            } 
        }
        return count <= 1;
    }
}

public class Solution {
    // 时间复杂度为 O(n)，空间复杂度为O(n)
    public boolean canPermutePalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (!set.add(s.charAt(i)))
                set.remove(s.charAt(i));
        }
        return set.size() <= 1;
    }
}
