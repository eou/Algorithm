// 41. First Missing Positive
class Solution {
    public int firstMissingPositive(int[] nums) {
        // 1 should be in nums[0], 2 should be in nums[1], ..., n should be in nums[n - 1]
        for(int i = 0; i < nums.length; i++) {
            while(nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1; // 如果全部是正确排列，就返回数组中最大正整数的后一个
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}