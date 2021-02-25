// 596. Minimum Subtree
// 3 values: current subtree sum, current minimum sum, current minimum subtree
// DAC + Traverse
public class Solution {
    /**
     * @param root: the root of binary tree
     * @return: the root of the minimum subtree
     */
    private TreeNode res = null;
    private int minSum = Integer.MAX_VALUE;

    public TreeNode findSubtree(TreeNode root) {
        subtreeSum(root);
        return res;
    }
    
    private int subtreeSum(TreeNode node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            if (node.val < minSum) {
                minSum = node.val;
                res = node;
            }
            
            return node.val;
        }
        
        int leftSum = subtreeSum(node.left);
        int rightSum = subtreeSum(node.right);
        
        int curSum = leftSum + rightSum + node.val;
        if (curSum < minSum) {
            minSum = curSum;
            res = node;
        }

        return curSum;
    }
}

// DFS
public class Solution {
    /**
     * @param root: the root of binary tree
     * @return: the root of the minimum subtree
     */
    private class AuxType {
        TreeNode node;
        int curMinSum;
        int curSum;

        AuxType(TreeNode node, int curMinSum, int curSum) {
            this.node = node;
            this.curMinSum = curMinSum;
            this.curSum = curSum;
        }
    }

    public TreeNode findSubtree(TreeNode root) {
        return dfs(root).node;
    }

    private AuxType dfs(TreeNode root) {
        if (root == null) {
            return new AuxType(null, Integer.MAX_VALUE, 0);
        }

        AuxType left = dfs(root.left);
        AuxType right = dfs(root.right);

        int curSum = left.curSum + right.curSum + root.val;
        AuxType aux = new AuxType(root, 0, curSum);

        if (left.curMinSum >= curSum && right.curMinSum >= curSum) {
            aux.curMinSum = curSum;
        } else if (left.curMinSum < right.curMinSum) {
            aux.curMinSum = left.curMinSum;
            aux.node = left.node;
        } else {
            aux.curMinSum = right.curMinSum;
            aux.node = right.node;
        }

        return aux;
    }
}