// 31. Next Permutation
// 纯粹数学规律，从低位到高位，找第一个出现递减的数字n，然后在第一列递增数字中找大于n的最小数字与n交换，然后从n之后一位到最低位进行反转
// [6,8,5,4,7,3,2,1,0] => [6,8,5,7,4,3,2,1,0] => [6,8,5,7,0,1,2,3,4]
// [6,8,5,6,9,8,7,1,0] => [6,8,5,7,9,8,6,1,0] => [6,8,5,7,0,1,6,8,9]
class Solution {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        for (int left = i + 1, right = nums.length - 1; left < right; left++, right--) {
            swap(nums, left, right);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}