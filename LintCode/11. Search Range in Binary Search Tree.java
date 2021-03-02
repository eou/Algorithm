// 11. Search Range in Binary Search Tree
// Non-recursive, inorder traversal
public class Solution {
    /**
     * @param root: param root: The root of the binary search tree
     * @param k1: An integer
     * @param k2: An integer
     * @return: return: Return all keys that k1<=key<=k2 in ascending order
     */
    public List<Integer> searchRange(TreeNode root, int k1, int k2) {
        List<Integer> res = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            if (node.val >= k1 && node.val <= k2) {
                res.add(node.val);
            }

            node = node.right;
        }

        return res;
    }
}

// DAC
public class Solution {
    /**
     * @param root: param root: The root of the binary search tree
     * @param k1:   An integer
     * @param k2:   An integer
     * @return: return: Return all keys that k1<=key<=k2 in ascending order
     */
    public List<Integer> searchRange(TreeNode root, int k1, int k2) {
        return dfs(root, k1, k2);
    }

    public List<Integer> dfs(TreeNode root, int k1, int k2) {
        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        List<Integer> left = dfs(root.left, k1, k2);
        List<Integer> right = dfs(root.right, k1, k2);

        res.addAll(left);
        if (root.val >= k1 && root.val <= k2) {
            res.add(root.val);
        }
        res.addAll(right);

        return res;
    }
}