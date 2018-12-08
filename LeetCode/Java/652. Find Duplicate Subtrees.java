// 652. Find Duplicate Subtrees
class Solution {
    // 时间复杂度为 O(n^2)
    Map<String, Integer> map;
    List<TreeNode> results;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        map = new HashMap<>();
        results = new ArrayList<>();
        
        helper(results, root);
        return results;
    }
    
    private String helper(List<TreeNode> results, TreeNode root) {
        if(root == null) {
            return "#";
        }
        
        String serialize = root.val + "," + helper(results, root.left) + "," + helper(results, root.right);
        map.put(serialize, map.getOrDefault(serialize, 0) + 1);
        if(map.get(serialize) == 2) {
            results.add(root);
        }
        
        return serialize;
    }
}

class Solution {
    int id;
    Map<Integer, Integer> map;
    Map<String, Integer> uniq;
    List<TreeNode> results;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        id = 1;
        map = new HashMap<>();
        uniq = new HashMap<>();
        results = new ArrayList<>();

        helper(results, root);
        return results;
    }

    private int helper(List<TreeNode> results, TreeNode root) {
        if (root == null) {
            return 0;
        }

        String serialize = root.val + "," + helper(results, root.left) + "," + helper(results, root.right);
        int uniqId = uniq.computeIfAbsent(serialize, x -> id++);
        map.put(uniqId, map.getOrDefault(uniqId, 0) + 1);
        if (map.get(uniqId) == 2) {
            results.add(root);
        }

        return uniqId;
    }
}