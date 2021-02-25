// 81. Search in Rotated Sorted Array II
class Solution {
    public boolean search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return true;
            }

            if (nums[mid] < nums[right]) {
                if (target >= nums[mid] && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid;
                }
            } else if (nums[mid] > nums[right]) {
                if (target >= nums[left] && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid;
                }
            } else {
                // The only difference with 33. Search in Rotated Sorted Array
                right--;
            }
        }

        if (nums[left] == target) {
            return true;
        }

        if (nums[right] == target) {
            return true;
        }

        return false;
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