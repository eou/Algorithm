// 145. Binary Tree Postorder Traversal
// left → right → root
// 2 Recursive + 6 Non-recursive (morris traversal)
class Solution {
    // Divide and conquer
    // return the result by return value
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        res.addAll(postorderTraversal(root.left));
        res.addAll(postorderTraversal(root.right));
        res.add(root.val);

        return res;
    }
}

class Solution {
    // Traverse
    // return the result by parameters
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        traverse(root, res);
        return res;
    }

    private void traverse(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        traverse(root.left, res);
        traverse(root.right, res);
        res.add(root.val);
    }
}

class Solution {
    // Non-recursive，熟读并背诵全文😈
    public List<Integer> postorderTraversal(TreeNode root) {
        // 注意这里是 LinkedList，要用到 addFirst，或者 ArrayList 在头部插入，或者最后翻转，但性能低
        LinkedList<Integer> res = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root == null) {
            return res;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            // 跟前序遍历类似，不过是反序加入链表头部，根 → 右 → 左
            TreeNode node = stack.pop();
            res.addFirst(node.val);

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.2，需要一个prev节点
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();

        TreeNode prev = null;
        TreeNode curr = root;

        stack.push(root);
        while (!stack.isEmpty()) {
            curr = stack.peek();
            if (prev == null || prev.left == curr || prev.right == curr) {
                if (curr.left != null) {
                    stack.push(curr.left);
                } else if (curr.right != null) {
                    stack.push(curr.right);
                }
            } else if (curr.left == prev) {
                if (curr.right != null) {
                    stack.push(curr.right);
                }
            } else {
                res.add(curr.val);
                stack.pop();
            }
            prev = curr;
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.3, pre 节点与栈的另一个版本，跟中序遍历类似，因为遍历每一棵树都要先找到最左边的结点
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode pre = null;
        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.peek();
            if (node.right != null && pre != node.right) {
                // pre != node.right 说明右子树尚未访问
                node = node.right;
            } else {
                // 右子树已经遍历过或者没有右子树
                pre = node;
                res.add(stack.pop().val);
                node = null;
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.4, double stack
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        // 倒序的节点栈
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> stack = new ArrayDeque<>();

        TreeNode node = root;

        while (node != null || !nodeStack.isEmpty()) {
            while (node != null) {
                stack.push(node.val);
                nodeStack.push(node);
                node = node.right;
            }

            if (!nodeStack.isEmpty()) {
                node = nodeStack.pop();
                node = node.left;
            }
        }

        while (!stack.isEmpty()) {
            res.add(stack.pop()); // 获取倒序的根右左序列
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.5, 用 set 保存左右结点是否访问过
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Set<TreeNode> visited = new HashSet<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if ((node.left == null || (node.left != null && visited.contains(node.left)))
                    && (node.right == null || (node.right != null && visited.contains(node.right)))) {
                res.add(node.val);
                visited.add(node);
            } else {
                stack.push(node);
                if (node.left != null && !visited.contains(node.left)) {
                    stack.push(node.left);
                } else {
                    stack.push(node.right);
                }
            }
        }

        return res;
    }
}

class Solution {
    // Non-recursive Ver.6，morris traversal, 空间复杂度O(1)，不需要栈，其实是利用线索二叉树thread binary tree的特性
    // 虽然有两个while循环，但是时间复杂度仍然是O(n)
    // morris postorder 比较复杂
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> res = new LinkedList();
        if (root == null) {
            return res;
        }

        // 需要一个新节点
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        TreeNode node = dummy;

        while (node != null) {
            if (node.left == null) {
                node = node.right;
            } else {
                TreeNode p = node.left;
                while (p.right != null && p.right != node) {
                    p = p.right;
                }
                if (p.right == null) {
                    p.right = node;
                    node = node.left;
                } else {
                    addReverse(node.left, p, res);
                    p.right = null;
                    node = node.right;
                }
            }
        }

        return res;
    }

    // 访问逆转后的路径上的所有节点
    private void addReverse(TreeNode from, TreeNode to, List<Integer> res) {
        reverse(from, to);
        TreeNode p = to;
        while (true) {
            res.add(p.val);
            if (p == from) {
                break;
            }
            p = p.right;
        }
        reverse(to, from);
    }

    // 逆转路径
    private void reverse(TreeNode from, TreeNode to) {
        if (from == to) {
            return;
        }
        TreeNode p = from;
        while (p != to) {
            TreeNode next = p.right;
            p.right = to.right;
            to.right = p;
            p = next;
        }
    }
}
