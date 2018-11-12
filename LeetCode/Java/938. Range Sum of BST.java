// 938. Range Sum of BST
class Solution {
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }

        if (root.val >= L && root.val <= R) {
            return rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R) + root.val;
        } else if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        } else {
            return rangeSumBST(root.left, L, R);
        }
    }
}

class Solution {
    public int rangeSumBST(TreeNode root, int L, int R) {
        int sum = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();

            if (node.val >= L && node.val <= R) {
                sum += node.val;
            }

            if (node.left != null) {
                stack.push(node.left);
            }

            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return sum;
    }
}