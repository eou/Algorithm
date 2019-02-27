import java.io.*;
import java.util.*;

// Count number of substrings with exactly k distinct characters
class countNumberOfSubstrings {
    // brute force is O(n^3)
    // sliding window ?
    public int solution1(String input, int k) {
        int result = 0;
        int cnt[] = new int[26];
        for (int i = 0; i < input.length(); i++) {
            int distinct = 0;
            Arrays.fill(cnt, 0);

            for (int j = i; j < input.length(); j++) {
                if (cnt[input.charAt(j) - 'a'] == 0) {
                    distinct++;
                }
                cnt[input.charAt(j) - 'a']++;
                if (distinct == k) {
                    result++;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        countNumberOfSubstrings s = new countNumberOfSubstrings();

        System.out.println(s.solution1("abc", 2));
        System.out.println(s.solution1("aba", 2));
        System.out.println(s.solution1("aa", 1));
    }
}