// 103. Binary Tree Zigzag Level Order Traversal
// 此题与102和107把层次遍历的各种顺序全考察了
class Solution {
    // 用栈解决，一开始以为一个栈就可以，后面就误入歧途，其实还是用队列更简洁
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        // 弹出和压入元素不能在一个栈中同时操作
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        stack1.push(root);
        boolean dir = true;

        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            if (dir) {
                int size = stack1.size();
                for (int i = 0; i < size; ++i) {
                    TreeNode node = stack1.pop();
                    level.add(node.val);
                    if (node.left != null) {
                        stack2.push(node.left);
                    }
                    if (node.right != null) {
                        stack2.push(node.right);
                    }
                }
            } else {
                int size = stack2.size();
                for (int i = 0; i < size; ++i) {
                    TreeNode node = stack2.pop();
                    level.add(node.val);
                    if (node.right != null) {
                        stack1.push(node.right);
                    }
                    if (node.left != null) {
                        stack1.push(node.left);
                    }
                }
            }
            dir = !dir;
            results.add(level);
        }

        return results;
    }
}

class Solution {
    // 用队列解决，改一下BFS的层次遍历即可
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) {
            return results;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean zigzag = false;

        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                // 每一层数组正序添加和反序添加轮流即可
                if (zigzag) {
                    level.add(0, node.val);
                } 
                else {
                    level.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            results.add(level);
            zigzag = !zigzag;
        }

        return results;
    }
}

class Solution {
    // 改一下层次遍历的DFS也能做
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        helper(root, results, 0);
        return results;
    }

    private void helper(TreeNode root, List<List<Integer>> results, int level) {
        if (root == null) {
            return;
        }

        if (results.size() == level) {
            results.add(new LinkedList<>());
        }

        // if (level % 2 == 0) {
        //     results.get(level).add(root.val);
        // } else {
        //     results.get(level).add(0, root.val);
        // }

        helper(root.left, results, level + 1);
        helper(root.right, results, level + 1);

        // 注意以下相应操作放在两个helper前面也是对的
        if (level % 2 == 0) {
            results.get(level).add(root.val);
        } else {
            results.get(level).add(0, root.val);
        }
    }
}