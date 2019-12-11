// 259. 3Sum Smaller
class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        int result = 0;
        Arrays.sort(nums);
        // 不需要做任何重复元素的筛选，因为题目要求下标组合而不是元素组合
        for(int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while(left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if(sum < target) {
                    result += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
}