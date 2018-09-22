// 110. Balanced Binary Tree
// 一般有 bottom up 和 top down 两种思路，前者分治法就是以下代码，时间复杂度比后者低
class Solution {
    // 关键，辅助包装类，用于递归过程中返回不止一个参数
    private class auxiliary {
        public int maxHeight;
        public boolean isBalanced;

        public auxiliary(int maxHeight, boolean isBalanced) {
            this.maxHeight = maxHeight;
            this.isBalanced = isBalanced;
        }
    }

    // 递归过程中要返回两个参数，一个是子树高度，一个是子树是否平衡，虽然两个参数能合二为一都为int，但是会有二义性，可读性不好
    public boolean isBalanced(TreeNode root) {
        return helper(root).isBalanced;
    }

    private auxiliary helper(TreeNode root) {
        if (root == null) {
            auxiliary aux = new auxiliary(0, true);
            return aux;
        } 
        else {
            auxiliary left = helper(root.left);
            auxiliary right = helper(root.right);
            auxiliary aux = new auxiliary(0, true);
            if (left.isBalanced == false || right.isBalanced == false) {
                aux.isBalanced = false;
                return aux;
            } 
            else {
                aux.maxHeight = Math.max(left.maxHeight, right.maxHeight) + 1;
                if (Math.abs(left.maxHeight - right.maxHeight) > 1) {
                    aux.isBalanced = false;
                    return aux;
                }
                else {
                    return aux;
                }
            }
        }
    }
}