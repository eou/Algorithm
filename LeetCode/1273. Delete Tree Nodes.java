// 1273. Delete Tree Nodes
// The author have assumed that parent[i] < i holds for all 0 <= i < nodes,
// but currently this assumption removed thus we have testcases for parent[i] >= i
// It is a graph problem!!!
// brute-force, tree DFS
class Solution {
    public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
        // Cant determine node depth based on its index
        // Only DFS each node for each subtree
        int[] subtreeSum = value.clone();
        
        // DFS each subtree's sum
        for (int i = 0; i < nodes; i++) {
            int ptr = parent[i];
            while (ptr >= 0) {
                subtreeSum[ptr] += value[i];
                ptr = parent[ptr];
            }
        }
        
        boolean[] remove = new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            if (subtreeSum[i] == 0) {
                remove[i] = true;
            }
            
            // Ancestors
            int ptr = parent[i];
            while (ptr >= 0) {
                if (subtreeSum[ptr] == 0) {
                    remove[i] = true;
                    break;
                }
                ptr = parent[ptr];
            }
        }
        
        int res = nodes;
        for (int i = 0; i < nodes; i++) {
            if (remove[i]) {
                res--;
            }
        }
        return res;
    }
}

// brute-force
class Solution {
    public List<Integer>[] graph; // 记录每个节点的子节点
    public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
        graph = new List[nodes];
        for (int i = 0; i < nodes; i++) {
            graph[i] = new ArrayList<>();
        }

        // 统计每个节点的子节点
        for (int i = 0; i < nodes; i++) {
            if (parent[i] >= 0) {
                graph[parent[i]].add(i);
            }
        }

        // 删除和为 0 的子树
        remove(value, parent, 0);

        // 统计删除后剩余节点数量，返回。
        return count(0, value);
    }

    public int remove(int[] value, int[] parent, int index) {
        int sum = value[index];
        for (int child : graph[index]) {
            sum += remove(value, parent, child);
        }
        if (sum == 0) {
            graph[index].clear();
            value[index] = 0;
        }
        return sum;
    }

    public int count(int index, int[] value) {
        // could be sum = 0 or leaf nodes
        if (graph[index].size() == 0) {
            return value[index] == 0 ? 0 : 1;
        }

        int res = 1;
        for (int child : graph[index]) {
            res += count(child, value);
        }
        return res;
    }
}

// dfs
class Solution {
    public int deleteTreeNodes(int n, int[] parent, int[] value) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
            
        for (int i = 0; i < n; i++) {
            if (parent[i] != -1) {
                graph.get(parent[i]).add(i);
            }
        }

        return dfs(graph, value, 0)[1];
    }

    // return {sum, children}
    private int[] dfs(List<List<Integer>> graph, int[] value, int root) {
        int sum = value[root];
        int cntRemainNodes = 1;

        for (int child : graph.get(root)) {
            int[] temp = dfs(graph, value, child);
            sum += temp[0];
            cntRemainNodes += temp[1];
        }

        if (sum == 0) {
            cntRemainNodes = 0; // Don't count nodes of this subtree
        }
        return new int[] { sum, cntRemainNodes };
    }
}

// These two solutions shall not pass since they assume parent[i] < i which is wrong currently
class Solution {
    public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
        for (int i = nodes - 1; i > 0; i--) {
            // don't need to add value to itself since value[i] is the value itself
            value[parent[i]] += value[i];
        }
        
        int res = nodes;
        // if parent node is 1, set children node as 1
        int[] isZero = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            if ((parent[i] >= 0 && isZero[parent[i]] == 1) || value[i] == 0) {
                isZero[i] = 1;
                res--;
            }
        }
        
        return res;
    }
}

class Solution {
    public int deleteTreeNodes(int n, int[] parent, int[] value) {
        int[] res = new int[n];
        for (int i = n - 1; i > 0; --i) {
            value[parent[i]] += value[i];
            res[parent[i]] += value[i] != 0 ? res[i] + 1 : 0;
        }
        return value[0] != 0 ? res[0] + 1 : 0;
    }
}