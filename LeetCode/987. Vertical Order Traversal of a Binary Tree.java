// 987. Vertical Order Traversal of a Binary Tree
// 跟 314. Binary Tree Vertical Order Traversal 不同之处在于 If two nodes are in the same row and column, the order should be from left to right.
// 而此题是同行同列的从大到小排列
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        // three sort, vertical, horizontal, duplicate position
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>(); // vertical level => (horizontal level => nodes)
        dfs(root, 0, 0, map);

        for (TreeMap<Integer, PriorityQueue<Integer>> m : map.values()) {
            results.add(new ArrayList<>());
            for (PriorityQueue<Integer> pq : m.values()) {
                while (!pq.isEmpty()) {
                    results.get(results.size() - 1).add(pq.poll());
                }
            }
        }

        return results;
    }

    public void dfs(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map) {
        if (root == null) {
            return;
        }

        if (!map.containsKey(x)) {
            map.put(x, new TreeMap<>());
        }

        if (!map.get(x).containsKey(y)) {
            map.get(x).put(y, new PriorityQueue<>());
        }

        map.get(x).get(y).add(root.val);
        dfs(root.left, x - 1, y + 1, map);
        dfs(root.right, x + 1, y + 1, map);
    }
}