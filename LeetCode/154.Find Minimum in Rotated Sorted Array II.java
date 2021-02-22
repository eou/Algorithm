// 154. Find Minimum in Rotated Sorted Array II
class Solution {
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                // only happens on rotated sorted array but not sorted array
                left = mid;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // nums[right] is useless, can move pointer to decrease the range
                right--;
            }
        }

        if (nums[left] < nums[right]) {
            return nums[left];
        } else {
            return nums[right];
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