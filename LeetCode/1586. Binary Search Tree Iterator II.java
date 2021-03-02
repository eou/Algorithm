// 1586. Binary Search Tree Iterator II
class BSTIterator {
    private Deque<TreeNode> stack;
    
    private List<Integer> list;
    // pointer points to current value
    // previous value is the value before this value
    // next is the value after this value
    private int pointer;
    private TreeNode node;

    public BSTIterator(TreeNode root) {
        node = root;
        stack = new ArrayDeque<>();
        list = new ArrayList<>();
        pointer = -1;
    }
    
    public boolean hasNext() {
        return pointer + 1 < list.size() || node != null || !stack.isEmpty();
    }
    
    public int next() {
        pointer++;
        if (pointer < list.size()) {
            return list.get(pointer);
        }

        while (node != null) {
            stack.push(node);
            node = node.left;
        }
        
        node = stack.pop();
        
        list.add(node.val);

        node = node.right;
        
        return list.get(pointer);
    }
    
    public boolean hasPrev() {
        return pointer > 0;
    }
    
    public int prev() {
        pointer--;
        return list.get(pointer);
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * boolean param_1 = obj.hasNext();
 * int param_2 = obj.next();
 * boolean param_3 = obj.hasPrev();
 * int param_4 = obj.prev();
 */