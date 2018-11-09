// 112. Path Sum
// 时间复杂度 O(n) ~ O(n^2)
class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) {
            return false;
        }
        
        if(root.left == null && root.right == null && sum == root.val) {
            return true;
        }
        
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }
}

class Solution {
    // 非递归版本，利用后序遍历
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) {
            return false;
        }
        
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        Deque<Integer> nums = new ArrayDeque<>();
        nums.push(root.val);
        
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int tmp = nums.pop();
            
            if(node.left == null && node.right == null) {
                if(tmp == sum) {
                    return true;
                }
            } else {
                if(node.left != null) {
                    stack.push(node.left);
                    nums.push(tmp + node.left.val);
                }
                if(node.right != null) {
                    stack.push(node.right);
                    nums.push(tmp + node.right.val);
                }
            }
        }
        
        return false;
    }
}

class Solution {
    // 非递归版本，利用后序遍历另一种形式
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode pre = null;
        TreeNode node = root;
        int num = 0;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                num += node.val;
                node = node.left;
            }
            node = stack.peek();
            if (node.left == null && node.right == null && num == sum) {
                return true;
            }
            if (node.right != null && pre != node.right) {
                node = node.right;
            } else {
                pre = node;
                stack.pop();
                num -= node.val;
                node = null;
            }
        }

        return false;
    }
}