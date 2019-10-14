// Maximum sum of nodes in Binary tree such that no two are adjacent
// https://www.geeksforgeeks.org/maximum-sum-nodes-binary-tree-no-two-adjacent/
class Solution {
    /*
    [1, 2, 3]
    [1]
    []
    [1, null, 3]
    [1,2,3,1,1,1,1]
    [1,1,1,1,1,4,6]
    [10,1,null,2,3,1,null,4,5] 21
    [1,2,3,1,null,4,5] 11
    */
    public int maxSumWithoutAdjacentNode(TreeNode root) {
        // two subproblems, include root node, exclude root node
        return Math.max(helper(root, true), helper(root, false));
    }
    
    public int helper(TreeNode root, boolean includeRoot) {
        if (root == null) {
            return 0;
        }
        
        int sum = 0;
        if (includeRoot) {
            // can only consider tree nodes which are below first level
            sum += root.val;
            // exclude left and right nodes
            sum += (helper(root.left, false) + helper(root.right, false));
        } else {
            // inlcude or exclude left child
            sum += Math.max(helper(root.left, false), helper(root.left, true));
            // inlcude or exclude right child
            sum += Math.max(helper(root.right, false), helper(root.right, true));
        }
        return sum;
    }
}