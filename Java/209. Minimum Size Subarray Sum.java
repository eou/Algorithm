// 209. Minimum Size Subarray Sum
// Two pointer问题，类似sliding window
// 竟然follow up要求的复杂度更高，O(nlogn)
// 注意数组中全是正整数，保证了前缀子数组的和是递增的，有序的情况下可以用binary search
// 其实也可以用分治，复杂度也是O(nlogn)
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE, sum = 0;
        int left = 0, right = 0;
        while (right < nums.length) {
            sum += nums[right];
            while (sum >= s) {
                min = Math.min(min, right - left + 1);
                sum -= nums[left++];
            }
            right++;
        }

        // 注意有可能一直min没有更新，因为子数组和最大也没有达到s
        return min = min == Integer.MAX_VALUE ? 0 : min;
    }
}

class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] preSum = new int[nums.length + 1];
        preSum[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < preSum.length; i++) {
            // 要使得两个preSum的差不小于s，所以在preSum数组中找preSum[i] + s
            int end = binarySearch(i + 1, preSum.length - 1, preSum[i] + s, preSum);
            if (end != preSum.length) {
                min = min > (end - i) ? end - i : min;
            }
        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }

    // 注意二分查找写法，最后要返回不小于target的位置
    private int binarySearch(int left, int right, int target, int[] nums) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    
    // 这种写法可能返回小于target的最大位置，最后还要处理
    private int binarySearch(int left, int right, int target, int[] nums) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (left < nums.length && nums[left] < target) {
            return left + 1;
        }
        return left;
    }
}