// 107. Binary Tree Level Order Traversal II
// 同102层次遍历，BFS，改动最后的res数组即可，要么尾部添加元素然后翻转，要么从头部添加元素
class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
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
            res.add(0, level);
        }

        return res;
    }
}

class Solution {
    // 改一下层次遍历的DFS版本
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, root, 0);
        return res;
    }

    public void dfs(List<List<Integer>> list, TreeNode root, int level) {
        if (root == null) {
            return;
        }
        // 如果当前的层次与二维数组大小一致，说明需要添加一个数组进去作为下一层
        if (level == list.size()) {
            // 从头部添加
            list.add(0, new ArrayList<Integer>());
        }

        // 放在分治两个dfs前面进行操作也行
        // 取当前属于层次的数组添加元素，注意顺序是倒的
        // list.get(list.size() - level - 1).add(root.val);

        dfs(list, root.left, level + 1);
        dfs(list, root.right, level + 1);

        // 取当前属于层次的数组添加元素，注意顺序是倒的
        list.get(list.size() - level - 1).add(root.val);
    }
}