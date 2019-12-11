// 655. Print Binary Tree
class Solution {
    // 时间复杂度为 O(h * 2^h) = O(N)，空间复杂度也为 O(h * 2^h)，height = log2(n), width = 2^h - 1 = n / 2
    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> results = new ArrayList<>();
        int rows = getHeight(root);
        int cols = (int)(Math.pow(2, rows) - 1);
        for(int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();
            for(int j = 0; j < cols; j++) {
                row.add("");
            }
            results.add(row);
        }
        
        helper(results, root, 0, 0, cols);
        
        return results;
    }
    
    private void helper(List<List<String>> results, TreeNode root, int row, int left, int right) {
        if(root == null) {
            return;
        }
        
        results.get(row).set((left + right) / 2, Integer.toString(root.val));
        
        helper(results, root.left, row + 1, left, (left + right) / 2 - 1);
        helper(results, root.right, row + 1, (left + right) / 2 + 1, right);
    }
    
    private int getHeight(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
}

class Solution {
    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> results = new ArrayList<>();
        int rows = getHeight(root);
        int cols = (int) (Math.pow(2, rows) - 1);
        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add("");
            }
            results.add(row);
        }

        // 用 ArrayDeque 无法放入空节点
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<int[]> indexQueue = new LinkedList<>();
        queue.offer(root);
        indexQueue.offer(new int[] { 0, cols - 1 });
        int row = -1;
        while (!queue.isEmpty()) {
            row++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                int[] index = indexQueue.poll();
                if (node == null) {
                    continue;
                }

                int left = index[0];
                int right = index[1];
                results.get(row).set((left + right) / 2, Integer.toString(node.val));

                queue.offer(node.left);
                queue.offer(node.right);
                indexQueue.offer(new int[] { left, (left + right) / 2 - 1 });
                indexQueue.offer(new int[] { (left + right) / 2 + 1, right });
            }
        }

        return results;
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
}