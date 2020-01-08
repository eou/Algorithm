// Given an integer array nums, find number of distinct contiguous subarrays with at most k odd elements. Two subarrays are distinct when they have at least one different element.
// https://leetcode.com/discuss/interview-question/278341/Uber-phone-interview-Number-of-distinct-subarrays-with-at-most-k-odd-elements/265140
import java.util.*;

class Solution {
    public static int evenSubarray(int[] nums, int k) {
        int left = 0, odd = 0, result = 0;
        for (int i = 0; i < nums.length; i++) {
            int right = i;
            if (nums[i] % 2 == 1) {
                odd++;
            }

            // need to remove at least 1 odd
            if (odd > k) {
                while (nums[left] % 2 == 0) {
                    left++;
                }
                left++;
                odd--;
            }

            result += (right - left + 1);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4 };
        System.out.println(evenSubarray(nums, 1));
    }
}