// 314. Binary Tree Vertical Order Traversal
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if(root == null) {
            return results;
        }
        
        // map<col, list<val>>
        Map<Integer, List<Integer>> map = new HashMap<>();
        // declare two queue to store the TreeNode and the col
        Queue<TreeNode> queue = new ArrayDeque<>();
        Queue<Integer> cols = new ArrayDeque<>();
        queue.add(root);
        cols.add(0);
        
        // the leftmost position
        int left = 0;
        // the rightmost position
        int right = 0;
        // level order traversal
        while(!queue.isEmpty()) {
            TreeNode tmp = queue.poll();
            int col = cols.poll();
            
            if(!map.containsKey(col)) {
                map.put(col, new ArrayList<Integer>());
            }
            map.get(col).add(tmp.val);
            
            if(tmp.left != null) {
                queue.offer(tmp.left);
                cols.offer(col - 1);
                left = Math.min(left, col - 1);
            }
            if(tmp.right != null) {
                queue.offer(tmp.right);
                cols.offer(col + 1);
                right = Math.max(right, col + 1);
            }
        }
        
        for(int i = left; i <= right; i++) {
            results.add(map.get(i));
        }
        
        return results;
    }
}