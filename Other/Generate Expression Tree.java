/**
 * Generate Expression Tree.java
 * 将前缀或者后缀表达式转换为表达式树
 */
import java.util.*;
import java.io.*;

class Solution {
    class TreeNode {
        char value;
        TreeNode left;
        TreeNode right;
        TreeNode(char c) {
            value = c;
        }
    }

    private boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
            return true;
        }
        return false;
    }

    private void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.value + " ");
            inorder(root.right);
        }
    }

    private TreeNode postfixToExpressionTree(String postfix) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode root, node1, node2;

        for (int i = 0; i < postfix.length(); i++) {
            root = new TreeNode(postfix.charAt(i));
            if (isOperator(postfix.charAt(i))) {
                node1 = stack.pop();
                node2 = stack.pop();
                root.right = node1;
                root.left = node2;
            }
            stack.push(root);
        }
        root = stack.peek();
        stack.pop();

        return root;
    }
    
    String prefix = "*+ab-c/de";
    private TreeNode prefixToExpressionTree() {
        char c = prefix.charAt(0);
        prefix = prefix.substring(1);
        TreeNode root = new TreeNode(c);

        if (isOperator(c)) {
            TreeNode left = prefixToExpressionTree();
            TreeNode right = prefixToExpressionTree();
            root.left = left;
            root.right = right;
        }

        return root;
    }

    public static void main(String args[]) {
        Solution s = new Solution();
        // String postfix = "ab+ef*g*-";
        // TreeNode root = s.postfixToExpressionTree(postfix);
        // System.out.println("infix expression is");
        // s.inorder(root);
        
        TreeNode root = s.prefixToExpressionTree();
        System.out.println("infix expression is");
        s.inorder(root);
    }
}
