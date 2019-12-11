// 238. Product of Array Except Self
class Solution {
    // 不用除法的思路就是先算自己左边所有数的乘积，然后右边所有数的乘积，最后相乘
    public int[] productExceptSelf(int[] nums) {
        int results[] = new int[nums.length];
        int leftProduct[] = new int[nums.length];
        int rightProduct[] = new int[nums.length];
        leftProduct[0] = 1;
        rightProduct[nums.length - 1] = 1;
        
        for(int i = 1; i < nums.length; i++) {
            leftProduct[i] = leftProduct[i - 1] * nums[i - 1];
        }
        for(int i = nums.length - 2; i >= 0; i--) {
            rightProduct[i] = rightProduct[i + 1] * nums[i + 1];
        }
        
        for(int i = 0; i < nums.length; i++) {
            results[i] = leftProduct[i] * rightProduct[i];
        }
        
        return results;
    }
}

class Solution {
    // 降低空间复杂度，用一个辅助变量
    public int[] productExceptSelf(int[] nums) {
        int results[] = new int[nums.length];
        results[0] = 1;
        for(int i = 1; i < nums.length; i++) {
            results[i] = results[i - 1] * nums[i - 1];
        }
        
        int right = 1;
        for(int i = nums.length - 1; i >= 0; i--) {
            results[i] *= right;
            right *= nums[i];
        }
        
        return results;
    }
}