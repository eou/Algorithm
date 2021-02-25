// 589. N-ary Tree Preorder Traversal
// Similar with 144. Binary Tree Preorder Traversal.
class Solution {
    public List<Integer> preorder(Node root) {
        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }
        
        res.add(root.val);
        for (Node child : root.children) {
            res.addAll(preorder(child));
        }
        
        return res;
    }
}

class Solution {
    public List<Integer> preorder(Node root) {
        Deque<Node> stack = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            res.add(node.val);
            
            for (int i = node.children.size() - 1; i >= 0; i--) {
                stack.push(node.children.get(i));
            }
        }

        return res;
    }
}
