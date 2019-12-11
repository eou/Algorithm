// 33. Search in Rotated Sorted Array
// 4,5,6,7 first half [start]
//          0,1,2 [end] second half
class Solution {
    public int search(int[] nums, int target) {
        // Step1. empty exception
        if (nums == null || nums.length == 0) {
            return -1;
        }

        // Step2. binary search
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;

            // compared with target
            if (nums[mid] == target) {
                return mid;
            }

            // compared with nums[end] can united operations in both rotated and unrotated sorted array
            if (nums[mid] > nums[end]) {
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
            return start;
        } else if (nums[end] == target) {
            return end;
        } else {
            return -1;
        }
    }
}

class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            // 由于没有重复元素，当 nums[mid] == nums[start] 成立的时候就是 mid == start 的时候，此时 start + 1 =
            // end
            // 因为这种情况下之前已经验证 nums[mid] != target，所以需要 start = mid + 1
            // 这也可以单独拿出来作为一个else语句，更加直观
            if (nums[mid] >= nums[start]) {
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

        if (nums[start] == target) {
            return start;
        }

        return -1;
    }
}

class Solution {
    // 递归版本
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        return search(nums, target, low, high);
    }

    private int search(int[] nums, int target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;

        if (nums[mid] == target) {
            return mid;
        }

        // if left half is sorted
        if (nums[low] <= nums[mid]) {
            if (nums[low] <= target && nums[mid] >= target) {
                return search(nums, target, low, mid - 1);
            } else {
                return search(nums, target, mid + 1, high);
            }
        }
        
        else {
            if (nums[mid] <= target && nums[high] >= target) {
                return search(nums, target, mid + 1, high);
            } else {
                return search(nums, target, low, mid - 1);
            }
        }
    }
}