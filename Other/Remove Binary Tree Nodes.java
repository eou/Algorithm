/**
 * Remove Binary Tree Nodes
 * 删除二叉树指定结点，并返回剩下子树
 */
class Solution {
    public List<TreeNode> removeBinaryTreeNodes(TreeNode root, List<TreeNode> nodes) {
        List<TreeNode> result = new ArrayList<>();
        helper(root, result, nodes);
        
        // 最后判断根节点
        if(isFind(root, nodes)) {
            return result;
        }

        result.add(root);
        return result;
    }
    
    // 在递归中只在删除操作后添加剩余的子树，最后要判断是否添加根
    private void helper(TreeNode root, List<TreeNode> result, List<TreeNode> nodes) {
        if(root == null) {
            return;
        }

        // 由于子树中可能存在要删除的结点，所以用后序遍历删除结点
        helper(root.left, result);
        helper(root.right, result);
        
        // 删除左右子树结点
        if(root.left != null && isFind(root.left, nodes)) {
            root.left = null;
        }
        if(root.right != null && isFind(root.right, nodes)) {
            root.right = null;
        }
        
        // 删除自身后把子树加入结果中
        if(isFind(root, nodes)) {
            if(root.left != null) {
                result.add(root.left);
            }
            if(root.right != null) {
                result.add(root.right);
            }
        }
    }

    private boolean isFind(TreeNode root, List<TreeNode> nodes) {
        for(TreeNode node : nodes) {
            if(node.val == root.val) {
                return true;
            }
        }
        return false;
    }
}