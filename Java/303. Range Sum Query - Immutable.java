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

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */