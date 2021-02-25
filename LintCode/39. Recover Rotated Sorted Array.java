// 39. Recover Rotated Sorted Array
// 3 step reverse
// [3, 4, 5, 1, 2]
// [3, 4, 5], [1, 2] => [5, 4, 3], [2, 1] => [1, 2, 3, 4, 5]
public class Solution {
    /**
     * @param nums: An integer array
     * @return: nothing
     */
    public void recoverRotatedSortedArray(List<Integer> nums) {
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i) > nums.get(i + 1)) {
                reverse(nums, 0, i);
                reverse(nums, i + 1, nums.size() - 1);
                reverse(nums, 0, nums.size() - 1);
            }
        }
    }

    private void reverse(List<Integer> nums, int start, int end) {
        for (int left = start, right = end; left < right; left++, right--) {
            int mid = nums.get(left);
            nums.set(left, nums.get(right));
            nums.set(right, mid);
        }
    }
}