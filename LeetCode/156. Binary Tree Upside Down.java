// 156. Binary Tree Upside Down
// https://leetcode.com/problems/binary-tree-upside-down/discuss/49406/Java-recursive-(O(logn)-space)-and-iterative-solutions-(O(1)-space)-with-explanation-and-figure
class Solution {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        TreeNode leftmost = root;
        while (leftmost.left != null) {
            leftmost = leftmost.left;
        }
        
        dfs(root);
        
        return leftmost;
    }
    
    public TreeNode dfs(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        if (root.left == null && root.right == null) {
            return root;
        }
        
        // !! disconnect previous connection, otherwise will form cycle
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = null;
        root.right = null;

        TreeNode rightmost = dfs(left);
        rightmost.left = right;
        rightmost.right = root;
        
        return root;
    }
}

// recursion
class Solution {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }

        TreeNode newRoot = upsideDownBinaryTree(root.left);
        root.left.left = root.right; // node 2 left children
        root.left.right = root; // node 2 right children
        root.left = null;
        root.right = null;
        return newRoot;
    }
}

// iterative
class Solution {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        TreeNode curr = root;
        TreeNode next = null;
        TreeNode temp = null;
        TreeNode prev = null;

        while (curr != null) {
            next = curr.left;

            // swapping nodes now, need temp to keep the previous right child
            curr.left = temp;
            temp = curr.right;
            curr.right = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }
}