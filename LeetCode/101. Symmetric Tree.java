// 101. Symmetric Tree
// 时间复杂度为 O(n)，空间复杂度为 O(n)，递归的栈最差情况是 n 个栈
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return dfs(root, root);
    }

    public boolean dfs(TreeNode t1, TreeNode t2) {
        // !!! this is root, avoid judge symmetric twice
        if (t1 == t2 && t1 != null) {
            return dfs(t1.left, t2.right);
        }
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }

        return (t1.val == t2.val) && dfs(t1.right, t2.left) && dfs(t1.left, t2.right);
    }
}

class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        // do not add root into queue
        // !!! ArrayDeque 不能添加空节点
        Queue<TreeNode> queue = new LinkedList<>();

        // start from second level
        queue.offer(root.left);
        queue.offer(root.right);
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            if (node1 == null && node2 == null) {
                continue;
            }
            if (node1 == null || node2 == null) {
                return false;
            }
            if (node1.val != node2.val) {
                return false;
            }
            queue.offer(node1.left);
            queue.offer(node2.right);
            queue.offer(node1.right);
            queue.offer(node2.left);
        }

        return true;
    }
}
