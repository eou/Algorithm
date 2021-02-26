// 110. Balanced Binary Tree
// 2 values: subtree height, subtree balanced
// 一般有 bottom up 和 top down 两种思路，前者分治法就是以下代码，时间复杂度比后者低，分别为 O(n) 和 O(n^2)
class Solution {
    // 关键，辅助包装类，用于递归过程中返回不止一个参数
    class AuxType {
        int height;
        boolean isBalanced;

        AuxType(int height, boolean isBalanced) {
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }

    // 递归过程中要返回两个参数，一个是子树高度，一个是子树是否平衡，虽然两个参数能合二为一都为int，但是会有二义性，可读性不好
    public boolean isBalanced(TreeNode root) {
        return dfs(root).isBalanced;
    }

    private AuxType dfs(TreeNode root) {
        if (root == null) {
            return new AuxType(0, true);
        }

        AuxType left = dfs(root.left);
        AuxType right = dfs(root.right);

        AuxType aux = new AuxType(1 + Math.max(left.height, right.height), false);

        if (Math.abs(left.height - right.height) <= 1 && left.isBalanced && right.isBalanced) {
            aux.isBalanced = true;
        }

        return aux;
    }
}

class Solution {
    // bottom up 递归版本，在返回二叉树高度的同时维护一个全局变量
    private boolean isBalanced = true;
    public boolean isBalanced(TreeNode root) {
        getHeight(root);
        return isBalanced;
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getHeight(root.left);
        int right = getHeight(root.right);

        if (Math.abs(left - right) > 1) {
            isBalanced = false;
            return -1;
        }

        return Math.max(left, right) + 1;
    }
}

class Solution {
    // 上面的bottom up 版本也可以不维护全局变量
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getHeight(root.left);
        int right = getHeight(root.right);

        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }

        return Math.max(left, right) + 1;
    }
}

class Solution {
    // 根据定义写的 top down 递归，其中判断每棵子树是否平衡，不断重复计算树的高度导致时间复杂度为 O(n^2)
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        int left = getHeight(root.left);
        int right = getHeight(root.right);

        return Math.abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
}

class Solution {
    // 非递归版本，利用后序遍历特性：返回根节点时保证已经遍历完两课子树，所以可以比较子树的高度
    // 所有按照后序遍历的顺序的遍历都可以用于非递归版本的类似问题
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        Map<TreeNode, Integer> map = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if ((node.left == null || (node.left != null && map.containsKey(node.left)))
                    && (node.right == null || (node.right != null && map.containsKey(node.right)))) {
                // do sth.
                int left = map.getOrDefault(node.left, 0);
                int right = map.getOrDefault(node.right, 0);
                if (Math.abs(left - right) > 1) {
                    return false;
                } else {
                    map.put(node, Math.max(left, right) + 1);
                }
            } else {
                stack.push(node);
                if (node.left != null && !map.containsKey(node.left)) {
                    stack.push(node.left);
                } else {
                    stack.push(node.right);
                }
            }
        }

        return true;
    }
}

class Solution {
    // 非递归版本，利用后序遍历另一个版本，当然还有别的写法
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        Map<TreeNode, Integer> map = new HashMap<>();
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
                node = stack.pop();
                // do sth.
                int left = map.getOrDefault(node.left, 0);
                int right = map.getOrDefault(node.right, 0);
                if (Math.abs(left - right) > 1) {
                    return false;
                } else {
                    map.put(node, Math.max(left, right) + 1);
                }
                node = null;
            }
        }

        return true;
    }
}