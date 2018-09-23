// 173. Binary Search Tree Iterator
// 整体思路就是把中序遍历非递归思路拆解成几个函数
// 题目要求 next() and hasNext() queries run in O(1) time IN AVERAGE; Extra memory usage O(h), h is the height of the tree
// 可以达到Extra memory usage O(1)，显然就是morris inorder了
public class BSTIterator {
    // 非递归遍历的常见版本
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode node = null;

    public BSTIterator(TreeNode root) {
        node = root;
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        // 这就是非递归中序遍历的while循环条件
        return node != null || !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
        TreeNode res = stack.pop();
        node = res.right;
        return res.val;
    }
}

public class BSTIterator {
    // morris inorder版本，时空复杂度O(1)
    TreeNode cur = null;
    TreeNode pre = null;
    int res;

    public BSTIterator(TreeNode root) {
        cur = root;
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return cur != null;
    }

    /** @return the next smallest number */
    public int next() {
        // 看上去有while循环，其实每个点只循环一两次
        while (cur != null) {
            if (cur.left == null) {
                res = cur.val;
                cur = cur.right;
                return res;
            } 
            else {
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
                    res = cur.val;
                    cur = cur.right;
                    return res;
                }
            }
        }
        return res;
    }
}

public class BSTIterator {
    // 第一个非递归版本的变形，
    private Deque<TreeNode> stack = new ArrayDeque<>();

    public BSTIterator(TreeNode root) {
        // 初始化时就直接找第一个节点
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        // 如果当前点存在右子树，那么就是右子树中最左边的那个点
        // 如果当前点不存在右子树，则是走到当前点的路径中，第一个左拐的点
        // 这里是提前找到下下一个节点
        TreeNode cur = stack.peek();
        // cur节点已经在上一次 next() 中找到了
        TreeNode node = cur;

        if (node.right == null) {
            node = stack.pop();
            while (!stack.isEmpty() && stack.peek().right == node) {
                node = stack.pop();
            }
        }
        else {
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        return cur;
    }
}