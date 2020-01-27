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
}