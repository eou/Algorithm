// 31. Next Permutation
// => LintCode 51. Previous Permutation
// https://leetcode-cn.com/problems/next-permutation/solution/xia-yi-ge-pai-lie-by-leetcode-solution/
// 纯数学规律，从低位到高位（从后往前 / 从右往左），找第一个出现递减的数字 n
// 然后在(n, nums.length)这列递增数字中找大于 n 的最小数字与 n 交换，此时排列已经增大
// 然后从 n 之后一位到最低位进行反转，保证排列增加幅度最小

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
        // 1. search backwards to find first (i, i + 1) which nums[i] < nums[i + 1]. Thus nums[i] could be replaced
        int s = -1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                s = i;
                break;
            }
        }
        
        // 2. Find the smallest number which is bigger than nums[s] after s
        if (s >= 0) {
            int r = nums.length - 1;
            for (int i = nums.length - 1; i > s; i--) {
                if (nums[i] > nums[s]) {
                    r = i;
                    break;
                }
            }

            // 3. swap the nums[s] and nums[r]
            swap(nums, s, r);   
        }
        
        // 4. reverse the subarray (s, nums.length - 1) to make it smallest
        reverse(nums, s + 1, nums.length - 1);
    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    private void reverse(int[] nums, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            swap(nums, i, j);
        }
    }
}