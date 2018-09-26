// 94. Binary Tree Inorder Traversal
// 左 → 根 → 右
class Solution {
    // 递归版本，分治法
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (root != null) {
            list.addAll(inorderTraversal(root.left));
            list.add(root.val);
            list.addAll(inorderTraversal(root.right));
        }
        return list;
    }
}

class Solution {
    // 递归版本，遍历法
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        traverse(root, list);
        return list;
    }

    // 遍历的同时改变函数参数
    private void traverse(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        traverse(root.left, list);
        list.add(root.val);
        traverse(root.right, list);
    }
}

class Solution {
    // 非递归版本，熟读并背诵全文😈
    public List<Integer> inorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        // 推荐ArrayDeque类用作栈或队列
        // Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> list = new ArrayList<>();

        TreeNode node = root;
        // 思路大意：把二叉树分为左上斜，右下斜两个方向：沿着左下斜找到最左的一个节点需要一次循环，由于需要倒着遍历，使用栈；然后沿着右下方向找到下一个左下方向的循环，这需要另一个循环
        while (node != null || !stack.isEmpty()) {
            // 沿着左下
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            // 对节点进行相应操作
            list.add(node.val);

            // 转向右下节点
            node = node.right;
        }

        return list;
    }
}

class Solution {
    // 非递归版本另一个形式，本质跟以上非递归代码一样
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> list = new ArrayList<>();

        while (root != null) {
            stack.push(root);
            root = root.left;
        }

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            // 对节点进行相应操作
            list.add(node.val);

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

        return list;
    }
}

class Solution {
    // 非递归版本，morris traversal, 空间复杂度O(1)，不需要栈，其实是利用线索二叉树thread binary tree的特性
    // 虽然有两个while循环，但是时间复杂度仍然是O(n)
    // morris preorder 和 morris inorder 只有一行代码不同
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        // 需要两个节点指针
        TreeNode cur = root;
        TreeNode pre = null;

        while (cur != null) {
            if (cur.left == null) {
                list.add(cur.val);
                cur = cur.right;
            } else {
                // pre先进入cur左子树，然后向右一直遍历到底，也就是寻找根节点cur的前继节点
                pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    // 如果pre的右子树为空，则右指针就指向cur节点，换言之此时cur的前继节点找到了
                    pre.right = cur;
                    cur = cur.left;
                }
                // 如果pre右子树不为空，说明此时右子树指向前继节点
                else {
                    pre.right = null;
                    list.add(cur.val);
                    cur = cur.right;
                }
            }
        }

        return list;
    }
}