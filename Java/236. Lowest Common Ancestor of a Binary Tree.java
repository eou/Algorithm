// 236. Lowest Common Ancestor of a Binary Tree
class Solution {
    // 这种解法很不严格，如果一个节点在树中，另一个不在，会返回其中一个节点而不是null
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 思路就是反过来想，哪一个root里面同时包含p和q两个节点，然后一层层递归往上面传递
        if (left != null && right != null) {
            // find LCA
            return root;
        } 
        else if (left != null) {
            return left;
        } 
        else if (right != null) {
            return right;
        } 
        else {
            return null;
        }
    }
}