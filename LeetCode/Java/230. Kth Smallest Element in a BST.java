// 230. Kth Smallest Element in a BST
// 可以特化为 find Median in a BST
// 可以转化为双向链表，然后用前后（快慢）指针找
// 可以用 max-heap
class Solution {
    // 时间复杂度为 O(k)
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            if (k == 1) {
                return node.val;
            }
            --k;
            node = node.right;
        }

        return -1;
    }
}

class Solution {
    // 时间复杂度为 O(n)
    public int kthSmallest(TreeNode root, int k) {
        int leftNodes = countNodes(root.left);
        if (k <= leftNodes) {
            return kthSmallest(root.left, k);
        } else if (k > leftNodes + 1) {
            return kthSmallest(root.right, k - leftNodes - 1);
        }

        return root.val;
    }
    
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}

class Solution {
    // 用 map 存各个节点的子节点数，多次查询会比较快
    public int kthSmallest(TreeNode root, int k) {
        Map<TreeNode, Integer> numOfChildren = new HashMap<>();
        countNodes(root, numOfChildren);
        return quickSelectOnTree(root, k, numOfChildren);
    }

    private int countNodes(TreeNode root, Map<TreeNode, Integer> numOfChildren) {
        if (root == null) {
            return 0;
        }

        int left = countNodes(root.left, numOfChildren);
        int right = countNodes(root.right, numOfChildren);
        numOfChildren.put(root, left + right + 1);
        return left + right + 1;
    }

    private int quickSelectOnTree(TreeNode root, int k, Map<TreeNode, Integer> numOfChildren) {
        if (root == null) {
            return -1;
        }

        int left = root.left == null ? 0 : numOfChildren.get(root.left);
        if (left >= k) {
            return quickSelectOnTree(root.left, k, numOfChildren);
        }
        if (left + 1 == k) {
            return root.val;
        }

        return quickSelectOnTree(root.right, k - left - 1, numOfChildren);
    }
}