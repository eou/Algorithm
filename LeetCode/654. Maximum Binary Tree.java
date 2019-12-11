// 654. Maximum Binary Tree
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        
        int maxIndex = 0, max = nums[0];
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] > max) {
                maxIndex = i;
                max = nums[i];
            }
        }
        
        int[] nums1 = new int[maxIndex];
        int[] nums2 = new int[nums.length - maxIndex - 1];
        System.arraycopy(nums, 0, nums1, 0, nums1.length);
        System.arraycopy(nums, maxIndex + 1, nums2, 0, nums2.length);
        
        TreeNode root = new TreeNode(max);
        root.left = constructMaximumBinaryTree(nums1);
        root.right = constructMaximumBinaryTree(nums2);
        
        return root;
    }
}