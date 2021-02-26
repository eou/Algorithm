// 98. Validate Binary Search Tree
// DAC，O(n)
class Solution {
    private class AuxType {
        long minVal, maxVal;
        boolean isValidBST;

        AuxType(long minVal, long maxVal, boolean isValidBST) {
            this.minVal = minVal;
            this.maxVal = maxVal;
            this.isValidBST = isValidBST;
        }
    }

    public boolean isValidBST(TreeNode root) {
        return dfs(root).isValidBST;
    }

    private AuxType dfs(TreeNode root) {
        if (root == null) {
            // 初始化 aux 需要把 minVal 和 maxVal 的值反过来设最大最小值，因为需要比较 minVal 的时候肯定是在右边，这时候需要满足 BST
            // 就必须 minVal > root.val, 反之亦然
            // 注意 Long 防止 Integer 范围太小
            return new AuxType(Long.MAX_VALUE, Long.MIN_VALUE, true);
        }

        AuxType left = dfs(root.left);
        AuxType right = dfs(root.right);

        long minVal = root.left == null ? root.val : left.minVal;
        long maxVal = root.right == null ? root.val : right.maxVal;
        AuxType aux = new AuxType(minVal, maxVal, false);

        if (root.val > left.maxVal && root.val < right.minVal && left.isValidBST && right.isValidBST) {
            aux.isValidBST = true;
        }

        return aux;
    }
}

// 严格按照题目写定义：
// The left subtree of a node contains only nodes with keys less than the node's
// key.
// The right subtree of a node contains only nodes with keys greater than the
// node's key.
// Both the left and right subtrees must also be binary search trees.
// 保证对于左子树，节点值必须小于根节点；对于右子树，节点值必须大于根节点
class Solution {
    // recommend, DAC，O(n)
    public boolean isValidBST(TreeNode root) {
        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean dfs(TreeNode root, long minVal, long maxVal) {
        if (root == null) {
            return true;
        }

        if (root.val >= maxVal || root.val <= minVal) {
            return false;
        }
            
        return dfs(root.left, minVal, root.val) && dfs(root.right, root.val, maxVal);
    }
}

class Solution {
    // 遍历法1，O(n)
    // 递归版本的中序遍历 in-order traverse，因为 BST 的前序遍历是严格递增（这个题目不是非递减）序列
    private int lastVal = Integer.MIN_VALUE;
    private boolean firstNode = true;

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 左节点
        if (!isValidBST(root.left)) {
            return false;
        }

        // 根节点
        if (!firstNode && lastVal >= root.val) {
            return false;
        }
        firstNode = false;
        // lastVal 就是上一个节点值
        lastVal = root.val;

        // 右节点
        if (!isValidBST(root.right)) {
            return false;
        }

        return true;
    }
}

class Solution {
    // 遍历法2，O(n)
    // 非递归版本的中序遍历 in-order traverse
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

        TreeNode node = root;
        TreeNode lastNode = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();

            // 对节点进行相应操作
            if (lastNode != null && lastNode.val >= node.val) {
                return false;
            }
            lastNode = node;

            node = node.right;
        }

        return true;
    }
}
