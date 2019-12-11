// 145. Binary Tree Postorder Traversal
// 左 → 右 → 根
class Solution {
    // 递归版本，分治法
    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();

        if (root != null) {
            list.addAll(postorderTraversal(root.left));
            list.addAll(postorderTraversal(root.right));
            list.add(root.val);
        }

        return list;
    }
}

class Solution {
    // 递归版本，遍历法
    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        traverse(root, list);
        return list;
    }

    private void traverse(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        traverse(root.left, list);
        traverse(root.right, list);
        list.add(root.val);
    }
}

class Solution {
    // 非递归版本，熟读并背诵全文😈
    public List<Integer> postorderTraversal(TreeNode root) {
        // 注意这里是LinkedList，要用到addFirst，或者ArrayList在头部插入，或者最后翻转
        LinkedList<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) {
            return list;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            // 跟前序遍历类似，不过是先在头部加入根，然后左节点和右节点，即左 → 右 → 根 → 头部
            TreeNode node = stack.pop();
            list.addFirst(node.val);

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return list;
    }
}

class Solution {
    // 非递归版本另一个形式，不是在数组头部插入元素或者最后翻转数组，但是需要一个prev节点
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        // 一个当前访问的节点，一个刚才访问的节点
        TreeNode prev = null;
        TreeNode curr = root;

        stack.push(root);
        while (!stack.empty()) {
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
                results.add(curr.val);
                stack.pop();
            }
            prev = curr;
        }

        return results;
    }
}

class Solution {
    // pre 节点与栈的另一个版本，跟中序遍历类似，因为遍历每一棵树都要先找到最左边的结点
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
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
                results.add(stack.pop().val);
                node = null;
            }
        }

        return results;
    }
}

class Solution {
    // 用 set 保存左右结点是否访问过
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        Set<TreeNode> visited = new HashSet<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if ((node.left == null || (node.left != null && visited.contains(node.left)))
                    && (node.right == null || (node.right != null && visited.contains(node.right)))) {
                results.add(node.val);
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

        return results;
    }
}

class Solution {
    // 非递归版本，morris traversal, 空间复杂度O(1)，不需要栈，其实是利用线索二叉树thread binary tree的特性
    // 虽然有两个while循环，但是时间复杂度仍然是O(n)
    // morris postorder比较复杂
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> list = new LinkedList();
        if (root == null) {
            return list;
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
                    addReverse(node.left, p, list);
                    p.right = null;
                    node = node.right;
                }
            }
        }

        return list;
    }

    // 访问逆转后的路径上的所有节点
    private void addReverse(TreeNode from, TreeNode to, List<Integer> list) {
        reverse(from, to);
        TreeNode p = to;
        while (true) {
            list.add(p.val);
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
