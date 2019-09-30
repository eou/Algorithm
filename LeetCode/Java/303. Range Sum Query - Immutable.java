// 303. Range Sum Query - Immutable
class NumArray {
    // Caching, 前缀和数组
    private int[] prefix;
    public NumArray(int[] nums) {
        prefix = new int[nums.length + 1];
        prefix[0] = 0;
        
        for(int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }
    
    public int sumRange(int i, int j) {
        return prefix[j + 1] - prefix[i];
    }
}

class NumArray {
    public int[] prefixSum;
    public NumArray(int[] nums) {
        prefixSum = new int[nums.length];
        if (nums.length > 0) {
            prefixSum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                prefixSum[i] = prefixSum[i - 1] + nums[i];
            }
        }
    }

    public int sumRange(int i, int j) {
        return prefixSum[j] - (i == 0 ? 0 : prefixSum[i - 1]);
    }
}

/**
 * Your NumArray object will be instantiated and called as such: NumArray obj =
 * new NumArray(nums); int param_1 = obj.sumRange(i,j);
 */