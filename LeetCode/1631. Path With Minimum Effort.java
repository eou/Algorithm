// 1631. Path With Minimum Effort
// weighted graph, find minimum distance between 2 vertices.
// A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
// Binary search all possibilities or Union Find or Dijkstra algorithm
class Solution {
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
        
        // sort by weight
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
}

// dijkstra algorithm
class Solution {
    int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[2] - b[2]);
        pq.offer(new int[] { 0, 0, 0 });

        int[] dist = new int[m * n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        boolean[] seen = new boolean[m * n];

        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int x = edge[0], y = edge[1], d = edge[2];
            int id = x * n + y;
            if (seen[id]) {
                continue;
            }

            // find destination
            if (x == m - 1 && y == n - 1) {
                break;
            }

            seen[id] = true;

            for (int i = 0; i < 4; ++i) {
                int nx = x + dir[i][0];
                int ny = y + dir[i][1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    // find shorter path
                    if (Math.max(d, Math.abs(heights[x][y] - heights[nx][ny])) < dist[nx * n + ny]) {
                        dist[nx * n + ny] = Math.max(d, Math.abs(heights[x][y] - heights[nx][ny]));
                        pq.offer(new int[] { nx, ny, dist[nx * n + ny] });
                    }
                }
            }
        }

        return dist[m * n - 1];
    }
}

// binary search
class Solution {
    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        int left = 0, right = 1000000 - 1, res = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Deque<int[]> queue = new ArrayDeque<int[]>();
            queue.offer(new int[]{0, 0});

            boolean[] seen = new boolean[m * n];
            seen[0] = true;

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                for (int i = 0; i < 4; ++i) {
                    int nx = x + dir[i][0];
                    int ny = y + dir[i][1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !seen[nx * n + ny] && Math.abs(heights[x][y] - heights[nx][ny]) <= mid) {
                        queue.offer(new int[]{nx, ny});
                        seen[nx * n + ny] = true;
                    }
                }
            }

            if (seen[m * n - 1]) {
                res = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return res;
    }
}