// 98. Validate Binary Search Tree
class Solution {
    // 分治法1，O(n)
    private class auxiliary {
        public boolean isValidBST;
        public long minVal, maxVal;

        public auxiliary(boolean isValidBST, long minVal, long maxVal) {
            this.isValidBST = isValidBST;
            this.minVal = minVal;
            this.maxVal = maxVal;
        }
    }

    // minVal, maxVal 也可以用 minNode, maxNode代替，换汤不换药
    // private class auxiliary {
    //     public boolean isValidBST;
    //     public TreeNode maxNode, minNode;
    //     public auxiliary(boolean isBisValidBST) {
    //         this.isValidBST = isValidBST;
    //         this.maxNode = null;
    //         this.minNode = null;
    //     }
    // }

    public boolean isValidBST(TreeNode root) {
        return helper(root).isValidBST;
    }

    private auxiliary helper(TreeNode root) {
        // 初始化 aux 需要把 minVal 和 maxVal 的值反过来设最大最小值，因为需要比较 minVal 的时候肯定是在右边，这时候需要满足 BST
        // 就必须 minVal > root.val, 反之亦然
        // 注意 Long 防止 Integer 范围太小
        auxiliary aux = new auxiliary(true, Long.MAX_VALUE, Long.MIN_VALUE);
        if (root == null) {
            return aux;
        }

        auxiliary left = helper(root.left);
        auxiliary right = helper(root.right);

        if (left.isValidBST == false || right.isValidBST == false) {
            aux.isValidBST = false;
        } else {
            // 以下这段需要注意非空节点但是有子节点为空的情况，如叶子节点，只有左子节点，只有右子节点
            if (left.maxVal < root.val && root.val < right.minVal) {
                aux.maxVal = right.maxVal;
                aux.minVal = left.minVal;
                if (root.left == null) {
                    aux.minVal = root.val;
                }
                if (root.right == null) {
                    aux.maxVal = root.val;
                }
                // 也可以写的简短一点
                // aux.minVal = root.left == null ? root.val : left.minVal;
                // aux.maxVal = root.right == null ? root.val : right.maxVal;
            } else {
                aux.isValidBST = false;
            }
        }
        return aux;
    }
}

class Solution {
    // 分治法2，O(n)
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null)
            return true;
        if (root.val >= maxVal || root.val <= minVal)
            return false;
        // 保证对于左子树，节点值必须小于根节点；对于右子树，节点值必须大于根节点
        // 其实这是严格按照题目写定义：
        // The left subtree of a node contains only nodes with keys less than the node's
        // key.
        // The right subtree of a node contains only nodes with keys greater than the
        // node's key.
        // Both the left and right subtrees must also be binary search trees.
        // 保证对于左子树，节点值必须小于根节点；对于右子树，节点值必须大于根节点
        // 通过多态传递最大最小值参数
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
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
