// 617. Merge Two Binary Trees
class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        TreeNode root = new TreeNode(-1);

        if (t1 != null && t2 != null) {
            root.val = t1.val + t2.val;
            root.left = mergeTrees(t1.left, t2.left);
            root.right = mergeTrees(t1.right, t2.right);
        } else if (t1 != null) {
            root.val = t1.val;
            root.left = mergeTrees(t1.left, null);
            root.right = mergeTrees(t1.right, null);
        } else if (t2 != null) {
            root.val = t2.val;
            root.left = mergeTrees(t2.left, null);
            root.right = mergeTrees(t2.right, null);
        } else {
            root = null;
            return root;
        }

        return root;
    }
}

class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
            
        if (t2 == null) {
            return t1;
        }
            
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);

        return t1;
    }
}

class Solution {
    // DFS
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }

        if (t2 == null) {
            return t1;
        }

        Deque<TreeNode[]> stack = new ArrayDeque<>();
        stack.push(new TreeNode[] { t1, t2 });

        while (!stack.isEmpty()) {
            TreeNode[] t = stack.pop();
            if (t[0] == null || t[1] == null) {
                continue;
            }

            t[0].val += t[1].val;

            if (t[0].left == null) {
                t[0].left = t[1].left;
            } else {
                stack.push(new TreeNode[] { t[0].left, t[1].left });
            }

            if (t[0].right == null) {
                t[0].right = t[1].right;
            } else {
                stack.push(new TreeNode[] { t[0].right, t[1].right });
            }
        }

        return t1;
    }
}

class Solution {
    // BFS
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }

        if (t2 == null) {
            return t1;
        }

        // use array in the queue to manipulate at the same time
        Deque<TreeNode[]> queue = new ArrayDeque<>();
        queue.offer(new TreeNode[] { t1, t2 });
        
        while (!queue.isEmpty()) {
            TreeNode[] t = queue.poll();
            // merge 2 into 1 when it is not null
            if (t[1] == null) {
                continue;
            }

            // t[0] must not be null
            t[0].val += t[1].val;

            if (t[0].left == null) {
                t[0].left = t[1].left;
            } else {
                queue.offer(new TreeNode[] { t[0].left, t[1].left });
            }
                
            if (t[0].right == null) {
                t[0].right = t[1].right;
            } else {
                queue.offer(new TreeNode[] { t[0].right, t[1].right });
            }   
        }

        return t1;
    }
}