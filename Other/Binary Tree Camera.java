/**
 * Binary Tree Camera.java 
 * 在树的结点上装camera，每一个结点上的camera能够监视到父结点，自身和两个子结点。最少安装多少个camera监视到所有结点
 */
class Solution {
    // 分治法，每次递归返回保存在数组里的两个值：此节点上有camera和此节点上无camera时最小总camera个数
    public int binaryTreeCamera(TreeNode root) {
        // dp[0] 保存此结点无camera时，以此结点为根的树上总camera个数
        // dp[1] 保存此结点有camera时，以此结点为根的树上总camera个数
        int[] dp = helper(root);
        return Math.min(dp[0], dp[1]);
    }
    
    private int[] helper(TreeNode root) {
        if(root == null) {
            return new int[2];
        }
        
        if(root.left == null && root.right == null) {
            return new int[] {0, 1};
        }
        
        int[] dp = new int[2];
        int[] left = helper(root.left);
        int[] right = helper(root.right);
        
        if(root.left == null) {
            dp[0] = right[1];
            dp[1] = Math.min(right[0], right[1]) + 1;
        } else if(root.right == null) {
            dp[0] = left[1];
            dp[1] = Math.min(left[0], left[1]) + 1;
        } else {
            dp[0] = Math.min(left[0] + right[1], Math.min(left[1] + right[0], left[1] + right[1]));
            dp[1] = Math.min(left[0] + right[0], Math.min(left[0] + right[1], left[1] + right[0])) + 1;
        }
        
        return dp;
    }
}