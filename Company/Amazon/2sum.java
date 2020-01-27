// https://leetcode.com/discuss/interview-question/356960
import java.util.*;

class Solution {
    private static int[] Find2Sum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[] {};
        }

        Map<Integer, Integer> map = new HashMap<>();
        int maxNum = Integer.MIN_VALUE;
        int[] res = new int[] { -1, -1 };
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                // should return pair with largest number
                if (nums[i] > maxNum || nums[map.get(nums[i])] > maxNum) {
                    res[0] = map.get(nums[i]);
                    res[1] = i;
                    maxNum = Math.max(nums[i], nums[map.get(nums[i])]);
                }
            }
            map.put(target - nums[i], i);
        }
        return res;
    }
    
    public static void main(String[] args) {
        int[] nums1 = { 1, 10, 25, 35, 60 };
        int target1 = 90;
        System.out.println(Arrays.toString(Find2Sum(nums1, target1 - 30)));
        int[] nums2 = { 20, 50, 40, 25, 30, 10 };
        int target2 = 90;
        System.out.println(Arrays.toString(Find2Sum(nums2, target2 - 30)));
        int[] nums3 = { 50, 20, 10, 40, 25, 30 };
        int target3 = 90;
        System.out.println(Arrays.toString(Find2Sum(nums3, target3 - 30)));
        int[] nums4 = { 1, 2 };
        int target4 = 90;
        System.out.println(Arrays.toString(Find2Sum(nums4, target4 - 30)));
        int[] nums5 = { 0, 0 };
        int target5 = 30;
        System.out.println(Arrays.toString(Find2Sum(nums5, target5 - 30)));
    }
}