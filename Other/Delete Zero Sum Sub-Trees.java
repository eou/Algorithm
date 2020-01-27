// Delete Zero Sum Sub-Trees.java
// leetcode playground
public class MainClass {
    public static TreeNode removeSubtree(TreeNode root) {
        int res = dfs(root);
        // for root
        if (res == 0) {
            return null;
        }
        return root;
    }
    
    public static int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int sumOfRight = dfs(root.right);
        if (sumOfRight == 0) {
            root.right = null;
        }
        int sumOfLeft = dfs(root.left);
        if (sumOfLeft == 0) {
            root.left = null;
        }
        return root.val + sumOfRight + sumOfLeft;
    }
    
    public static void main(String[] args) throws IOException {
    }
}