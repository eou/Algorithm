// 426. Convert Binary Search Tree to Sorted Doubly Linked List
// Traversal
class Solution {
    private Node pre;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        Node head = new Node();
        pre = head;
        dfs(root);
        pre.right = head.right;
        head.right.left = pre;
        return head.right;
    }

    private void dfs(Node root) {
        if (root == null) {
            return;
        }

        dfs(root.left);
        pre.right = root;
        root.left = pre;
        pre = root;
        dfs(root.right);

    }
}

// DAC
class Solution {    
    private class auxiliary {
        Node head, tail;

        public auxiliary(Node head, Node tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public Node treeToDoublyList(Node root) {
        return helper(root).head;
    }

    private auxiliary helper(Node root) {
        // 判空
        if (root == null) {
            return new auxiliary(root, root);
        }

        auxiliary leftList = helper(root.left);
        auxiliary rightList = helper(root.right);

        // 先断开子树的双向链表首尾指针，然后连接根节点与其头部
        if (leftList.head != null) {
            leftList.head.left = null;
            leftList.tail.right = null;

            root.left = leftList.tail;
            leftList.tail.right = root;
        }
        if (rightList.head != null) {
            rightList.head.left = null;
            rightList.tail.right = null;

            root.right = rightList.head;
            rightList.head.left = root;
        }

        // 首尾闭合，注意前后方向很容易混淆！
        if (leftList.head != null && rightList.head != null) {
            leftList.head.left = rightList.tail;
            rightList.tail.right = leftList.head;
            return new auxiliary(leftList.head, rightList.tail);
        }
        if (leftList.head != null) {
            root.right = leftList.head;
            leftList.head.left = root;
            return new auxiliary(leftList.head, root);
        }
        if (rightList.head != null) {
            root.left = rightList.tail;
            rightList.tail.right = root;
            return new auxiliary(root, rightList.tail);
        }
        root.right = root;
        root.left = root;
        return new auxiliary(root, root);
    }
}

class Solution {
    // 非递归版本，中序遍历的非递归版本
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        Node pre = null, cur = root, head = new Node();

        Deque<Node> stack = new ArrayDeque<>();
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            if (pre != null) {
                pre.right = cur;
                cur.left = pre;
            } else {
                head.right = cur;
            }

            pre = cur;
            cur = cur.right;
        }

        pre.right = head.right;
        head.right.left = pre;

        return head.right;
    }
}
