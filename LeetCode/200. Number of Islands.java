// 200. Number of Islands
// BFS, DFS, Union Find，时间复杂度都是O(m*n)，
// 其中 BFS 的空间复杂度较低，为 O(min(m, n)), because number of elements being added to the queue are constrained.
// BFS
class Solution {
    private int[] dx = { -1, 1, 0, 0 };
    private int[] dy = { 0, 0, 1, -1 };

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    Deque<int[]> queue = new ArrayDeque<>();
                    queue.offer(new int[] { i, j });
                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();

                        for (int k = 0; k < 4; k++) {
                            int x = cur[0] + dx[k];
                            int y = cur[1] + dy[k];
                            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                                if (grid[x][y] == '1') {
                                    grid[x][y] = '0';
                                    // This is the space complexity limitation
                                    queue.offer(new int[] { x, y });
                                }
                            }
                        }
                    }
                }
            }
        }

        return res;
    }
}

// DFS
class Solution {
    private int[] dx = { -1, 1, 0, 0 };
    private int[] dy = { 0, 0, 1, -1 };

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    dfs(i, j, grid);
                }
            }
        }

        return res;
    }

    private void dfs(int x, int y, char[][] grid) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return;
        }

        if (grid[x][y] == '0') {
            return;
        }

        grid[x][y] = '0';

        for (int i = 0; i < 4; i++) {
            dfs(x + dx[i], y + dy[i], grid);
        }
    }
}

class Solution {
    // Union Find版本
    // union 操作 时间复杂度为 O(α(n))，近似 O(1)，总时间复杂度为 O(m*n), 空间复杂度为 O(m*n)
    class UnionFind {
        // # of connected components
        int count;
        // root of connected component
        int[] parent;
        // see it as connected component's height/depth
        int[] rank;

        public UnionFind(char[][] grid) {
            count = 0;
            int m = grid.length, n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (grid[i][j] == '1') {
                        parent[i * n + j] = i * n + j;
                        ++count;
                    }
                    rank[i * n + j] = 0;
                }
            }
        }

        // 两个优化：
        // union with rank
        // 按秩合并，即总是将更小的树连接至更大的树上。因为影响运行时间的是树的深度，更小的树添加到更深的树的根上将不会增加秩除非它们的秩相同
        public void union(int x, int y) {
            int rootx = find(x);
            int rooty = find(y);
            if (rootx != rooty) {
                if (rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx;
                } else if (rank[rootx] < rank[rooty]) {
                    parent[rootx] = rooty;
                // 注意 rank 只有相同的时候合并才会增加
                } else {
                    parent[rooty] = rootx;
                    rank[rootx]++;
                }
                --count;
            }
        }

        // find root, path compression
        public int find(int i) {
            if(parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        public int getCount() {
            return count;
        }
    }


    private int[] dx = { -1, 1, 0, 0 };
    private int[] dy = { 0, 0, 1, -1 };
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length;
        int nc = grid[0].length;

        UnionFind uf = new UnionFind(grid);

        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0';

                    // 实际上只需要判断往右和往下两个方向即可，因为整体遍历是从左往右，从上往下的
                    for (int i = 0; i < 4; i++) {
                        int x = r + dx[i];
                        int y = c + dy[i];
                        if (x >= 0 && x < nr && y >= 0 && y < nc) {
                            if (grid[x][y] == '1') {
                                uf.union(r * nc + c, x * nc + y);
                            }
                        }
                    }
                }
            }
        }

        return uf.getCount();
    }
}