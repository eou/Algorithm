// 450. Delete Node in a BST
// 一般是用 Hibbard deletion algorithm
class Solution {
    // Hibbard deletion algorithm 非递归删除
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }

        TreeNode node = root;
        TreeNode pre = new TreeNode(0);
        // 标记被删节点是前继节点的左节点还是右节点
        boolean _left = false;
        // 寻找被删节点
        while (node != null) {
            if (node.val == key) {
                break;
            } else if (node.val > key) {
                pre = node;
                node = node.left;
            } else {
                pre = node;
                node = node.right;
            }
        }

        if (node == null) {
            return root;
        } else {
            if (pre.left == node) {
                _left = true;
            } else {
                _left = false;
            }

            // 注意基本每种情况都要考虑被删节点是根节点的特殊情况
            if (node.left == null && node.right == null) {
                if (node == root) {
                    return null;
                } else {
                    if (_left == true) {
                        pre.left = null;
                    } else {
                        pre.right = null;
                    }
                }
            } else if (node.left == null) {
                if (_left == true) {
                    pre.left = node.right;
                    if (node == root) {
                        return pre.left;
                    }
                } else {
                    pre.right = node.right;
                    if (node == root) {
                        return pre.right;
                    }
                }
            } else if (node.right == null) {
                if (_left == true) {
                    pre.left = node.left;
                    if (node == root) {
                        return pre.left;
                    }
                } else {
                    pre.right = node.left;
                    if (node == root) {
                        return pre.right;
                    }
                }
            } else {
                // 如果被删节点的左右子树均存在，用后继节点替换被删节点，然后删除后继节点，后继节点肯定是右子树或者左子树叶子
                TreeNode succ = node.right;
                TreeNode _succ = node;
                while (succ.left != null) {
                    _succ = succ;
                    succ = succ.left;
                }
                node.val = succ.val;
                if (_succ != node) {
                    _succ.left = succ.right;
                } else {
                    node.right = succ.right;
                }
            }
            return root;
        }
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