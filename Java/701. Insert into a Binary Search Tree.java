// 701. Insert into a Binary Search Tree
class Solution {
    // 非递归插入节点
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            root.val = val;
            return root;
        }

        TreeNode node = root;
        TreeNode newNode = new TreeNode(val);
        while (node != null) {
            if (node.val < val) {
                if (node.right == null) {
                    node.right = newNode;
                    return root;
                }
                // 如果没空节点位置，就继续往下走
                node = node.right;
            } 
            else if (node.val > val) {
                if (node.left == null) {
                    node.left = newNode;
                    return root;
                }
                // 如果没空节点位置，就继续往下走
                node = node.left;
            } 
            else {
                // 如已经有了节点，就不用插入直接返回
                return root;
            }
        }
        return root;
    }
}

class Solution {
    // 递归版本更加简洁
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode node = new TreeNode(val);

        // 递归到空位置就直接返回，也就是插入
        if (root == null) {
            return node;
        }
        if (root.val > node.val) {
            root.left = insertIntoBST(root.left, val);
        } 
        else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }
}