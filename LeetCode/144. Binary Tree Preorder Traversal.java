// 144. Binary Tree Preorder Traversal
// root → left → right
// 2 Recursive + 3 Non-recursive (morris traversal)
// DAC vs Traverse. Traverse needs a global variable or uses parameter to pass value.
class Solution {
    // Divide and conquer
    // Return the result by return value
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        res.add(root.val);
        res.addAll(preorderTraversal(root.left));
        res.addAll(preorderTraversal(root.right));
        return res;
    }
}

class Solution {
    // Traverse
    // Return the result by parameters or global variables
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        traverse(root, res);
        return res;
    }

    private void traverse(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        res.add(root.val);
        traverse(root.left, res);
        traverse(root.right, res);
    }
}

class Solution {
    // Non-recursive，熟读并背诵全文😈
    // Using stack. Every loop deals with the top element of stack.
    // root => push right child into stack => push left child into stack => pop left child
    public List<Integer> preorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            // 弹出节点并进行相应操作
            TreeNode node = stack.pop();
            res.add(node.val);

            // 然后分别打入右节点和左节点
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.2
    // Similar with 1 solution of Postorder traversal.
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        
        if (root == null) {
            return res;
        }

        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                res.add(node.val);
                stack.push(node);
                node = node.left;
            } else {
                // Condition is !stack.isEmpty() here
                node = stack.pop();
                node = node.right;
            }
        }

        return res;

    }
}

class Solution {
    // Non-recursive Ver.2
    // Using node pointer. Every loop deals with the node pointed with node pointer.
    // root => push right child into stack => switch to left child, if not exists, pop right child
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // 只有右节点被放入栈中
        Deque<TreeNode> stack = new ArrayDeque<>();

        TreeNode node = root;
        while (node != null) {
            // 首先对节点进行相应操作
            res.add(node.val);

            // 若存在右节点，打入栈中备用
            if (node.right != null) {
                stack.push(node.right);
            }

            node = node.left;

            // 若没有左节点，取栈中右节点
            if (node == null && !stack.isEmpty()) {
                node = stack.pop();
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.3, morris traversal, 空间复杂度O(1)，不需要栈，其实是利用线索二叉树 thread binary tree 的特性
    // 虽然有两个 while 循环，但是时间复杂度仍然是O(n)
    // 要使用 O(1) 空间进行遍历，最大的难点在于，遍历到子节点的时候怎样重新返回到父节点（假设节点中没有指向父节点的指针）
    // morris traversal 利用叶子节点中的左右空指针指向某种顺序遍历下的前驱节点或后继节点
    // morris preorder 和 morris inorder 只有一行代码不同
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root;
        TreeNode pre = null;

        if (root == null) {
            return res;
        }

        while (cur != null) {
            if (cur.left == null) {
                res.add(cur.val);
                cur = cur.right;
            } else {
                pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
                if (pre.right == null) {
                    res.add(cur.val);
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    cur = cur.right;
                }
            }
        }

        return res;
    }
}