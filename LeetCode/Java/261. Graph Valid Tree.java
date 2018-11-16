// 261. Graph Valid Tree
class Solution {
    // 判断图是否是树需要两个条件：1. 边比点少一个（无环）；2. 所有点均连通
    public boolean validTree(int n, int[][] edges) {
        if(n == 0 || n - 1 != edges.length) {
            return false;
        }
        
        // nodes, edges => adjacent list 邻接表
        Map<Integer, Set<Integer>> graph = initialGraph(n, edges);
        
        Deque<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(0);
        visited.add(0);
        
        // bfs
        while(!queue.isEmpty()) {
            int node = queue.poll();
            for(Integer neighbor : graph.get(node)) {
                if(!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                } else {
                    continue;
                }
            }
        }
        
        return visited.size() == n;
    }
    
    private Map<Integer, Set<Integer>> initialGraph(int n, int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        
        for(int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }
        
        for(int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        return graph;
    }
}

class Solution {
    // 谈到连通性，必然可以用并查集 Union Find，这是一个简易版本
    public boolean validTree(int n, int[][] edges) {
        // initialize n isolated islands
        int[] nums = new int[n];
        Arrays.fill(nums, -1);

        // perform union find
        for (int[] edge : edges) {
            // 其实就是合并操作，可以单独写一个函数
            // if(!unite(nums, edge[0], edge[1])) {
            //     return false;
            // }

            int x = root(nums, edge[0]);
            int y = root(nums, edge[1]);

            // if two vertices happen to be in the same set, then there's a cycle
            if (x == y)
                return false;

            // union
            nums[x] = y;
        }

        return edges.length == n - 1;
    }

    private boolean unite(int nums[], int p, int q) {
        int pRoot = root(nums, p);
        int qRoot = root(nums, q);
        if(pRoot == qRoot) {
            return false;
        }

        nums[pRoot] = qRoot;
    }

    private int root(int nums[], int i) {
        // 由于这里将数组初始化为-1，如果 path compression 可能直接跳到 -1 导致返回的 i 不对
        while(nums[i] != -1) {
            i = nums[i];
        }
        return i;

        // 如果递归查找根，需要定义出口
        // if (nums[i] == -1) {
        //     return i;
        // }
        // return root(nums, nums[i]);
    }
}

class Solution {
    // Union Find 版本
    class unionFind {
        private int[] id;
        private int[] size;
        private int unions;

        public unionFind(int n) {
            unions = n;
            id = new int[n];
            size = new int[n];
            // initialize the root of each node to itself
            // initialize the size of each union to 1
            for (int i = 0; i < n; i++) {
                id[i] = i;
                size[i] = 1;
            }
        }

        public int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // path compression
                i = id[i];
            }
            return i;

            // 递归查找根
            // if (i == id[i]) {
            //     return i;
            // }
            // return root(id[i]);
        }

        public void unite(int p, int q) {
            if(connected(p, q)) {
                return;
            }

            int i = root(p);
            int j = root(q);
            if (size[i] < size[j]) { // if-else: weighted quick-union
                id[i] = j;
                size[j] += size[i];
            } else {
                id[j] = i;
                size[i] += size[j];
            }

            unions--;
        }

        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }
    }

    public boolean validTree(int n, int[][] edges) {
        unionFind uf = new unionFind(n);

        for (int[] edge : edges) {
            int p = edge[0], q = edge[1];
            if (uf.root(p) == uf.root(q)) {
                return false;
            }
            uf.unite(p, q);
        }

        return uf.unions == 1;
    }
}