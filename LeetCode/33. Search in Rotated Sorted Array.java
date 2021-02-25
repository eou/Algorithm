// 33. Search in Rotated Sorted Array
// https://www.cnblogs.com/grandyang/p/4325648.html
// The goal is to find the sorted subarray!!!
// If we compare nums[mid] with nums[right]:
// [7,0,1,2,3,4,5] => nums[mid] < nums[right] means right side [2,3,4,5] is sorted
// [2,3,4,5,7,0,1] => nums[mid] > nums[right] means left side [2,3,4,5] is sorted
// If nums[mid] == nums[right] means left == mid == right, which is impossible under `left + 1 < right`

// If we compare nums[mid] with nums[left]:
// [7,0,1,2,3,4,5] => nums[mid] < nums[left] means right side [2,3,4,5] is sorted
// [2,3,4,5,7,0,1] => nums[mid] > nums[left] means left side [2,3,4,5] is sorted
// If nums[mid] == nums[left] means left == mid == right - 1, which is impossible under `left + 1 < right`
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            
            // Improve performace!
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < nums[right]) {
                if (target >= nums[mid] && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid;
                }
            } else {
                if (target >= nums[left] && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
        }

        if (nums[left] == target) {
            return left;
        }

        if (nums[right] == target) {
            return right;
        }

        return -1;
    }
}

class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < nums[left]) {
                if (target >= nums[mid] && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid;
                }
            } else {
                if (target >= nums[left] && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
        }

        if (nums[left] == target) {
            return left;
        }

        if (nums[right] == target) {
            return right;
        }

        return -1;
    }
}

// Interesting. nums[mid] 与 nums[right] / nums[nums.length - 1], nums[left] / nums[0] 比较都是正确的
// 大致证明即每次我们只筛选出有序的一边，只要二分操作正确，整个过程就正确
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            
            // 'right' can be replaced with 'nums.length - 1'
            // 'left' can be replaced with '0'
            if (nums[mid] < nums[nums.length - 1]) {
                if (target >= nums[mid] && target <= nums[nums.length - 1]) {
                    left = mid;
                } else {
                    right = mid;
                }
            } else {
                if (target >= nums[0] && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
        }

        if (nums[left] == target) {
            return left;
        }

        if (nums[right] == target) {
            return right;
        }

        return -1;
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
        return search(nums, target, 0, nums.length - 1);
    }

    private int search(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;

        if (nums[mid] == target) {
            return mid;
        }

        // if left half is sorted
        if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && nums[mid] >= target) {
                return search(nums, target, left, mid - 1);
            } else {
                return search(nums, target, mid + 1, right);
            }
        }
        
        else {
            if (nums[mid] <= target && nums[right] >= target) {
                return search(nums, target, mid + 1, right);
            } else {
                return search(nums, target, left, mid - 1);
            }
        }
    }
}