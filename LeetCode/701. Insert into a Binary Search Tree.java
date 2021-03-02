// 701. Insert into a Binary Search Tree
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Find the first greater node.
        // The valid insert position could be in the upper tree (parent) or the lower tree (child).
        // Easiest way is to insert it in the lower tree.
        TreeNode target = new TreeNode(val);
        if (root == null) {
            return target;
        }

        TreeNode node = root, pre = null;
        while (node != null) {
            pre = node;
            if (node.val < val) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        // Do sth. around this node.
        node = pre;
        if (node.val < val) {
            // Insert the biggest node in the tree.
            pre = node;
            node = node.right;
            if (node == null) {
                pre.right = target;
                return root;
            }
        }

        // Insert as the most left child.
        while (node != null) {
            pre = node;
            node = node.left;
        }

        pre.left = target;

        return root;
    }
}

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
                    return root; // 可以改成 break;
                }
                // 如果没空节点位置，就继续往下走
                node = node.right;
            } else if (node.val > val) {
                if (node.left == null) {
                    node.left = newNode;
                    return root; // 可以改成 break;
                }
                // 如果没空节点位置，就继续往下走
                node = node.left;
            } else {
                // 如已经有了节点，就不用插入直接返回
                return root; // 可以改成 break;
            }
        }
        // 其实这个return没用，但是为了编译通过要加上，解决这个尴尬的问题就是让前面的 return root; 全改为 break; 这样最后的 return
        // root; 就有用了
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
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }
}