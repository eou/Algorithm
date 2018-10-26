// 674. Longest Continuous Increasing Subsequence
// 此题为 Continuous Subsequence, 300 不是 Continuous
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if(nums.length < 1) {
            return 0;
        }
        
        int dp[] = new int[nums.length];
        dp[0] = 1;
        
        int max = 1;
        for(int i = 1; i < nums.length; i++) {
            // 注意不递增时就为1
            dp[i] = nums[i] > nums[i - 1] ? dp[i - 1] + 1 : 1;
            max = Math.max(max, dp[i]);
        }
        
        return max;
    }
}