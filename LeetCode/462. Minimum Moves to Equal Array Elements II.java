// 462. Minimum Moves to Equal Array Elements II
// 此题LeetCode上官方solution很详尽，有O(n)算法
class Solution {
    // 我自己尝试用最接近平均数的数字作为目标数字，结果报错，实际上用中位数即可
    // 理由大意是移动到均值容易受到极端值的影响，而移动到中位数则不存在这个问题
    // 如 [1,1,3,4,12345678] 这类
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int num : nums) {
            sum += Math.abs(nums[nums.length / 2] - num);
        }
        return sum;
    }
}