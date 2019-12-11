// 331. Verify Preorder Serialization of a Binary Tree
class Solution {
    // all non-null node provides 2 outdegree and 1 indegree (2 children and 1 parent), except root
    // all null node provides 0 outdegree and 1 indegree (0 child and 1 parent).
    public boolean isValidSerialization(String preorder) {
        String[] nodes = preorder.split(",");
        int degree = 1;
        for(String node : nodes) {
            degree--;
            if(degree < 0) {
                return false;
            }
            if(!node.equals("#")) {
                degree += 2;
            }
        }
        
        return degree == 0;
    }
}

class Solution {
    public boolean isValidSerialization(String preorder) {
        String[] nodes = preorder.split(",");
        Deque<String> stack = new ArrayDeque<>();
        for (String node : nodes) {
            // when the node is # and the top of the stack is #, which means they are the leaves of one parent
            while (node.equals("#") && !stack.isEmpty() && stack.peek().equals("#")) {
                // pop the left child of the parent and the parent, then push # replacing the parent
                stack.pop();
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
            stack.push(node);
        }

        return stack.size() == 1 && stack.peek().equals("#");
    }
}