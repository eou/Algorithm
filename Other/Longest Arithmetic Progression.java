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

import java.util.*;

public class Solution {
    public static void main(String args[]) {
        Solution s = new Solution();
        int num[] = { 1, 7, 15, 29, 27, 10 };
        System.out.println(s.longestArithmeticProgression(num));
    }

    public int longestArithmeticProgression(int num[]) {
        int llap = 0;
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < num.length; i++) {
            set.add(num[i]);
            map.put(num[i], map.getOrDefault(num[i], 0) + 1);
            llap = Math.max(llap, map.get(num[i]));
        }

        Integer[] nums = set.toArray(new Integer[set.size()]);
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - llap + 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int gap = nums[j] - nums[i];
                if (gap != 0) {
                    int cur = nums[i];
                    while (map.containsKey(cur + gap)) {
                        cur += gap;
                    }
                    llap = Math.max(llap, (cur - nums[i]) / gap + 1);
                }
            }

            // optimization
            if (llap > nums.length / 2) {
                break;
            }
        }

        return llap;
    }

    public int longestArithmeticProgression(int num[]) {
        int n = num.length;
        if(n <= 2) {
            return n;
        }

        // The value ofL[i][j] stores LLAP with set[i] and set[j] as first two elements of AP
        // Only valid entries are the entries where j > i
        int dp[][] = new int[n][n];

        int llap = 2;

        // Fill entries in last column as 2 because the last element is the second element of AP
        for(int i = 0; i < n; i++) {
            dp[i][n - 1] = 2;
        }

        // Consider every element as second element of AP
        for(int j = n - 2; j >= 1; j--) {
            int i = j - 1, k = j + 1;

            while(i >= 0 && k <= n - 1) {
                if (num[i] + num[k] < 2 * num[j]) {
                    k++;
                } else if (num[i] + num[k] > 2 * num[j]) {
                    // Before changing i, set L[i][j] as 2
                    dp[i][j] = 2;
                    i--;
                } else {
                    // Found i and k for j, LLAP with i and j as first two elements is equal to LLAP with j and k
                    // L[j][k] must have been filled before as we run the loop from right side
                    dp[i][j] = dp[j][k] + 1;

                    // Update overall LLAP, if needed
                    llap = Math.max(llap, dp[i][j]);

                    // Change i and k to fill more L[i][j] values for current j
                    i--;
                    k++;
                }
            }

            // If the loop was stopped due to k becoming more than n-1
            while(i >= 0) {
                dp[i][j] = 2;
                i--;
            }
        }

        return llap;
    }
}