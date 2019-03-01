// 81. Search in Rotated Sorted Array II
class Solution {
    public boolean search(int[] nums, int target) {
        // Step1. empty exception
        if (nums == null || nums.length == 0) {
            return false;
        }

        // Step2. binary search
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;

            // compared with target
            if (nums[mid] == target) {
                return true;
            }

            // compared with nums[end] can united operations in both rotated and unrotated sorted array
            if (nums[mid] == nums[end]) {
                end--;
            } else if (nums[mid] > nums[end]) {
                // mid in the first half of the array: [start, ..., mid]
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else {
                // mid in the second half of the array: [mid, ..., end]
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }

        // Step3. final process
        if (nums[start] == target) {
            return true;
        } else if (nums[end] == target) {
            return true;
        } else {
            return false;
        }
    }
}

class Solution {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int start = 0, end = nums.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return true;
            }
            // 比I多一个处理
            if (nums[start] == nums[mid] && nums[end] == nums[mid]) {
                start++;
                end--;
            } else if (nums[mid] >= nums[start]) {
                if (nums[mid] > target && nums[start] <= target) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if (nums[mid] < target && nums[end] >= target) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }

        // 如果之前while循环条件是start <= end，这里就不用判断，在while循环就判断完毕了，更加简洁
        if (nums[start] == target) {
            return true;
        }
        return false;
    }
}