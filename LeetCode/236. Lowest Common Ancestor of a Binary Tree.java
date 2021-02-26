// 236. Lowest Common Ancestor of a Binary Tree
// 3 values: lca, if p exists in subtree, if q exists in subtree
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
    class AuxType {
        boolean pExists, qExists;
        TreeNode lca;

        AuxType(boolean pExists, boolean qExists, TreeNode lca) {
            this.pExists = pExists;
            this.qExists = qExists;
            this.lca = lca;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return dfs(root, p, q).lca;
    }

    private AuxType dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return new AuxType(false, false, null);
        }

        AuxType left = dfs(root.left, p, q);
        AuxType right = dfs(root.right, p, q);

        AuxType aux = new AuxType(left.pExists || right.pExists || root == p,
                left.qExists || right.qExists || root == q, null);
        if (left.lca != null) {
            aux.lca = left.lca;
        } else if (right.lca != null) {
            aux.lca = right.lca;
        } else if ((left.pExists || right.pExists || root == p) && (left.qExists || right.qExists || root == q)) {
            aux.lca = root;
        }

        return aux;
    }
}
