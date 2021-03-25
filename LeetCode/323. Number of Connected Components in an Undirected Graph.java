// 323. Number of Connected Components in an Undirected Graph
// Similar with 305. Number of Islands II
// BFS
class Solution {
    public int countComponents(int n, int[][] edges) {
        // build graph
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int res = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            // bfs
            Deque<Integer> queue = new ArrayDeque<>();
            queue.offer(i);
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                visited[cur] = true;

                for (int neighbor : graph[cur]) {
                    if (!visited[neighbor]) {
                        queue.offer(neighbor);
                    }
                }
            }
            res++;
        }

        return res;
    }
}

// Simplfy Union Find, O(n + m*α(n)) ≈ O(n + m)
class Solution {
    public int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        int[] size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int components = n;
        for (int[] e : edges) {
            int p1 = findParent(parent, e[0]);
            int p2 = findParent(parent, e[1]);
            if (p1 != p2) {
                if (size[p1] < size[p2]) { // Merge small size to large size
                    size[p2] += size[p1];
                    parent[p1] = p2;
                } else {
                    size[p1] += size[p2];
                    parent[p2] = p1;
                }
                components--;
            }
        }
        return components;
    }
    
    // Path compression
    private int findParent(int[] parent, int i) {
        if (i == parent[i]) {
            return i;
        }
            
        return findParent(parent, parent[i]);
    }
}