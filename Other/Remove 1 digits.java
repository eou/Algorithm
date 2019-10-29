import java.util.*;

// Given 2 strings, remove only one digit to make 1 string lexicographically smaller, find how many possible ways
// a: "3abc", b: "bbc" => a remove 3
class Solution {
    public int solution(String a, String b) {
        if (a == "" && b == "") {
            return 0;
        }

        if (a == "") {
            // count b's digits
            int digits = 0;
            for (Character c : b.toCharArray()) {
                if (Character.isDigit(c)) {
                    digits++;
                }
            }
            return b.length() == 1 ? 0 : digits;    // only 1 digits, cannot remove otherwhise equal
        }

        if (b == "") {
            // a is not empty
            return 0;
        }

        if (a.charAt(0) == b.charAt(0)) {
            // substring might be out of bound
            if (a.length() > 1 && b.length() > 1) {
                int ways = 0;
                if (Character.isDigit(a.charAt(0))) {
                    // remove a's first character
                    if (a.substring(1).compareTo(b) < 0) {
                        ways++;
                    }
                }
                if (Character.isDigit(b.charAt(0))) {
                    // remove b's first character
                    if (a.compareTo(b.substring(1)) < 0) {
                        ways++;
                    }
                }
                return ways + solution(a.substring(1), b.substring(1));
            } else if (a.length() == 1) {
                return solution("", b.substring(1));
            } else {
                // b is empty, a is not, a is always larger than b
                return 0;
            }
            
        } else if (a.charAt(0) < b.charAt(0)) {
            // remove any digits from a and b
            int digits = 0;
            for (Character c : a.toCharArray()) {
                if (Character.isDigit(c)) {
                    digits++;
                }
            }
            for (Character c : b.toCharArray()) {
                if (Character.isDigit(c)) {
                    digits++;
                }
            }
            if (Character.isDigit(a.charAt(0))) {
                // remove a's first character
                if (a.substring(1).compareTo(b) >= 0) {
                    digits--;
                }
            }
            if (Character.isDigit(b.charAt(0))) {
                // remove b's first character
                if (a.compareTo(b.substring(1)) >= 0) {
                    digits--;
                }
            }
            return digits;
        } else {
            if (Character.isDigit(a.charAt(0))) {
                // remove a's first character
                if (a.substring(1).compareTo(b) < 0) {
                    return 1;
                }
            }
            if (Character.isDigit(b.charAt(0))) {
                // remove b's first character
                if (a.compareTo(b.substring(1)) < 0) {
                    return 1;
                }
            }
            // cannot remove any first characters
            return 0;
        }
    }

    public static void test(String a, String b, int result) {
        Solution s = new Solution();
        System.out.println(s.solution(a, b) == result);
    }

    public static void main(String[] args) {
        // System.out.println("a".compareTo("b")); // -1
        // System.out.println("b".compareTo("a")); // 1
        // System.out.println("b".compareTo("")); // 1
        // System.out.println("b".compareTo("b")); // 0
        // test("3abc", "bbc", 1);
        // test("123ab", "423cd", 6);
        // test("ab23", "a1xx", 1);
        // test("", "1234", 4);
        // test("a123", "", 0);
        // test("aaaa", "aaaa", 0);
        // test("aaa1", "aaaa", 1);
        // test("a", "a1", 0);
        // test("a1", "a", 0);
        // test("a", "1", 0);
        // test("1", "a", 1);
        test("9ab12dd", "9ab24ff", 3);
    }
}