// 543. Diameter of Binary Tree
// 思路有点像找数组中最长连续子序列的分治解法，子序列要么在中点左边或右边，要么包括中点
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        return Math.max(Math.max(diameterOfBinaryTree(root.left), diameterOfBinaryTree(root.right)), longestPathThroughRoot(root));
    }
    
    private int longestPathThroughRoot(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        return maxDepth(root.left) + maxDepth(root.right);
    }
    
    private int maxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        return Math.max(maxDepth(root.left) + 1, maxDepth(root.right) + 1);
    }
}

class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getHeight(root.left) + getHeight(root.right),
                Math.max(diameterOfBinaryTree(root.left), diameterOfBinaryTree(root.right)));
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
}

// !!! similar with 124. Binary Tree Maximum Path Sum
class Solution {
    int res = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return res;
    }

    private int maxDepth(TreeNode root) {
        if (root == null)
            return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        
        // 在找深度的同时更新一下此刻的最长路径，在以某个结点为根的时候会达到最长
        res = Math.max(res, left + right);

        return Math.max(left, right) + 1;
    }
}