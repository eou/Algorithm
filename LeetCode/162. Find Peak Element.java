// 162. Find Peak Element
class Solution {
    public int findPeakElement(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            // 此时 mid 在递减数列上
            // 不能 end = mid - 1; 因为 mid 本身可能是极大值
            if (nums[mid] > nums[mid + 1]) {
                end = mid;
            }
            // 不大于的时候可以 +1，因为 mid 本身肯定不会是极大值
            else {
                start = mid + 1;
            }
        }
        return start;
    }
}

class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else if (nums[mid] > nums[mid - 1]) {
                left = mid;
            } else {
                right = mid;
            }
        }

        if (nums[left] > nums[right]) {
            return left;
        } else {
            return right;
        }
    }
}