// 34. Find First and Last Position of Element in Sorted Array
class Solution {
    // O(k+logn)
    public int[] searchRange1(int[] nums, int target) {
        int[] results = { -1, -1 };
        if (nums == null || nums.length == 0) {
            return results;
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[start] == target) {
            results[0] = start;
            int i = start;
            for (; i < nums.length && nums[i] == target; ++i);
            results[1] = i - 1;
        } 
        if (nums[end] == target) {
            results[0] = end;
            int i = end;
            for (; i < nums.length && nums[i] == target; ++i);
            results[1] = i - 1;
        }

        return results;
    }

    // O(logn)
    private int binarySearch(int[] nums, int target, boolean index) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        // 用[1,1,1,1]这种测试样例
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                if (index) {
                    end = mid; // first position
                } else {
                    start = mid; // last position
                }
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        // 最后处理顺序不一样
        if (index) {
            if (nums[start] == target) {
                return start;
            }
            if (nums[end] == target) {
                return end;
            }
        } 
        else {
            if (nums[end] == target) {
                return end;
            }
            if (nums[start] == target) {
                return start;
            }
        }
        return -1;
    }

    public int[] searchRange2(int[] nums, int target) {
        int[] results = { binarySearch(nums, target, true), binarySearch(nums, target, false) };
        return results;
    }
}