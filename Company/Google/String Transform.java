import java.util.*;

// 1. leetcode 295 isomorphic string
// 2. lintcode 1580 transition string
// 3. transform string a to string b using any string b's characters
// 4. google interview: transform string using any 26 letters
// use any letter (26) to transfrom String a to String b, one time change all one type of letters
// aacc => bbdd, aacc => bbcc => bbdd
// abc => bca, must get the help of letters which are not in the input string, abc => bbc => ccc?, abc => ebc => eba => eca => bca
// thus if all 26 distinct chars are in the 2 input string respectively, no possible ways only if abc...z => abc..z (no need to change)
class Solution {
    public boolean solution(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            Character ca = a.charAt(i);
            Character cb = b.charAt(i);
            map.putIfAbsent(ca, cb);
            if (map.get(ca) != cb) {
                return false;
            }
        }

        // find cycle, if map.keySet() and map.values() are all 26 distinct characters, and exist map.get(i) != i, which means cycle exist
        int diff = 0;
        Set<Character> set = new HashSet<>();
        for (Character c : map.keySet()) {
            set.add(map.get(c));
            // if values() contains 26 distinct characters which means keySet() must contain 26 distinct characters
            // abcd...z => abcd..z, abcd...z => bbcd...z(only 25), abcd...z => bacd..z
            // has no redundant letter to do the 'bridge' transition
            if (map.get(c) != c) {
                diff++;
            }
        }

        if (diff > 0 && set.size() == 26) {
            return false;
        }

        return true;
    }

    public static void canTransform(String a, String b, boolean result) {
        Solution s = new Solution();
        System.out.println(s.solution(a, b) == result);
    }

    public static void main(String[] args) {
        canTransform("abcdefghijklmnopqrstuvwxyz","abcdefghijklmnopqrstuvwxya", true); // z => a, true
        canTransform("abcdefghijklmnopqrstuvwxya","abcdefghijklmnopqrstuvwxyz", false); // a => a / z, false
        canTransform("aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz","bbaaccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz", false);// false
        canTransform("abba","baab", true); // true
        canTransform("abaca", "ebece", true); // true
        canTransform("abba","bbbb", true); // true
        canTransform("abc","cba", true); // true
        canTransform("aac","abc", false); // false
        canTransform("abc", "bca", true); // true
        canTransform("edabc", "dcbca", true); // true
    }
}
