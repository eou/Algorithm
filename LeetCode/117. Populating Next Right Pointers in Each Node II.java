// 117. Populating Next Right Pointers in Each Node II
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        // queue,
        // 1 null <=
        // 2 3 null <=
        // 4 5 7 null <=
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();

            Node cur = queue.poll();
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                queue.offer(cur.right);
            }

            Node nxt = null;
            for (int i = 1; i < size; i++) {
                nxt = queue.poll();
                if (nxt.left != null) {
                    queue.offer(nxt.left);
                }
                if (nxt.right != null) {
                    queue.offer(nxt.right);
                }
                cur.next = nxt;
                cur = nxt;
            }
        }

        return root;
    }
}

// follow up: no next pointer, use right pointer as next pointer
class Solution {
    public void treeToLinkedList(TreeNode root) {
        if (root == null) {
            return;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();

            TreeNode nextLeftMost = null;
            TreeNode curLeftMost = null;
            TreeNode next = null;

            TreeNode cur = queue.poll();
            curLeftMost = cur;
            if (cur.left != null) {
                nextLeftMost = cur.left;
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                nextLeftMost = nextLeftMost == null ? cur.right : nextLeftMost;
                queue.offer(cur.right);
            }

            for (int i = 1; i < size; i++) {
                next = queue.poll();
                if (next.left != null) {
                    nextLeftMost = nextLeftMost == null ? next.left : nextLeftMost;
                    queue.offer(next.left);
                }
                if (next.right != null) {
                    nextLeftMost = nextLeftMost == null ? next.right : nextLeftMost;
                    queue.offer(next.right);
                }
                cur.left = null;
                cur.right = next;
                cur = next;
            }
            // right most node
            cur.left = null;
            cur.right = null;
            curLeftMost.left = nextLeftMost;
        }
    }
}