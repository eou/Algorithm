// 236. Lowest Common Ancestor of a Binary Tree
class Solution {
    // 此解法虽然满足题意，但不严格，如果一个节点在树中，另一个不在，也会返回其中一个节点而不是null
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 当到达叶子节点返回 null 表明找不到这两个节点任何一个
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 思路就是反过来想，哪一个root里面同时包含p和q两个节点，然后一层层递归往上面传递
        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        } else {
            return null;
        }
    }
}

class Solution {
    // 更严格的解法，如果需要正确判断节点是否在树中，需要包装一个辅助类返回存在与否的参数
    private class auxiliary {
        public boolean a_exist, b_exist;
        public TreeNode lca;

        public auxiliary(boolean a_exist, boolean b_exist, TreeNode lca) {
            this.a_exist = a_exist;
            this.b_exist = b_exist;
            this.lca = lca;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        auxiliary aux = helper(root, p, q);
        if (aux.a_exist && aux.b_exist) {
            return aux.lca;
        } else {
            return null;
        }
    }

    public auxiliary helper(TreeNode root, TreeNode p, TreeNode q) {
        auxiliary aux = new auxiliary(false, false, null);
        if (root == null) {
            return aux;
        }

        auxiliary left = helper(root.left, p, q);
        auxiliary right = helper(root.right, p, q);

        // 判断两个节点存在与否
        aux.a_exist = left.a_exist || right.a_exist || root == p;
        aux.b_exist = left.b_exist || right.b_exist || root == q;

        // 找到两个节点本身
        if (root == p || root == q) {
            aux.lca = root;
            return aux;
        }

        // 左右两边子树都存在目标节点，说明找到最近祖先
        if (left.lca != null && right.lca != null) {
            aux.lca = root;
            return aux;
        }

        // 找到一个节点就把其本身逐层上传
        if (left.lca != null) {
            aux.lca = left.lca;
            return aux;
        }

        if (right.lca != null) {
            aux.lca = right.lca;
            return aux;
        }

        return aux;
    }
}
