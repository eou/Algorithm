// 270. Closest Binary Search Tree Value
class Solution {
    public int closestValue(TreeNode root, double target) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        double preDiff = Math.abs(root.val - target), diff = Math.abs(root.val - target);
        int result = root.val;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            diff = Math.abs(node.val - target);
            if (diff < preDiff) {
                result = node.val;
            }
            preDiff = diff;

            node = node.right;
        }

        return result;
    }
}

class Solution {
    public int closestValue(TreeNode root, double target) {
        int result = root.val;
        while (root != null) {
            if (Math.abs(target - root.val) < Math.abs(target - result)) {
                result = root.val;
            }
            root = root.val > target ? root.left : root.right;
        }
        return result;
    }
}
