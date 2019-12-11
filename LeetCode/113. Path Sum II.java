// 113. Path Sum II
class Solution {
    // DFS，时间复杂度为 O(n)
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        pathSum(root, sum, new ArrayList<>(), results);
        return results;
    }

    public void pathSum(TreeNode root, int sum, List<Integer> list, List<List<Integer>> results){
        if (root == null){
            return; 
        }
        
        list.add(root.val);
        if (root.left == null && root.right == null && root.val == sum){
            results.add(new ArrayList(list));
        } else {
            pathSum(root.left, sum - root.val, list, results);
            pathSum(root.right, sum - root.val, list, results);
        }
        list.remove(list.size()-1);
    }
}

class Solution {
    // 非递归版本，利用后序遍历，类似 112. Path Sum
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode pre = null;
        TreeNode node = root;
        int num = 0;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                num += node.val;
                list.add(node.val);
                node = node.left;
            }
            node = stack.peek();
            if (node.left == null && node.right == null && num == sum) {
                results.add(new ArrayList<>(list));
            }
            if (node.right != null && pre != node.right) {
                node = node.right;
            } else {
                pre = node;
                stack.pop();
                list.remove(list.size() - 1);
                num -= node.val;
                node = null;
            }
        }

        return results;
    }
}