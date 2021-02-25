// 257. Binary Tree Paths
// 时间复杂度为 O(n)
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        if (root.left == null && root.right == null) {
            res.add("" + root.val);
            return res;
        }

        List<String> left = binaryTreePaths(root.left);
        for (String str : left) {
            res.add("" + root.val + "->" + str);
        }

        List<String> right = binaryTreePaths(root.right);
        for (String str : right) {
            res.add("" + root.val + "->" + str);
        }

        return res;
    }
}

class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        StringBuilder strBuilder = new StringBuilder();
        helper(res, root, strBuilder);
        return res;
    }
    
    private void helper(List<String> res, TreeNode root, StringBuilder strBuilder) {
        if(root == null) {
            return;
        }
        int len = strBuilder.length();
        strBuilder.append(root.val);
        if(root.left == null && root.right == null) {
            res.add(strBuilder.toString());
        } else {
            strBuilder.append("->");
            helper(res, root.left, strBuilder);
            helper(res, root.right, strBuilder);
        }
        strBuilder.setLength(len);
    }
}
