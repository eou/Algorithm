// 450. Delete Node in a BST
// 一般是用 Hibbard deletion algorithm
class Solution {
    // Hibbard deletion algorithm 非递归删除
    public TreeNode deleteNode(TreeNode root, int value) {
        TreeNode ptr = root;
        TreeNode parent = null;
        boolean leftNode = false;
        while (ptr != null) {
            if (ptr.val == value) {
                break;
            } else if (ptr.val < value) {
                parent = ptr;
                ptr = ptr.right;
            } else {
                parent = ptr;
                ptr = ptr.left;
            }
        }

        if (ptr == null || ptr.val != value) {
            return root;
        }

        if (parent == null) {
            // remove root
            if (ptr.left == null && ptr.right == null) {
                return null;
            } else if (ptr.left == null) {
                return ptr.right;
            } else if (ptr.right == null) {
                return ptr.left;
            } else {
                TreeNode left = ptr.left;
                TreeNode right = ptr.right;

                TreeNode leftmost = right;
                while (leftmost.left != null) {
                    leftmost = leftmost.left;
                }
                leftmost.left = left;
                return right;
            }
        }

        if (parent.left == ptr) {
            leftNode = true;
        }

        // remove
        if (ptr.left == null && ptr.right == null) {
            if (leftNode) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (ptr.left == null) {
            if (leftNode) {
                parent.left = ptr.right;
            } else {
                parent.right = ptr.right;
            }
        } else if (ptr.right == null) {
            if (leftNode) {
                parent.left = ptr.left;
            } else {
                parent.right = ptr.left;
            }
        } else {
            TreeNode left = ptr.left;
            TreeNode right = ptr.right;
            if (leftNode) {
                parent.left = right;
            } else {
                parent.right = right;
            }
            TreeNode leftmost = right;
            while (leftmost.left != null) {
                leftmost = leftmost.left;
            }
            leftmost.left = left;
        }

        return root;
    }
}

class Solution {
    // Hibbard deletion algorithm 递归删除
    private TreeNode deleteRootNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        if (root.right == null) {
            return root.left;
        }
        TreeNode next = root.right;
        TreeNode pre = null;
        while (next.left != null) {
            pre = next;
            next = next.left;
        }
        next.left = root.left;
        if (root.right != next) {
            pre.left = next.right;
            next.right = root.right;
        }
        return next;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode cur = root;
        TreeNode pre = null;
        // 寻找被删节点
        while (cur != null && cur.val != key) {
            pre = cur;
            if (key < cur.val) {
                cur = cur.left;
            } else if (key > cur.val) {
                cur = cur.right;
            }
        }

        // 代表被删节点为根节点
        if (pre == null) {
            return deleteRootNode(cur);
        }
        if (pre.left == cur) {
            pre.left = deleteRootNode(cur);
        } else {
            pre.right = deleteRootNode(cur);
        }
        return root;
    }
}

class Solution {
    // 更加简洁的递归版本
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        }
        // 找到被删节点
        else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            TreeNode minNode = root.right;
            while (minNode.left != null) {
                minNode = minNode.left;
            }
            root.val = minNode.val;
            // 用后继节点值替换被删节点值后，再在右子树中删除后继节点(此时值与被删节点相同)
            root.right = deleteNode(root.right, root.val);
        }
        return root;
    }
}