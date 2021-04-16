// 105. Construct Binary Tree from Preorder and Inorder Traversal
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 1. find root in preorder; 2. find last right node; 3. find right part in inorder; 4. find right tree in inorder;
        return dfs(preorder, 0, inorder, 0, preorder.length);
    }
    
    private TreeNode dfs(int[] preorder, int preStart, int[] inorder, int inStart, int len) {
        if (len == 0) {
            return null;
        }
        
        if (len == 1) {
            return new TreeNode(preorder[preStart]);
        }
        
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);
        
        int rightInStart = inStart, leftInStart = inStart, leftLen = len - 1, rightLen = 0;
        for (int i = inStart + len - 1; i >= inStart; i--) {
            if (inorder[i] == rootVal) {
                rightInStart = i + 1;
                rightLen = inStart + len - 1 - i;
                leftLen = len - rightLen - 1;
                break;
            }
        }
        
        int rightPreStart = preStart + 1 + leftLen, leftPreStart = preStart + 1;
        root.left = dfs(preorder, leftPreStart, inorder, leftInStart, leftLen);
        root.right = dfs(preorder, rightPreStart, inorder, rightInStart, rightLen);

        return root;
    }
}