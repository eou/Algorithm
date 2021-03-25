// 305. Number of Islands II
// 比 200. Number of Islands 多了一个逐步添加岛屿的操作
// 初始化的时间复杂度为 O(N)，按大小/秩合并之后每次查找操作为 O(logN)，路径压缩之后每次查找操作 amortized O(1)
// It takes O(m * n) to initialize UnionFind, 
// and O(L) to process positions where LL is the number of operations, m is the number of rows and n is the number of columns. 
// Note that Union operation takes essentially constant time[1] when UnionFind is implemented with both path compression and union by rank.
class Solution {
    class UnionFind {
        int[] parent;
        int[] size;
        int unions;
        
        public UnionFind(int m, int n) {
            parent = new int[n * m];
            size = new int[n * m];
            for(int i = 0; i < m; i++) {
                for(int j = 0; j < n; j++) {
                    parent[i * n + j] = -1;
                    size[i * n + j] = 1;
                }
            }
        }
        
        
        public int root(int i) {
            while(i != parent[i]) {
                parent[i] = parent[parent[i]]; // path compression
                i = parent[i];
            }
            return i;
        }
        
        public void unite(int i, int j) {
            int ri = root(i);
            int rj = root(j);
            if(ri == rj) {
                return;
            }
            
            if(size[ri] > size[rj]) {
                parent[rj] = ri;
                size[ri] += size[rj];
            } else {
                parent[ri] = rj;
                size[rj] += size[ri];
            }
            unions--;
        }
        
        public void add(int i) {
            if(parent[i] == -1) {
                parent[i] = i;
                unions++;
            }
        }
    }
    
    private int[] dx = {0, 0, 1, -1};
    private int[] dy = {1, -1, 0, 0};  

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        if(m == 0 || n == 0 || positions == null || positions.length == 0) {
            return res;
        }

        UnionFind uf = new UnionFind(m, n);
        for(int[] position : positions) {
            int x = position[0];
            int y = position[1];
            uf.add(x * n + y);
            for(int i = 0; i < 4; i++) {
                int x1 = x + dx[i];
                int y1 = y + dy[i];
                if (x1 >= 0 && x1 < m && y1 >= 0 && y1 < n) {
                    if (uf.parent[x1 * n + y1] != -1) {
                        uf.unite(x * n + y, x1 * n + y1);
                    }
                }
            }
            res.add(uf.unions);
        }
        
        return res;
    }
}

// HashMap, time complexity O(L^2), TLE
class Solution {
    private int[] dx = { 0, 0, 1, -1 };
    private int[] dy = { 1, -1, 0, 0 };

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        Map<Integer, Integer> land2id = new HashMap<>();
        int num_islands = 0;
        int island_id = 0;

        for (int[] pos : positions) {
            int r = pos[0], c = pos[1];

            if (land2id.containsKey(r * n + c)) {
                continue;
            }

            Set<Integer> overlap = new HashSet<>();

            for (int i = 0; i < 4; i++) {
                int x = r + dx[i];
                int y = c + dy[i];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    if (land2id.containsKey(x * n + y)) {
                        overlap.add(land2id.get(x * n + y));
                    }
                } 
            }

            if (overlap.isEmpty()) {
                // find a new island, no adjacent neighbor island
                ++num_islands;
                land2id.put(r * n + c, island_id++);
            } else {
                // find multiple neighbor islands
                int root_id = overlap.iterator().next();
                // union adjacent island connected by current position (uf.union)
                for (Integer p : land2id.keySet()) {
                    if (overlap.contains(land2id.get(p))) {
                        land2id.put(p, root_id);
                    }
                }
                land2id.put(r * n + c, root_id);

                ++num_islands;
                num_islands -= overlap.size();
            }

            res.add(num_islands);
        }

        return res;
    }
}