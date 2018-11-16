// 572. Subtree of Another Tree
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) {
            return false;
        }
        if(isSame(s, t)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    
    private boolean isSame(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) {
            return true;
        }
        if(t1 == null || t2 == null) {
            return false;
        }
        return (t1.val == t2.val) && isSame(t1.left, t2.left) && isSame(t1.right, t2.right);
    }
}

class Solution {
    // 将树转换成字符串比较，时间复杂度为 O(n^2)，除非用KMP
    // 注意逗号加在节点值前面，否则[12]转换成12,#,#,与[2]转换成2,#,#,后会误判
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) {
            return false;
        }

        String sPreorder = preOrder(s);
        String tPreorder = preOrder(t);

        return sPreorder.contains(tPreorder);
    }

    // 不止前序遍历，其他遍历应该也行
    private String preOrder(TreeNode root) {
        StringBuilder strBuilder = new StringBuilder();
        // 注意用 ArrayDeque 作为栈的话不能 push 空节点进去
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if(node == null) {
                strBuilder.append(",#");
            } else {
                strBuilder.append("," + node.val);
            }
            if(node != null) {
                stack.push(node.right);
                stack.push(node.left);
            }
        }

        return strBuilder.toString();
    }

    // 前序遍历递归版本
    // private String preOrder(TreeNode root) {
    //     StringBuilder strBuilder = new StringBuilder();
    //     if (root == null) {
    //         strBuilder.append(",#");
    //         return strBuilder.toString();
    //     }
    //     strBuilder.append(",").append(root.val);
    //     StringBuilder left = new StringBuilder(preOrder(root.left));
    //     StringBuilder right = new StringBuilder(preOrder(root.right));
    //     strBuilder.append(left).append(right);
    //     return strBuilder.toString();
    // }
}