// 1448. Count Good Nodes in Binary Tree
class Solution {
    private int res = 0;
    public int goodNodes(TreeNode root) {
        dfs(root, root.val);
        return res;
    }
    
    private void dfs(TreeNode root, int curMax) {
        if (root == null) {
            return;
        }

        if (root.val >= curMax) {
            res++;
        }
        
        curMax = Math.max(root.val, curMax);
        dfs(root.left, curMax);
        dfs(root.right, curMax);
    }
}

class Solution {
    public int goodNodes(TreeNode root) {
        return dfs(root, root.val);
    }

    private int dfs(TreeNode root, int curMax) {
        if (root == null) {
            return 0;
        }

        int res = 0;
        if (root.val >= curMax) {
            res++;
        }

        curMax = Math.max(root.val, curMax);
        res += dfs(root.left, curMax);
        res += dfs(root.right, curMax);
        return res;
    }
}