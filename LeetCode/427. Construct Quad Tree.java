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
// DFS
class Solution {
    public Node construct(int[][] grid) {
        return dfs(grid, 0, 0, grid.length);
    }
    
    public Node dfs(int[][] grid, int row, int col, int len) {
        if (len == 1) {
            return new Node(grid[row][col] == 1 ? true : false, true, null, null, null, null);
        }
        
        int val = getVal(grid, row, col, len);
        if (val != -1) {
            // leaf
            return new Node(val == 1 ? true : false, true, null, null, null, null);
        } else {
            return new Node(false, false, 
                        dfs(grid, row, col, len / 2),
                        dfs(grid, row, col + len / 2, len / 2),
                        dfs(grid, row + len / 2, col, len / 2),
                        dfs(grid, row + len / 2, col + len / 2, len / 2));
        }
    }
    
    public int getVal(int[][] grid, int row, int col, int len) {
        // 1 for all 1, 0 for all 0, -1 for non-leaf
        for (int i = row; i < len + row; i++) {
            for (int j = col; j < len + col; j++) {
                if (grid[i][j] != grid[row][col]) {
                    return -1;
                }
            }
        }
        return grid[row][col];
    }
}

// DFS, 时间复杂度为 O(n^2)
class Solution {
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