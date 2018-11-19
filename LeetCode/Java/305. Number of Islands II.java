// 305. Number of Islands II
// 初始化的时间复杂度为 O(N)，按大小/秩合并之后每次查找操作为 O(logN)，路径压缩之后每次查找操作 amortized O(1)
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
    
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};  
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> results = new ArrayList<>();
        if(m == 0 || n == 0 || positions.length == 0) {
            return results;
        }

        UnionFind uf = new UnionFind(m, n);
        for(int[] position : positions) {
            int x = position[0];
            int y = position[1];
            uf.add(x * n + y);
            for(int i = 0; i < 4; i++) {
                if(withinRange(x + dx[i], y + dy[i], m, n) && uf.parent[(x + dx[i]) * n + y + dy[i]] != -1) {
                    uf.unite(x * n + y, (x + dx[i]) * n + y + dy[i]);
                }
            }
            results.add(uf.unions);
        }
        
        return results;
    }
    
    public boolean withinRange(int i, int j, int m, int n) {
        if(i >= 0 && i < m && j >= 0 && j < n) {
            return true;
        } else {
            return false;
        }
    }
}