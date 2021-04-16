// 1631. Path With Minimum Effort
class Solution {
    class UnionFind {
        private int[] id;
        public UnionFind(int n) {
            id = new int[n];
            for (int i = 0; i < n; i++) {
                id[i] = i;
            }
        }
        public void union(int i, int j) {
            id[find(i)] = id[find(j)];
        }
        public boolean query(int i, int j) {
            return find(i) == find(j);
        }
        public int find(int i) {
            if (id[i] != i) {
                id[i] = find(id[i]);  // path compression
            }
            return id[i];
        }
    }

    public int indexMap(int i, int j, int col) {
        return i * col + j;
    }

    public int minimumEffortPath(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;
        
        UnionFind uf = new UnionFind(row * col);
        
        // edge: (i, j, w)
        List<int[]> graph = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int index = indexMap(i, j, col);
                // only to right, down
                if (i + 1 < row) {
                    int x = index, y = indexMap(i + 1, j, col);
                    int w = Math.abs(heights[i][j] - heights[i + 1][j]);
                    graph.add(new int[]{x, y, w});
                }
                if (j + 1 < col) {
                    int x = index, y = indexMap(i, j + 1, col);
                    int w = Math.abs(heights[i][j] - heights[i][j + 1]);
                    graph.add(new int[]{x, y, w});
                }
            }
        }
        
        Collections.sort(graph, (a, b) -> a[2] - b[2]);
        
        int start = indexMap(0, 0, col), end = indexMap(row - 1, col - 1, col);
        for (int[] edge : graph) {
            int x = edge[0], y = edge[1], w = edge[2];
            uf.union(x, y);
            if (uf.query(start, end)) {
                return w;
            }
        }
        return 0;
    }
}


