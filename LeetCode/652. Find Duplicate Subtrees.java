// 652. Find Duplicate Subtrees
// 时间复杂度为 O(n^2), N nodes, serialization needs N time
class Solution {
    Map<String, Integer> map;
    List<TreeNode> res;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        map = new HashMap<>();
        res = new ArrayList<>();
        
        dfs(res, root);
        return res;
    }
    
    private String dfs(List<TreeNode> res, TreeNode root) {
        if(root == null) {
            return "#";
        }
        
        String serialize = root.val + "," + dfs(res, root.left) + "," + dfs(res, root.right);
        map.put(serialize, map.getOrDefault(serialize, 0) + 1);

        // only add once
        if(map.get(serialize) == 2) {
            res.add(root);
        }
        
        return serialize;
    }
}

// O(N), use id instead of serialization
class Solution {
    // global id
    int id;
    // id => frequency
    Map<Integer, Integer> map;
    // tree serialization => id
    Map<String, Integer> uniq;
    List<TreeNode> res;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        id = 1;
        map = new HashMap<>();
        uniq = new HashMap<>();
        res = new ArrayList<>();

        dfs(res, root);
        return res;
    }

    private int dfs(List<TreeNode> res, TreeNode root) {
        if (root == null) {
            return 0;
        }

        String serialize = root.val + "," + dfs(res, root.left) + "," + dfs(res, root.right);

        int uniqId = uniq.getOrDefault(serialize, id++);
        uniq.put(serialize, uniqId);
        // int uniqId = uniq.computeIfAbsent(serialize, x -> id++);
        map.put(uniqId, map.getOrDefault(uniqId, 0) + 1);
        if (map.get(uniqId) == 2) {
            res.add(root);
        }

        return uniqId;
    }
}