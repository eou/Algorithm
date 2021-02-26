// 1120. Maximum Average Subtree
// 3 values: number of nodes, subtree sum, max average
class Solution {
    class AuxType {
        int nodes;
        double subSum;
        double maxAverage;
        AuxType(int nodes, double subSum, double maxAverage) {
            this.nodes = nodes;
            this.subSum = subSum;
            this.maxAverage = maxAverage;
        }
    }

    public double maximumAverageSubtree(TreeNode root) {
        return dfs(root).maxAverage;
    }
    
    private AuxType dfs(TreeNode root) {
        if (root == null) {
            return new AuxType(0, 0, Double.MIN_VALUE);
        }

        if (root.left == null && root.right == null) {
            return new AuxType(1, root.val, root.val);
        }
        
        AuxType left = dfs(root.left);
        AuxType right = dfs(root.right);
        
        double maxAverage = (left.subSum + right.subSum + root.val) / 
                            (left.nodes + right.nodes + 1);
        AuxType aux = new AuxType(left.nodes + right.nodes + 1,
                                  left.subSum + right.subSum + root.val,
                                  maxAverage);
        
        if (maxAverage >= left.maxAverage && maxAverage >= right.maxAverage) {
            return aux;
        } else if (left.maxAverage > right.maxAverage) {
            aux.maxAverage = left.maxAverage;
            return aux;
        } else {
            aux.maxAverage = right.maxAverage;
            return aux;
        }
    }
}

// Non-recursive, using Postorder traversal
// Make sure that left child and right child have already been visited before visiting root node
class Solution {
    public double maximumAverageSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Map<TreeNode, Integer> nodeMap = new HashMap<>();
        Map<TreeNode, Integer> sumMap = new HashMap<>();
        Double maxAverage = Double.MIN_VALUE;

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode pre = null;
        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.peek();
            if (node.right != null && pre != node.right) {
                // pre != node.right 说明右子树尚未访问
                node = node.right;
            } else {
                // 右子树已经遍历过或者没有右子树
                pre = node;

                node = stack.pop();
                // do sth.
                int leftNodes = nodeMap.getOrDefault(node.left, 0);
                int rightNodes = nodeMap.getOrDefault(node.right, 0);

                int leftSum = sumMap.getOrDefault(node.left, 0);
                int rightSum = sumMap.getOrDefault(node.right, 0);

                maxAverage = Math.max(maxAverage,
                        (double) (leftSum + rightSum + node.val) / (double) (leftNodes + rightNodes + 1));

                nodeMap.put(node, leftNodes + rightNodes + 1);
                sumMap.put(node, leftSum + rightSum + node.val);

                node = null;
            }
        }

        return maxAverage;
    }
}
