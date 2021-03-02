// 450. Delete Node in a BST
// 一般是用 Hibbard deletion algorithm
// Hibbard deletion algorithm, Non-recursive
class Solution {
    public TreeNode deleteNode(TreeNode root, int value) {
        TreeNode ptr = root, parent = null;
        boolean leftNode = false;
        // Find the target
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

        // Remove root
        if (parent == null) {
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

// Hibbard deletion algorithm, recursive
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode cur = root, pre = null;
        // Find target node
        while (cur != null && cur.val != key) {
            pre = cur;
            if (key < cur.val) {
                cur = cur.left;
            } else if (key > cur.val) {
                cur = cur.right;
            }
        }

        // Delete target node
        if (pre == null) {
            return deleteRootNode(cur);
        } else if (pre.left == cur) {
            pre.left = deleteRootNode(cur);
        } else {
            pre.right = deleteRootNode(cur);
        }

        return root;
    }

    private TreeNode deleteRootNode(TreeNode root) {
        if (root == null) {
            return null;
        }

        if (root.left != null && root.right != null) {
            // Move the left child to the right child's most left position
            TreeNode ptr = root.right, pre = null;
            while (ptr.left != null) {
                pre = ptr;
                ptr = ptr.left;
            }
            ptr.left = root.left;

            // Do some balanced operations.
            if (pre != null) {
                pre.left = ptr.right;
                ptr.right = root.right;
            }
            return ptr;

            // We can return root.right; here without any balancing.
        }

        if (root.right != null) {
            return root.right;
        }

        if (root.left != null) {
            return root.left;
        }
    }
}

// Recursion
class Solution {
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

// Recursion
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }

        if (root.val == key) {
            if (root.left != null && root.right != null) {
                // Move the left child to its right child's most left child
                TreeNode ptr = root.right, pre = null;
                while (ptr != null) {
                    pre = ptr;
                    ptr = ptr.left;
                }

                pre.left = root.left;
                return root.right;
            } else if (root.left != null) {
                return root.left;
            } else {
                return root.right;
            }
        } else if (root.left != null && root.left.val == key) {
            TreeNode left = deleteNode(root.left, key);
            root.left = left;
            return root;
        } else if (root.right != null && root.right.val == key) {
            TreeNode right = deleteNode(root.right, key);
            root.right = right;
            return root;
        } else if (root.val < key) {
            deleteNode(root.right, key);
            return root;
        } else {
            deleteNode(root.left, key);
            return root;
        }
    }
}