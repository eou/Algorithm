// 704. Binary Search
// Binary Search Template，熟读并背诵全文😈
// nums.length > 0, integer, unique numbers, ascending order
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null) {
            return -1;
        }
        
        // non-recursive
        // return binarySearch(nums, target);
        
        // recursive
        return binarySearch2(nums, target, 0, nums.length - 1);
    }
    
    private int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    private int binarySearch2(int[] nums, int target, int left, int right) {
        // exit
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearch2(nums, target, mid + 1, right);
        } else {
            return binarySearch2(nums, target, left, mid - 1);
        }
    }
}