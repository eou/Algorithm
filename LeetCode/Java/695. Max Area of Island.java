// 695. Max Area of Island
class Solution {
    class UnionFind {
        int[] parent;
        int[] size;
        int unions;

        public UnionFind(int[][] grid) {
            unions = 0;
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows * cols];
            size = new int[rows * cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 1) {
                        parent[i * cols + j] = i * cols + j;
                        size[i * cols + j] = 1;
                        unions++;
                    }
                }
            }
        }

        public int root(int i) {
            while (i != parent[i]) {
                parent[i] = parent[parent[i]]; // path compression
                i = parent[i];
            }
            return i;
        }

        public void unite(int i, int j) {
            int ri = root(i);
            int rj = root(j);
            if (ri == rj) {
                return;
            }

            if (size[ri] > size[rj]) {
                parent[rj] = ri;
                size[ri] += size[rj];
            } else {
                parent[ri] = rj;
                size[rj] += size[ri];
            }
            unions--;
        }
    }

    int[] dx = { 0, 0, 1, -1 };
    int[] dy = { 1, -1, 0, 0 };
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        UnionFind uf = new UnionFind(grid);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < 4; k++) {
                        int x = i + dx[k];
                        int y = j + dy[k];
                        if (isValid(x, y, rows, cols) && grid[x][y] == 1) {
                            uf.unite(x * cols + y, i * cols + j);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < uf.size.length; i++) {
            max = Math.max(max, uf.size[i]);
        }
        return max;
    }

    private boolean isValid(int i, int j, int rows, int cols) {
        if (i >= 0 && i < rows && j >= 0 && j < cols) {
            return true;
        } else {
            return false;
        }
    }
}

class Solution {
    // DFS 版本，时间复杂度为 O(n^2)
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    max = Math.max(max, helper(grid, i, j));
                }
            }
        }

        return max;
    }

    public int helper(int[][] grid, int i, int j) {
        if (isValid(i, j, grid.length, grid[0].length) && grid[i][j] == 1) {
            grid[i][j] = 0;
            return 1 + helper(grid, i + 1, j) + helper(grid, i, j + 1) + helper(grid, i - 1, j)
                    + helper(grid, i, j - 1);
        }
        return 0;
    }

    private boolean isValid(int i, int j, int rows, int cols) {
        if (i >= 0 && i < rows && j >= 0 && j < cols) {
            return true;
        } else {
            return false;
        }
    }

}