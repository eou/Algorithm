// 144. Binary Tree Preorder Traversal
// 根 → 左 → 右
class Solution {
    // 递归版本，分治法
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root != null) {
            list.add(root.val);
            list.addAll(preorderTraversal(root.left));
            list.addAll(preorderTraversal(root.right));
        }
        return list;
    }
}

class Solution {
    // 递归版本，遍历法
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        traverse(root, list);
        return list;
    }

    private void traverse(TreeNode root, ArrayList<Integer> list) {
        if (root == null) {
            return;
        }

        list.add(root.val);
        traverse(root.left, list);
        traverse(root.right, list);
    }
}

class Solution {
    // 非递归版本，熟读并背诵全文😈
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        ArrayList<Integer> list = new ArrayList<Integer>();

        if (root == null) {
            return list;
        }

        stack.push(root);
        while (!stack.empty()) {
            // 弹出节点并进行相应操作
            TreeNode node = stack.pop();
            list.add(node.val);

            // 然后分别打入右节点和左节点
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return list;
    }
}

class Solution {
    // 非递归版本另一种形式
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        // 只有右节点被放入栈中
        Stack<TreeNode> rights = new Stack<TreeNode>();

        TreeNode node = root;
        while (node != null) {
            // 首先对节点进行相应操作
            list.add(node.val);

            // 若存在右节点，打入栈中备用
            if (node.right != null) {
                rights.push(node.right);
            }

            node = node.left;

            // 若没有左节点，取栈中右节点
            if (node == null && !rights.isEmpty()) {
                node = rights.pop();
            }
        }

        return list;
    }
}

class Solution {
    // 非递归版本，morris traversal, 空间复杂度O(1)，不需要栈，其实是利用线索二叉树thread binary tree的特性
    // 虽然有两个while循环，但是时间复杂度仍然是O(n)
    // morris preorder 和 morris inorder 只有一行代码不同
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        TreeNode cur = root;
        TreeNode pre = null;

        if (root == null) {
            return list;
        }

        while (cur != null) {
            if (cur.left == null) {
                list.add(cur.val);
                cur = cur.right;
            } else {
                pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
                if (pre.right == null) {
                    list.add(cur.val);
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    cur = cur.right;
                }
            }
        }

        return list;
    }
}