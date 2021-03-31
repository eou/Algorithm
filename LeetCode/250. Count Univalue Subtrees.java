// 250. Count Univalue Subtrees
class Solution {
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // !!! be careful of the priority of ?: operation, it should have parenthesises here
        // otherwise it will calculate + first
        return (isUnivalSubtrees(root) ? 1 : 0) + countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
    }

    private boolean isUnivalSubtrees(TreeNode root) {
        if (root.left != null && root.right != null) {
            return root.val == root.left.val && root.val == root.right.val && isUnivalSubtrees(root.left)
                    && isUnivalSubtrees(root.right);
        }

        if (root.left != null) {
            return root.val == root.left.val && isUnivalSubtrees(root.left);
        }

        if (root.right != null) {
            return root.val == root.right.val && isUnivalSubtrees(root.right);
        }

        // single node or empty node should return true
        // actually it is impossible to reach null node in our codes here
        return true;
    }
}

// 简化上个版本
class Solution {
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return (isUnivalSubtree(root) ? 1 : 0) + countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
    }

    private boolean isUnivalSubtree(TreeNode root) {
        boolean result = true;
        if (root.left != null) {
            result &= root.val == root.left.val;
            result &= isUnivalSubtree(root.left);
        }
        if (root.right != null) {
            result &= root.val == root.right.val;
            result &= isUnivalSubtree(root.right);
        }
        return result;
    }
}

class Solution {
    int res = 0;
    public int countUnivalSubtrees(TreeNode root) {
        all(res, 0);
        return res;
    }

    boolean all(TreeNode root, int val) {
        if (root == null) {
            return true;
        }
            
        // "|" ensures the recursion do both sides instead of "||" only dose recursion on left side if it is wrong(it will miss the count from right side)
        if (!all(root.left, root.val) | !all(root.right, root.val)) {
            return false;
        }

        res++;  // we have 1 more unival subtree
        return root.val == val;
    }
}