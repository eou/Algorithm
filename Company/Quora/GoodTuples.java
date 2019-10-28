import java.util.*;

/**
 * Give an array and find the count of a pair number and a single number
 * combination in a row of this array. 
 * Target array is a[i - 1], a, a[i + 1]
 */
class Solution {
    public int GoodTuples(int[] A) {
        int res = 0;
        for (int i = 0; i <= A.length - 3; i++) {
            if (helper(A, i)) {
                res++;
            }
        }

        return res;
    }

    public boolean helper(int[] A, int start) {
        Set<Integer> set = new HashSet<>();
        for (int i = start; i < start + 3; i++) {
            set.add(A[i]);
        }
        return set.size() == 2;
    }

    public static int check(int a, int b, int c) {
        if (a == b && a != c) {
            return 1;
        } else if (a == c && a != b) {
            return 1;
        } else if (b == c && a != b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) { 
        Solution s = new Solution();
        int[] A = new int[]{1, 1, 2, 1, 5, 3, 2, 3};
        System.out.println(s.GoodTuples(A));
    }
}
