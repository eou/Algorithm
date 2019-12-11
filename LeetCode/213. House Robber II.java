// 213. House Robber II
// 分成两个部分，0 ~ n - 2, 1 ~ n - 1
// 1 -> 2 -> 3 -> 1 becomes 2 -> 3 if 1 is not robbed.
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums.length == 1 ? nums[0] : 0;
        }
        
		return Math.max(helper(nums, 0, nums.length - 2), helper(nums, 1, nums.length - 1));

        
    }

    private int helper(int[] nums, int start, int end) {
        int prev = 0;
        int curr = 0;
        for (int i = start; i <= end; i++) {
            int tmp = curr;
            curr = Math.max(prev + nums[i], curr);
            prev = tmp;
        }

        return curr;
    }
}