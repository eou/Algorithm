// 124. Binary Tree Maximum Path Sum
// 与 543. Diameter of Binary Tree 一个类型
class Solution {
    int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxSumOfOneSide(root);
        return max;
    }
    
    // 返回其中一个子树的最大和路径，形成一边
    private int maxSumOfOneSide(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        int left = Math.max(0, maxSumOfOneSide(root.left));
        int right = Math.max(0, maxSumOfOneSide(root.right));

        max = Math.max(max, left + right + root.val);
        return Math.max(left, right) + root.val;
    }
}