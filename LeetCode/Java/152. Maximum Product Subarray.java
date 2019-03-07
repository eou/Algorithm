// 152. Maximum Product Subarray
class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int result = nums[0], min = nums[0], max = nums[0];
        // Note that starting from 1
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] < 0) {
                int tmp = min;
                min = max;
                max = tmp;
            }
            
            max = Math.max(nums[i], nums[i] * max);
            min = Math.min(nums[i], nums[i] * min);
            
            result = Math.max(result, max);
        }
        
        return result;
    }
}