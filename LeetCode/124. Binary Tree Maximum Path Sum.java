// 124. Binary Tree Maximum Path Sum
// 与 543. Diameter of Binary Tree 一个类型
class Solution {
    int res = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxSumOfOneSide(root);
        return max;
    }
    
    // return the maimum path sum on one side
    private int maxSumOfOneSide(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        int left = Math.max(0, maxSumOfOneSide(root.left));
        int right = Math.max(0, maxSumOfOneSide(root.right));

        // update result, combining 2 sides
        res = Math.max(res, left + right + root.val);

        // !!! only return one side sum
        return Math.max(left, right) + root.val;
    }
}