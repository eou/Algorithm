// 572. Subtree of Another Tree
class Solution {
    // 时间复杂度为 O(m * n)
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return s != null && (isSame(s, t) || isSubtree(s.left, t) || isSubtree(s.right, t));
    }

    public boolean isSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        } else if (s == null || t == null) {
            return false;
        } else {
            return s.val == t.val && isSame(s.left, t.left) && isSame(s.right, t.right);
        }
    }
}

class Solution {
    // 将树转换成字符串比较，时间复杂度为 O(m * n + n^2 + m^2)，除非用KMP
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
    private String preOrder(TreeNode root) {
        StringBuilder strBuilder = new StringBuilder();
        if (root == null) {
            strBuilder.append(",#");
            return strBuilder.toString();
        }
        strBuilder.append(",").append(root.val);
        StringBuilder left = new StringBuilder(preOrder(root.left));
        StringBuilder right = new StringBuilder(preOrder(root.right));
        strBuilder.append(left).append(right);
        return strBuilder.toString();
    }
}