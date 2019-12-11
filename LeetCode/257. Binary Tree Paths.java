// 257. Binary Tree Paths
// 时间复杂度为 O(n)
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        if (root.left == null && root.right == null) {
            String tmp = new String(root.val + "");
            result.add(tmp);
            return result;
        }
        List<String> left = binaryTreePaths(root.left);
        List<String> right = binaryTreePaths(root.right);
        for (String s : left) {
            String tmp = new String(s);
            tmp = root.val + "->" + tmp;
            result.add(tmp);
        }
        for (String s : right) {
            String tmp = new String(s);
            tmp = root.val + "->" + tmp;
            result.add(tmp);
        }
        return result;
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
