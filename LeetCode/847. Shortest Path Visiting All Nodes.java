// 847. Shortest Path Visiting All Nodes
// BFS, state compression, O(N^2 * 2^N)
class Solution {
    class State {
        int path;
        int cur;
        public State(int path, int cur) {
            this.path = path;   // 111100110...0001011
            this.cur = cur;
        }
    }

    public int shortestPathLength(int[][] graph) {
        // number of vertex of graph
        int v = graph.length;
        
        // dp[i][j] means the shortest path through path i ending at j
        // New graph vertex: O(N * 2^N), edge: O(N^2 * 2^N)
        // BFS need to traverse all edges thus the overall time compexity is O(N^2 * 2^N)
        int[][] dp = new int[1 << v][v];
        for (int[] r : dp) {
            Arrays.fill(r, v * v);
        }
        
        Deque<State> queue = new ArrayDeque<>();
        for (int i = 0; i < v; i++) {
            queue.offer(new State(1 << i, i));
            dp[1 << i][i] = 0;
        }
        
        while (!queue.isEmpty()) {
            State s = queue.poll();
            int distance = dp[s.path][s.cur];
            if (s.path == (1 << v) - 1) {
                return distance;
            }
            
            for (int neighbor : graph[s.cur]) {
                int nextPath = s.path | (1 << neighbor);
                if (dp[nextPath][neighbor] > distance + 1) {
                    dp[nextPath][neighbor] = distance + 1;
                    queue.offer(new State(nextPath, neighbor));
                }
            }
        }
        
        return -1;
    }
}

// Folyed + TSP (DP), O(N^3 + N^2*2^N)
class Solution {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        // Use floyd-warshall algorithm to calculate the shortest distance between any two different nodes
        int[][] dis = new int[n][n];
        for (int[] arr : dis) {
            Arrays.fill(arr, n * n);
        }
        
        // Construct distance between neighbors
        for (int i = 0; i < n; ++i) {
            dis[i][i] = 0;
            for (int j : graph[i]) {
                dis[i][j] = 1;
            }
        }
        
        // Floyd-warhsall algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }

        // travel salesman problem(TSP)
        int[][] dp = new int[1 << n][n];
        for (int[] arr : dp) {
            Arrays.fill(arr, n * n);
        }
        
        // Starting point
        for (int i = 0; i < n; ++i) {
            dp[1 << i][i] = 0;
        }

        for (int i = 0; i < (1 << n); ++i) {
            for (int j = 0; j < n; ++j) {
                // avoid duplicate with starting point
                if ((i & (1 << j)) != 0) {
                    for (int k = 0; k < n; k++){
                        // avoid duplicate with starting point, try all k between i and j
                        if ((i & (1 << k)) != 0){
                            // distance for path i ending at j = distance for path i' (without j) ends at k + distance between k and j
                            dp[i][j] = Math.min(dp[i][j], dp[i ^ (1 << j)][k] + dis[k][j]);
                        }
                    }
                }
            }
        }
        
        // Math.min (dp[(1 << n) - 1][i])
        int res = n * n;
        for (int i : dp[(1 << n) - 1]) {
            res = Math.min(res, i);
        }
        return res;
    }
}