// 53. Maximum Subarray 
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // prefix sum array
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; ++i) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1]; // nums[0] + nums[1] + ... + nums[i] = prefixSum[i + 1]
        }

        // subarray sum [i...j] = prefixSum[j + 1] - prefixSum[i]
        // => Best Time to buy and sell stock I
        int largest = prefixSum[1], min = prefixSum[0];
        for (int i = 1; i < prefixSum.length; ++i) {
            largest = Math.max(largest, prefixSum[i] - min);
            min = Math.min(min, prefixSum[i]);
        }
        // Best Time to buy and sell stock I
        // int largest = 0, min = prices[0];
        // for (int i = 0; i < prices.length; ++i) {
        // largest = Math.max(largest, prices[i] - min);
        // min = Math.min(min, prices[i]);
        // }

        return largest;
    }
}

class Solution {
    // for every loop, preSum stores the sum of numbers which from nums[0] to nums[i]
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE, preSum = 0, minPre = 0;

        for(int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            max = Math.max(max, preSum - minPre);
            minPre = minPre > preSum ? preSum : minPre;
        }
        return max;
    }
}

class Solution {
    public int maxSubArray(int[] nums) {
        int dp[] = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];

        for(int i = 1; i < nums.length; i++) {
            dp[i] = dp[i - 1] > 0 ? dp[i - 1] + nums[i] : nums[i];
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}

class Solution {
    // divide and conquer:
    // Step1. Select the middle element of the array. So the maximum subarray may contain that middle element or not.
    // Step 2.1 If the maximum subarray does not contain the middle element, then we can apply the same algorithm to the the subarray to the left of the middle element and the subarray to the right of the middle element.
    // Step 2.2 If the maximum subarray does contain the middle element, then the result will be simply the maximum suffix subarray of the left subarray plus the maximum prefix subarray of the right subarray.
    // Step 3 return the maximum of those three answer.
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // 注意右端点是 nums.length - 1 而不是 nums.length，否则后面 mid 会出错
        return helper(nums, 0, nums.length - 1);
    }

    private int helper(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        int mid = (left + right) / 2;
        int leftSum = helper(nums, left, mid);
        int rightSum = helper(nums, mid + 1, right);
        int leftMax = nums[mid];
        int rightMax = nums[mid + 1];
        int tmp = 0;
        for (int i = mid; i >= left; i--) {
            tmp += nums[i];
            leftMax = Math.max(tmp, leftMax);
        }
        tmp = 0;
        for (int i = mid + 1; i <= right; i++) {
            tmp += nums[i];
            rightMax = Math.max(tmp, rightMax);
        }
        return Math.max(Math.max(leftSum, rightSum), leftMax + rightMax);
    }
}