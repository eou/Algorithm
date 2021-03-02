// 510. Inorder Successor in BST II
class Solution {
    public Node inorderSuccessor(Node node) {
        Node pointer = node, pre = null;
        // Find root
        while (pointer.parent != null) {
            pointer = pointer.parent;
        }

        // Same as 285. Inorder Successor in BST
        while (pointer != null) {
            if (pointer.val <= node.val) {
                pointer = pointer.right;
            } else {
                pre = pointer;
                pointer = pointer.left;
            }
        }
        
        return pre;
    }
}

class Solution {
    public Node inorderSuccessor(Node node) {
        // 1. If node is left child of his parent. 
        // Next greater node should be the most left child of his right child OR his parent.
        // 2. If node is right child of his parent. 
        // Next greater node should be the most left child of his right child OR the first greater grand parent.
        // 3. If node has no parent. 
        // Next greater node should be the most left child of his right child.
        if (node.parent != null && node.parent.val > node.val) {
            // left child
            if (node.right == null) {
                return node.parent;
            }
        }

        if (node.parent != null && node.parent.val < node.val) {
            // right child
            if (node.right == null) {
                Node ptr = node, pre = null;
                while (ptr != null && ptr.val <= node.val) {
                    pre = ptr;
                    ptr = ptr.parent;
                }

                return ptr;
            }
        }

        // right child or no parent
        Node ptr = node, pre = null;
        if (node.right != null) {
            ptr = node.right;
            while (ptr != null) {
                pre = ptr;
                ptr = ptr.left;
            }
        }

        return pre;
    }
}

// More simpler, same solution
class Solution {
    public Node inorderSuccessor(Node node) {
        // the successor is somewhere lower in the right subtree
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        // the successor is somewhere upper in the tree
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }
}