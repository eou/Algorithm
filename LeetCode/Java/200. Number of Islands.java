// 200. Number of Islands
// BFS, DFS, Union Find，时间复杂度都是O(m*n)，其中BFS的空间复杂度较低，为O(min(m, n))
class Solution {
    // BFS版本，目的是从一个点延伸到一个岛，但是要改变原来数组的值
    // 可以不用Pair类，直接保存 row * len + col，也就是二维数组摊成一维数组后的下标
    class Pair {
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public int numIslands(char[][] grid) {
        if(grid.length < 1) {
            return 0;
        }
        
        int result = 0;
        Queue<Pair> queue = new ArrayDeque<>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == '1') {
                    result++;
                    queue.offer(new Pair(i, j));
                    grid[i][j] = 0;
                    helper(queue, grid);
                }
            }
        }
        
        return result;
    }
    
    private void helper(Queue<Pair> queue, char[][] grid) {
        while(!queue.isEmpty()) {
            Pair p = queue.poll();
            
            if(p.y > 0 && grid[p.x][p.y - 1] == '1') {
                queue.offer(new Pair(p.x, p.y - 1));
                grid[p.x][p.y - 1] = 0;
            }
            
            if(p.y < grid[0].length - 1 && grid[p.x][p.y + 1] == '1') {
                queue.offer(new Pair(p.x, p.y + 1));
                grid[p.x][p.y + 1] = 0;
            }
            
            if(p.x > 0 && grid[p.x - 1][p.y] == '1') {
                queue.offer(new Pair(p.x - 1, p.y));
                grid[p.x - 1][p.y] = 0;
            }
            
            if(p.x < grid.length - 1 && grid[p.x + 1][p.y] == '1') {
                queue.offer(new Pair(p.x + 1, p.y));
                grid[p.x + 1][p.y] = 0;
            }
        }
    }
}

class Solution {
    // DFS版本，目的是从一个点找到所有到周边的路径，也是要改变原数组的值
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int num_islands = 0;
        for (int r = 0; r < grid.length; ++r) {
            for (int c = 0; c < grid[0].length; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    helper(grid, r, c);
                }
            }
        }

        return num_islands;
    }

    void helper(char[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        helper(grid, r - 1, c);
        helper(grid, r + 1, c);
        helper(grid, r, c - 1);
        helper(grid, r, c + 1);
    }
}

class Solution {
    // Union Find版本
    // union操作 时间复杂度为O(α(n))，近似O(1)，总时间复杂度为O(m*n), 空间复杂度为O(n)
    class UnionFind {
        // # of connected components
        int count;
        int[] parent;
        int[] rank;

        public UnionFind(char[][] grid) {
            count = 0;
            int m = grid.length;
            int n = grid[0].length;
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
                    rank[rootx] += 1;
                }
                --count;
            }
        }

        // path compression
        // 路径压缩
        public int find(int i) {
            if(parent[i] != i)
                parent[i] = find(parent[i]);
            return parent[i];
        }

        public int getCount() {
            return count;
        }
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;

        UnionFind uf = new UnionFind(grid);
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0';
                    if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                        uf.union(r * nc + c, (r - 1) * nc + c);
                    }
                    if (r + 1 < nr && grid[r + 1][c] == '1') {
                        uf.union(r * nc + c, (r + 1) * nc + c);
                    }
                    if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                        uf.union(r * nc + c, r * nc + c - 1);
                    }
                    if (c + 1 < nc && grid[r][c + 1] == '1') {
                        uf.union(r * nc + c, r * nc + c + 1);
                    }
                }
            }
        }

        return uf.getCount();
    }
}