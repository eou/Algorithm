// 298. Binary Tree Longest Consecutive Sequence
class Solution {
    public int res = 0;
    public int longestConsecutive(TreeNode root) {
        dfs(root);
        return res;
    }
    
    // return the length of consecutive sequence start from root
    public int dfs(TreeNode root) {
        int longest = 1;
        if (root == null) {
            longest = 0;
            res = Math.max(res, longest);
            return longest;
        }
        
        if (root.left == null && root.right == null) {
            longest = 1;
            res = Math.max(res, longest);
            return longest;
        }
        
        if (root.left != null) {
            int leftLongest = dfs(root.left);
            if (root.val == root.left.val - 1) {
                longest = Math.max(longest, leftLongest + 1);
            }
        }
        
        if (root.right != null) {
            int rightLongest = dfs(root.right);
            if (root.val == root.right.val - 1) {
                longest = Math.max(longest, rightLongest + 1);
            }
        }
        
        res = Math.max(res, longest);
        return longest;
    }
}

class Solution {
    public int res = 0;
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        helper(root, 0, root.val);
        return res;
    }

    public void helper(TreeNode root, int cur, int target) {
        if (root == null) {
            return;
        }

        if (root.val == target) {
            cur++;
        } else {
            cur = 1;
        }
        res = Math.max(cur, res);
        helper(root.left, cur, root.val + 1);
        helper(root.right, cur, root.val + 1);
    }
}