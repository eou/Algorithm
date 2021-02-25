// 94. Binary Tree Inorder Traversal
// left → root → right
// 2 Recursive + 3 Non-recursive (morris traversal)
class Solution {
    // Divide and conquer
    // return the result by return value
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        res.addAll(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        return res;
    }
}

class Solution {
    // Traverse
    // return the result by parameters
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        traverse(root, res);
        return res;
    }

    private void traverse(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        traverse(root.left, res);
        res.add(root.val);
        traverse(root.right, res);
    }
}

class Solution {
    // Non-recursive，熟读并背诵全文😈
    public List<Integer> inorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();

        TreeNode node = root;
        // 思路：把二叉树分为左上斜，右下斜两个方向。
        // 沿着左下斜找到最左的一个节点需要一次循环，由于需要倒着遍历，使用栈；然后沿着右下方向找到下一个左下方向的循环，这需要另一个循环
        while (node != null || !stack.isEmpty()) {
            // 对于当前节点，一直沿着左下
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            // 对节点进行相应操作
            res.add(node.val);

            // 转向右下节点
            node = node.right;
        }

        return res;
    }
}

class Solution {
    // Non-recursive，本质跟以上非递归代码一样
    public List<Integer> inorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();

        while (root != null) {
            stack.push(root);
            root = root.left;
        }

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            // 对节点进行相应操作
            res.add(node.val);

            if (node.right == null) {
                node = stack.pop();
                while (!stack.isEmpty() && stack.peek().right == node) {
                    node = stack.pop();
                }
            } else {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

        if (root == null) {
            return res;
        }

        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                res.add(node.val);
                node = node.right;
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive，morris traversal, 空间复杂度 O(1)，不需要栈，其实是利用线索二叉树 thread binary tree 的特性
    // 虽然有两个 while 循环，但是时间复杂度仍然是 O(n)
    // 要使用 O(1) 空间进行遍历，最大的难点在于，遍历到子节点的时候怎样重新返回到父节点（假设节点中没有指向父节点的指针）
    // morris traversal 利用叶子节点中的左右空指针指向某种顺序遍历下的前驱节点或后继节点
    // morris preorder 和 morris inorder 只有一行代码不同
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        // 需要两个节点指针
        TreeNode cur = root;
        TreeNode pre = null;

        while (cur != null) {
            if (cur.left == null) {
                // 左子树为空就直接添加根节点，然后进入右子树
                res.add(cur.val);
                cur = cur.right;
            } else {
                // pre 先进入 cur 左子树，然后向右一直遍历到底，也就是寻找根节点 cur 的前继节点
                pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    // 如果 pre 的右子树为空，则右指针就指向 cur 节点，换言之此时 cur 的前继节点找到了
                    pre.right = cur;
                    cur = cur.left;
                }
                // 如果 pre 右子树不为空，说明此时右子树指向前继节点
                else {
                    pre.right = null;
                    res.add(cur.val);
                    cur = cur.right;
                }
            }
        }

        return res;
    }
}