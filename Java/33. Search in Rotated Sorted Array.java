// 33. Search in Rotated Sorted Array
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
                } 
                else {
                    start = mid + 1;
                }
            } 
            else {
                if (nums[mid] < target && nums[end] >= target) {
                    start = mid + 1;
                } 
                else {
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