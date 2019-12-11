// 152. Maximum Product Subarray
class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxProduct = nums[0];
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = nums[0];
        min[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                min[i] = Math.min(max[i - 1] * nums[i], nums[i]);
                max[i] = Math.max(min[i - 1] * nums[i], nums[i]);
            } else {
                min[i] = Math.min(min[i - 1] * nums[i], nums[i]);
                max[i] = Math.max(max[i - 1] * nums[i], nums[i]);
            }
            maxProduct = Math.max(maxProduct, max[i]);
        }
        return maxProduct;
    }
}

class Solution {
    // 滚动数组
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int result = nums[0], min = nums[0], max = nums[0];
        // Notice that starting from 1
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