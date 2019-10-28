import java.util.*;

/**
 * Given a list representing the length of ribbon, and the target number "k"
 * parts of ribbon. we want to cut ribbon into k parts with the same size, at
 * the same time we want the maximum size. 
 * Ex. Input: A = [1, 2, 3, 4, 9], k = 5
 * Output: 3
 */
class Solution {
    public int maxRibbon(int[] A, int k) {
        int left = 1, right = A.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (cut(A, mid) > k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public int cut(int[] A, int len) {
        int res = 0;
        for (int i : A) {
            res += i / len;
        }
        return res;
    }

    public static void main(String[] args) { 
        Solution s = new Solution();
        int[] a = new int[]{1, 2, 3, 4, 9};
        System.out.println(s.maxRibbon(a, 5));
    }
}
