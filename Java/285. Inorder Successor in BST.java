// 285. Inorder Successor in BST
class Solution {
    // 这是普通二叉树的后继查找方法，略微修改中序遍历的非递归模板
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }

        TreeNode node = root;
        boolean isfind = false;
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();

            if (isfind == true) {
                return node;
            }
            if (node == p) {
                isfind = true;
            }

            node = node.right;
        }

        return null;
    }
}

class Solution {
    // 利用BST左小右大的特性更简洁
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode node = root;
        TreeNode res = null;
        while (node != null) {
            if (node.val <= p.val) {
                node = node.right;
            } 
            else {
                res = node;
                node = node.left;
            }
        }
        return res;
    }
}