// 426. Convert Binary Search Tree to Sorted Doubly Linked List
class Solution {
    // 分治法
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
    // 遍历法
    Node pre = null;
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        // 新建一个独立节点，是在head之前的辅助节点
        Node tmp = new Node(0, null, null);
        // pre用tmp初始化，之后连上第一个节点
        pre = tmp;
        helper(root);
        // 此时pre在最后一个节点上，tmp.right指向head节点，tmp.right也就是head指针
        pre.right = tmp.right;
        tmp.right.left = pre;
        return tmp.right;
    }

    private void helper(Node cur) {
        if (cur == null) {
            return;
        }
        helper(cur.left);
        pre.right = cur;
        cur.left = pre;
        pre = cur;
        helper(cur.right);
    }
}

class Solution {
    // 非递归版本，中序遍历的非递归版本改编
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        Node head = null;
        Node pre = null;
        Node node = root;
        // Stack类比较旧，Java推荐用ArrayDeque
        // 使用栈时，用ArrayDeque的push和pop方法
        // 使用队列时，使用ArrayDeque的add和remove方法
        // 同时注意LinkedList类实现了List接口，是一个集合，可以根据索引来随机的访问集合中的元素；还实现了Deque接口，还是一个队列，可以被当成双端队列来使用
        Deque<Node> stack = new ArrayDeque<>();

        // 以下就是中序遍历非递归模板
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();

            // 进行相应操作
            if (head == null) {
                // 首节点确认
                head = node;
            }
            if (pre != null) {
                pre.right = node;
                node.left = pre;
            }
            pre = node;
            
            node = node.right;
        }

        // 首尾闭合
        head.left = pre;
        pre.right = head;
        return head;
    }
}