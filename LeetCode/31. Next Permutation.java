// 31. Next Permutation
// => LintCode 51. Previous Permutation
// 纯数学规律，从低位到高位（从后往前 / 从右往左），找第一个出现递减的数字 n
// 然后在第一列递增数字中找大于 n 的最小数字与 n 交换
// 然后从 n 之后一位到最低位进行反转

// [6,8,5,4,7,3,2,1,0] => [6,8,5,7,4,3,2,1,0] => [6,8,5,7,0,1,2,3,4]
// [6,8,5,6,9,8,7,1,0] => [6,8,5,7,9,8,6,1,0] => [6,8,5,7,0,1,6,8,9]

// 证明：
// [1,2,3,5,4,4,3] 中不需要换 1，因为此数不是以 1 开头的排列最大数，2 同理，换低位数可以使其变大，所以不需要换它
// 发现从 3 开始 3,5,.. 无法换 3 的低位使其变得更大，这是以 3 开头最大的数字，于是 3 要换掉
// 因此需要找 3 之后的一个大数换它。要找一个比 3 大但又是备选数中最小的，否则增大得过快
// 所以在 5,4,4,3 中找 4
// 规律即找到最后一部分非单调增的数列，从此数列中找一个数换掉此数列前面紧挨的一个数
// 最后反转是使得增大的数尽可能小
class Solution {
    public void nextPermutation(int[] nums) {
        // start from end
        int target = nums.length - 2;
        // non-increasing
        for (; target >= 0 && nums[target] >= nums[target + 1]; target--) // target >= 0 should be the first check condition !!!
            ;

        int larger = nums.length - 1;
        // if exist, otherwise numbers are all decreasing: [5,4,2,1] => [1,2,4,5]
        if (target >= 0) {
            // find a number larger than target
            for (; larger > target && nums[larger] <= nums[target]; larger--)
                ;
            swap(nums, target, larger);
        }

        // reverse
        for (int i = target + 1, j = nums.length - 1; i < j; i++, j--) {
            swap(nums, i, j);
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}