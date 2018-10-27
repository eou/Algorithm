// 684. Redundant Connection
class Solution {
    // 典型的 Union Find，时间复杂度 O(n), 空间复杂度 O(n)
    int MAX_EDGE = 1005;
    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(MAX_EDGE);
        for(int[] e : edges) {
            if(!uf.union(e[0], e[1])) {
                return e;
            }
        }
        return new int[0];
    }
    
    class UnionFind {
        int[] parent;
        int[] rank;
        
        public UnionFind(int size) {
            parent = new int[size];
            for(int i = 0; i < size; i++) {
                parent[i] = i;
            }
            rank = new int[size];
        }
        
        // path compression
        public int find(int i) {
            if(parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }
        
        // union by rank
        public boolean union(int x, int y) {
            int rootx = find(x);
            int rooty = find(y);
            
            if(rootx != rooty) {
                if(rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx;
                } else if(rank[rooty] > rank[rootx]) {
                    parent[rootx] = rooty;
                } else {
                    parent[rooty] = rootx;
                    rank[rootx]++;
                }
            } else {
                return false;
            }
            
            return true;
        }
    }
}

class Solution {
    // DFS版本
    Set<Integer> visited = new HashSet<>();

    public int[] findRedundantConnection(int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        // 初始化的时候就要添加所有可能的点
        for (int i = 0; i <= edges.length * edges[0].length; i++) {
            graph.add(new ArrayList());
        }

        for (int[] edge : edges) {
            visited.clear();
            if (!graph.get(edge[0]).isEmpty() && !graph.get(edge[1]).isEmpty() && helper(graph, edge[0], edge[1])) {
                return edge;
            }
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        return new int[0];
    }

    private boolean helper(List<List<Integer>> graph, int source, int target) {
        if (!visited.contains(source)) {
            visited.add(source);
            if (source == target) {
                return true;
            }

            for (int neighbor : graph.get(source)) {
                if (helper(graph, neighbor, target)) {
                    return true;
                }
            }
        }
        return false;
    }
}