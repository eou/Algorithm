// 427. Construct Quad Tree
/*
Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    public Node() {}

    public Node(boolean _val,boolean _isLeaf,Node _topLeft,Node _topRight,Node _bottomLeft,Node _bottomRight) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = _topLeft;
        topRight = _topRight;
        bottomLeft = _bottomLeft;
        bottomRight = _bottomRight;
    }
};
*/
class Solution {
    // 时间复杂度为 O(n^2)
    public Node construct(int[][] grid) {
        return helper(grid, 0, 0, grid.length);
    }
    
    private Node helper(int[][] grid, int x, int y, int len) {
        if(len == 1) {
            return new Node(grid[x][y] != 0, true, null, null, null, null);
        }
        
        Node root = new Node();
        Node topLeft = helper(grid, x, y, len / 2);
        Node topRight = helper(grid, x, y + len / 2, len / 2);
        Node bottomLeft = helper(grid, x + len / 2, y, len / 2);
        Node bottomRight = helper(grid, x + len / 2, y + len / 2, len / 2);
        
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf 
            && topLeft.val == topRight.val && topRight.val == bottomLeft.val && bottomLeft.val == bottomRight.val) {
            root.isLeaf = true;
            root.val = topLeft.val;
        } else {
            root.topLeft = topLeft;
            root.topRight = topRight;
            root.bottomLeft = bottomLeft;
            root.bottomRight = bottomRight;
        }
        return root;
    }
}