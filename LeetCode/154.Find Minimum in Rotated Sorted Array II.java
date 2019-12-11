// 154.Find Minimum in Rotated Sorted Array II
class Solution {
    public int findMin(int[] nums) {
        // Step1. empty exception
        if (nums == null || nums.length == 0) {
            return -1;
        }

        // Step2. binary search
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;

            // compared with nums[end] can united operations in both rotated and unrotated sorted array
            if (nums[mid] == nums[end]) {
                end--;
            } else if (nums[mid] > nums[end]) {
                // mid in the first half of the array: [start, ..., mid]
                start = mid;
            } else {
                // mid in the second half of the array: [mid, ..., end]
                end = mid;
            }
        }

        // Step3. final process
        if (nums[start] < nums[end]) {
            return nums[start];
        } else {
            return nums[end];
        }
    }
}

class Solution {
    public int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[end]) {
                start = mid + 1;
            } else if (nums[mid] > nums[end]) {
                end = mid - 1;
            } else {
                end--;
            }
        }

        return nums[start];
    }
}