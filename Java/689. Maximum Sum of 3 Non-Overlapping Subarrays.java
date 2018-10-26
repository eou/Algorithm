// 689. Maximum Sum of 3 Non-Overlapping Subarrays
class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int results[] = new int[3];
        int sum[] = new int[n + 1];
        int leftMax[] = new int[n];
        int rightMax[] = new int[n];
        
        // maintain prefix sum array
        sum[0] = 0;
        for(int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        
        // store the starting index for the left-interval whose elements' sum is the maximum
        int total = sum[k] - sum[0];
        for(int i = k; i < n; i++) {
            if(sum[i + 1] - sum[i + 1 - k] > total) {
                leftMax[i] = i + 1 - k;
                total = sum[i + 1] - sum[i + 1 - k];
            } else {
                leftMax[i] = leftMax[i - 1];
            }
        }
        
        rightMax[n - k] = n - k;
        total = sum[n] - sum[n - k];
        for(int i = n - k - 1; i >= 0; i--) {
            // >= means always select left most one
            if(sum[i + k] - sum[i] >= total) {
                rightMax[i] = i;
                total = sum[i + k] - sum[i];
            } else {
                rightMax[i] = rightMax[i + 1];
            }
        }
        
        int max = Integer.MIN_VALUE;
        for(int i = k; i <= n - 2 * k; i++) {
            int left = leftMax[i - 1], right = rightMax[i + k];
            total = (sum[i + k] - sum[i]) + (sum[left + k] - sum[left]) + (sum[right + k] - sum[right]);
            if(total > max) {
                max = total;
                results[0] = left;
                results[1] = i;
                results[2] = right;
            }
        }
        
        return results;
    }
}