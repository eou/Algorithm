// 209. Minimum Size Subarray Sum
// Two pointer, sliding window
// 竟然 follow up 要求的复杂度更高，O(nlogn)
// 注意数组中全是正整数，保证了前缀和数组 prefix 是递增的，数组有序的情况下就可以用binary search
// 其实也可以用分治，复杂度也是O(nlogn)
class Solution {
    // sliding window，时间复杂度为 O(n)
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length + 1;
        int sum = 0;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            sum += nums[right];
            while (sum >= s) {
                len = Math.min(len, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;
        }

        return len == nums.length + 1 ? 0 : len; // 注意有可能一直 len 没有更新，因为子数组和最大也没有达到s
    }
}

class Solution {
    // 时间复杂度为 O(nlogn)
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

    // 注意二分查找写法，最后要返回不小于target的位置，包括越界下标
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
    
    // 这种 left < right 的写法可能返回小于target的最大位置，最后处理相等时候的情况
    private int binarySearch(int left, int right, int target, int[] nums) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        if (left == right) {
            return nums[left] >= target ? left : nums.length;
        }
        return left;
    }

    // 或者这样写，但前面调用的right写成nums.length而不是nums.length-1
    private int binarySearch(int left, int right, int target, int[] nums) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}