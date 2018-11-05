// 235. Lowest Common Ancestor of a Binary Search Tree
// LCA 的一般算法是分治，但利用 BST 的特性可以很快缩小到 LCA 所在的范围，不需要全局DFS
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return null;
        }
        while((root.val - p.val) * (root.val - q.val) > 0) {
            root = root.val > q.val ? root.left : root.right;
        }
        return root;
    }
}

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return null;
        }
        while(root != null) {
            if(root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if(root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }
}