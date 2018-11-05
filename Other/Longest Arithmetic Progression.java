/**
 * - Longest Arithmetic Progression
 * https://www.geeksforgeeks.org/longest-arithmetic-progression-dp-35/
 * 
 * Given an array of sorted numbers having no duplicates , write a program to
 * find the length of the Longest Arithmetic Progression (LLAP) in it.
 * 
 * Example:
 * 
 * set[] = {7, 1, 15, 10, 27, 29} 
 * output = 3 
 * The longest arithmetic progression is {1, 15, 29}
 * 
 * set[] = {5, 10, 15, 20, 25, 30} 
 * output = 6 
 * The whole set is in AP
 * 
 */

// 暴力解法时间复杂度为 O(n^3)，排序后任取两个数字作为起始，扫描后面的数字有多少在数列中
// 可以用 HashMap 把时间复杂度降为 O(n^2)，用 map 保存数组，去重后任取两个数字作为起始，用 map 判断是否有后续数字在数列中，然后单独找出去重前相同数字的最大重复次数进行比较
// 用 dp 也可以达到时间复杂度 O(n^2)，

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Solution {
    public static void main(String args[]) {
        Solution s = new Solution();
        int num[] = { 1, 7, 15, 29, 27, 10 };
        System.out.println(s.longestArithmeticSeq(num));
    }

    public int longestArithmeticSeq(int num[]) {
        int longest = 0;
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < num.length; i++) {
            set.add(num[i]);
            map.put(num[i], map.getOrDefault(num[i], 0) + 1);
            longest = Math.max(longest, map.get(num[i]));
        }

        Integer[] nums = set.toArray(new Integer[set.size()]);
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - longest + 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int gap = nums[j] - nums[i];
                if (gap != 0) {
                    int cur = nums[i];
                    while (map.containsKey(cur + gap)) {
                        cur += gap;
                    }
                    longest = Math.max(longest, (cur - nums[i]) / gap + 1);
                }
            }

            if (longest > nums.length / 2) {
                break;
            }
        }

        return longest;
    }

    public int longestArithmeticSeq(int num[]) {
        int longest = 0;
        // the length of arithmetic sequence by setting num[i] and num[j] as first two elements
        int dp[][] = new int[num.length][num.length];
        Arrays.sort(num);

        // base case
        for (int i = 0; i < num.length - 1; i++) {
            for (int j = i; j < num.length; j++) {
                dp[i][j] = 2;
            }
        }
        
        for (int j = num.length; j >= 0; j--) {
            int i = j - 1, k = j + 1;
            while (i >= 0 && k < num.length) {
                if (num[i] + num[k] < 2 * num[j]) {
                    k++;
                } else if (num[i] + num[k] > 2 * num[j]) {
                    i--;
                } else {
                    dp[i][j] = dp[j][k] + 1;
                    longest = Math.max(longest, dp[i][j]);
                    i--;
                    k++;
                }
            }
        }

        return longest;
    }
}