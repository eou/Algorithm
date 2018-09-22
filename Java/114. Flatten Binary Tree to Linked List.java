// 114. Flatten Binary Tree to Linked List
// 其实是一个用树节点伪装的链表
class Solution {
    // 其实不需要这个辅助类，因为first不需要通过类返回
    private class auxiliary {
        TreeNode first, last;
        public auxiliary(TreeNode first, TreeNode last) {
            this.first = first;
            this.last = last;
        }
    }

    public void flatten(TreeNode root) {
        root = helper(root).first;
    }

    public auxiliary helper(TreeNode root) {
        if (root == null) {
            return new auxiliary(null, null);
        } 
        else {
            auxiliary left = helper(root.left);
            auxiliary right = helper(root.right);
            TreeNode node = root;
            if (left.first != null && right.first != null) {
                left.last.right = right.first;
                node.right = left.first;
                node.left = null; // 注意要加这段代码
                return new auxiliary(node, right.last);
            } 
            else if (left.first != null) {
                node.right = left.first;
                node.left = null; // 注意要加这段代码
                return new auxiliary(node, left.last);
            } 
            else if (right.first != null) {
                node.right = right.first;
                // 此处node肯定无left子树
                return new auxiliary(node, right.last);
            } 
            else {
                return new auxiliary(node, node);
            }
        }
    }
}

class Solution {
    // 上一种方法的简化版本，只需返回last节点
    public void flatten(TreeNode root) {
        helper(root);
    }

    public TreeNode helper(TreeNode root) {
        if (root == null) {
            return null;
        } 
        else {
            TreeNode leftLast = helper(root.left);
            TreeNode rightLast = helper(root.right);

            if (leftLast != null && rightLast != null) {
                leftLast.right = root.right;
                root.right = root.left;
                root.left = null;
                return rightLast;
            }
            if (leftLast != null) {
                root.right = root.left;
                root.left = null;
                return leftLast;
            }
            if (rightLast != null) {
                return rightLast;
            }
            return root;
        }
    }
}

class Solution {
    // 分治法
    public void flatten(TreeNode root) {
        flatten(root, null);
    }

    private TreeNode flatten(TreeNode root, TreeNode pre) {
        if (root == null) {
            return pre;
        }

        pre = flatten(root.right, pre);
        pre = flatten(root.left, pre);
        root.right = pre;

        root.left = null;
        pre = root;
        return pre;
    }
}

class Solution {
    // 遍历法，类似后序遍历
    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }
}

class Solution {
    // 非递归版本
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }

        while (root != null) {
            if (root.left == null) {
                root = root.right;
                continue;
            }

            // 找左子树的最右节点
            TreeNode left = root.left;
            while (left.right != null) {
                left = left.right;
            }
            // 找到左子树的最右节点连接到右子树上，也就是左子树拉平之后的最后节点
            left.right = root.right;
            // 然后根节点连上左子树
            root.right = root.left;
            root.left = null;
            root = root.right;
        }
    }
}