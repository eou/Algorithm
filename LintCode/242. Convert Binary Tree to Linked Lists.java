// 242. Convert Binary Tree to Linked Lists by Depth
// BFS
public class Solution {
    /**
     * @param root the root of binary tree
     * @return a lists of linked list
     */
    public List<ListNode> binaryTreeToLists(TreeNode root) {
        List<ListNode> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            ListNode head = new ListNode(-1);
            ListNode ptr = head;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                ptr.next = new ListNode(node.val);
                ptr = ptr.next;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(head.next);
        }

        return res;
    }
}

// DFS
public class Solution {
    /**
     * @param root the root of binary tree
     * @return a lists of linked list
     */
    public List<ListNode> binaryTreeToLists(TreeNode root) {
        List<ListNode> res = new ArrayList<>();
        dfs(root, res, 0);
        return res;
    }

    private void dfs(TreeNode root, List<ListNode> res, int level) {
        if (root == null) {
            return;
        }

        if (res.size() == level) {
            res.add(new ListNode(root.val));
        } else {
            ListNode head = res.get(level);
            while (head.next != null) {
                head = head.next;
            }

            head.next = new ListNode(root.val);
        }
        
        // we have to deal with left child first
        // otherwise the list node will be reversed
        // since the standard level traversal is from left to right
        dfs(root.left, res, level + 1);
        dfs(root.right, res, level + 1);
    }
}