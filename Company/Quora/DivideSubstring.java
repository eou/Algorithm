import java.util.*;

/**
 * Give a number n and digit number k find all serial substring whose length is
 * k is able to divisible n. Input: n = 120, k = 2 Output: 2 Explain: 120 -> 12
 * and 20 120 % 12 == 0 120 % 20 == 0
 */
class Solution {
    public int DivideSubString(String s, int k) {
        int res = 0;
        int total = Integer.parseInt(s);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < s.length() - k + 1; i++) {
            String tmp = s.substring(i, i + k);
            int num = Integer.parseInt(tmp);
            if (!set.contains(num) && num != 0) {
                if (total % num == 0) {
                    res++;
                }
            }
            set.add(num);
        }
        return res;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.DivideSubString("120", 2));
        System.out.println(s.DivideSubString("555", 1));
    }
}