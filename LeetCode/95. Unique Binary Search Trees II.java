// 95. Unique Binary Search Trees II
class Solution {
    public List<TreeNode> generateTrees(int n) {
        // should return [], not [[]], but I think it is trivial
        if (n == 0) {
            return new ArrayList<>();
        }
        return dfs(1, n);
    }
    
    public List<TreeNode> dfs(int left, int right) {
        List<TreeNode> list = new ArrayList<>();
        if (left > right) {
            list.add(null); // must add null
            return list;
        }
        
        for (int i = left; i <= right; i++) {
            List<TreeNode> leftList = dfs(left, i - 1);
            List<TreeNode> rightList = dfs(i + 1, right);
            for (int j = 0; j < leftList.size(); j++) {
                for (int k = 0; k < rightList.size(); k++) {
                    TreeNode root = new TreeNode(i);
                    TreeNode leftTree = leftList.get(j);
                    TreeNode rightTree = rightList.get(k);
                    root.left = leftTree;
                    root.right = rightTree;
                    list.add(root);
                }
            }
        }
        
        return list;
    }
}