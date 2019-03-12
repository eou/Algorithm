// 108. Convert Sorted Array to Binary Search Tree
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        TreeNode left = null;
        TreeNode right = null;
        int[] leftPart = new int[nums.length / 2];
        System.arraycopy(nums, 0, leftPart, 0, nums.length / 2);
        left = sortedArrayToBST(leftPart);

        if (nums.length - nums.length / 2 - 1 > 0) {
            int[] rightPart = new int[nums.length - nums.length / 2 - 1];
            System.arraycopy(nums, nums.length / 2 + 1, rightPart, 0, nums.length - nums.length / 2 - 1);
            right = sortedArrayToBST(rightPart);
        }

        TreeNode root = new TreeNode(nums[nums.length / 2]);
        root.left = left;
        root.right = right;

        return root;
    }
}