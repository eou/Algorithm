// https://leetcode.com/discuss/interview-question/373202
import java.util.*;

class Solution {
    private static List<int[]> getPairs(List<int[]> a, List<int[]> b, int target) {
        Collections.sort(a, (i, j) -> i[1] - j[1]);
        Collections.sort(b, (i, j) -> i[1] - j[1]);

        List<int[]> res = new ArrayList<>();

        int max = Integer.MIN_VALUE;    // -1 might be a suitable init val
        int m = a.size();
        int n = b.size();
        int left = 0;
        int right = n - 1;
        // two pointer
        while (left < m && right >= 0) {
            int sum = a.get(left)[1] + b.get(right)[1];
            if (sum > target) {
                --right;
            } else {
                if (max <= sum) {
                    // find more closer sum
                    if (max < sum) {
                        max = sum;
                        res.clear();
                    }
                    res.add(new int[] { a.get(left)[0], b.get(right)[0] });
                    // add more duplicate elements
                    int index = right - 1;
                    while (index >= 0 && b.get(index)[1] == b.get(index + 1)[1]) {
                        res.add(new int[] { a.get(left)[0], b.get(index--)[0] });
                    }
                }
                ++left;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<int[]> A = new ArrayList<>();
        A.add(new int[] { 1, 2 });
        A.add(new int[] { 2, 2 });
        A.add(new int[] { 3, 8 });
        // A.add(new int[] { 1, 2 });
        // A.add(new int[] { 2, 2 });
        // A.add(new int[] { 3, 2 });
        List<int[]> B = new ArrayList<>();
        // B.add(new int[] { 1, 8 });
        // B.add(new int[] { 2, 8 });
        // B.add(new int[] { 3, 8 });
        B.add(new int[] { 1, 4 });
        B.add(new int[] { 2, 2 });
        B.add(new int[] { 3, 8 });
        for (int[] i : getPairs(A, B, 10)) {
            System.out.println(i[0] + ", " + i[1]);
        }
    }
}