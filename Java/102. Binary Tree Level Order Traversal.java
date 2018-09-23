// 102. Binary Tree Level Order Traversal
class Solution {
    // 层次遍历模板，也就是BFS
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) {
            return results;
        }
        
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 注意 size 要先在for循环外面取得
            int size = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            results.add(level);
        }

        return results;
    }
}