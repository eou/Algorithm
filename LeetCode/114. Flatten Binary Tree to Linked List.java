// 114. Flatten Binary Tree to Linked List
// 1 value: last node of subtree
// DAC
class Solution {
    // 其实不需要这个辅助类，因为first不需要通过类返回
    // first 可以通过 root 辅助找到, last 不可以
    // 单独返回一个 last 只需通过单个 TreeNode 即可
    private class AuxType {
        TreeNode first, last;

        public AuxType(TreeNode first, TreeNode last) {
            this.first = first;
            this.last = last;
        }
    }

    public void flatten(TreeNode root) {
        root = helper(root).first;
    }

    public AuxType helper(TreeNode root) {
        if (root == null) {
            return new AuxType(null, null);
        }
        
        AuxType left = helper(root.left);
        AuxType right = helper(root.right);
        TreeNode node = root;
        if (left.first != null && right.first != null) {
            left.last.right = right.first;
            node.right = left.first;
            node.left = null; // 注意要断开左子树
            return new AuxType(node, right.last);
        } else if (left.first != null) {
            node.right = left.first;
            node.left = null; // 注意要断开左子树
            return new AuxType(node, left.last);
        } else if (right.first != null) {
            node.right = right.first;
            // 此处node肯定无left子树
            return new AuxType(node, right.last);
        } else {
            return new AuxType(node, node);
        }
    }
}

// DAC
class Solution {
    // 上一种方法的简化版本，只需返回last节点
    public void flatten(TreeNode root) {
        dfs(root);
    }

    public TreeNode dfs(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode leftLast = dfs(root.left);
        TreeNode rightLast = dfs(root.right);

        if (leftLast != null && rightLast != null) {
            leftLast.right = root.right; // right head
            root.right = root.left; // left head
            root.left = null;
            return rightLast;
        } else if (leftLast != null) {
            root.right = root.left; // left head
            root.left = null;
            return leftLast;
        } else if (rightLast != null) {
            // Unnecessary!
            // root.right = root.right;  Duplicate code.
            // root.left = null;  No left child here.
            return rightLast;
        } else {
            return root;
        }
    }
}

// DAC vs traversal: 
// DAC doesn't care the order of processing left or right child. They are independent.
// Traversal does since it must follow some order to visit the subtree.
// Reverse preorder traversal, use parameter
class Solution {
    public void flatten(TreeNode root) {
        flatten(root, null);
    }

    private TreeNode flatten(TreeNode root, TreeNode head) {
        if (root == null) {
            return head;
        }

        head = flatten(root.right, head);
        head = flatten(root.left, head);

        root.right = head;
        root.left = null;

        head = root;
        return head;
    }
}

// Reverse preorder traversal, use global variable
class Solution {
    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
            
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }
}

// Preorder traversal
class Solution {
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode pre = null, cur = root;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            cur = stack.pop();

            if (pre != null) {
                pre.right = cur;
                pre.left = null;
            }

            pre = cur;

            if (cur.right != null) {
                stack.push(cur.right);
            }

            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }
}

class Solution {
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