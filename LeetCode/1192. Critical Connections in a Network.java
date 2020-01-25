// 1192. Critical Connections in a Network
// https://www.acwing.com/blog/content/376
// https://www.acwing.com/solution/LeetCode/content/4575
// https://leetcode.jp/leetcode-1192-critical-connections-in-a-network-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
// Testcases do not include duplicate edges
// Same code may be AC or TLE at different time on LeetCode server !!!

// tarjan, build graph using arraylist or array not HASHMAP!!!
class Solution {
    public static int time = 0; // dfs visit timestamp

    public static List<List<Integer>> bridges;
    public static List<List<Integer>> graph;
    public static int[] dfs;
    public static int[] low;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        graph = new ArrayList<>();
        // build graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (List<Integer> con : connections) {
            graph.get(con.get(0)).add(con.get(1));
            graph.get(con.get(1)).add(con.get(0));
        }

        bridges = new ArrayList<>();
        dfs = new int[n];
        low = new int[n]; // dont need to be initialized
        Arrays.fill(dfs, -1);

        tarjan(0, 0);

        return bridges;
    }

    private static void tarjan(int cur, int parent) {
        dfs[cur] = low[cur] = ++time;
        for (int nb : graph.get(cur)) {
            // if unvisited yet
            if (dfs[nb] == -1) {
                tarjan(nb, cur);
                low[cur] = Math.min(low[cur], low[nb]);

                // !!! Verify bridges
                if (low[nb] > dfs[cur]) {
                    bridges.add(new ArrayList<>(Arrays.asList(cur, nb)));
                }
            } else if (nb != parent) {
                low[cur] = Math.min(low[cur], dfs[nb]);
            }
        }
    }
}

// similar with tarjan, dfs function return low but not store value into low[] array
class Solution {
    List<List<Integer>> res;
    int[] dnf;  // store dfs timestamp
    List<Integer>[] graph;
    int time = 0;  // timestamp

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        dnf = new int[n];
        Arrays.fill(dnf, -1);

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (List<Integer> con : connections) {
            graph[con.get(0)].add(con.get(1));
            graph[con.get(1)].add(con.get(0));
        }

        res = new ArrayList<>();
        dfs(0, 0);
        return res;
    }

    private int dfs(int cur, int parent) {
        dnf[cur] = ++time;
        int low = dnf[cur];
        for (int nb : graph[cur]) {
            if (nb == parent) { // cannot go back
                continue;
            }

            int low_nb; // dfs final depth from this neighbor
            if (dnf[nb] == -1) { // did not visited
                low_nb = dfs(nb, cur);
                // !!! Verify bridge
                if (low_nb > dnf[cur]) {
                    res.add(new ArrayList<>(Arrays.asList(cur, nb)));
                }
            } else {
                low_nb = dnf[nb];
            }
            low = Math.min(low, low_nb);
        }
        return low;
    }
}

// faster tarjan implementation, using array storing graph
class Solution {
    int edgeIndex = 0;
    int[] to;
    int[] next;
    int[] head;
    int[] low;
    int[] dfs;
    int time = -1;
    List<List<Integer>> res = new ArrayList<>();

    private void addEdge(int u, int v) {
        to[edgeIndex] = v;
        next[edgeIndex] = head[u];
        head[u] = edgeIndex++;
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        low = new int[n];
        dfs = new int[n];
        int m = connections.size();
        to = new int[m * 2];
        head = new int[n];
        next = new int[m * 2];
        Arrays.fill(head, -1);
        Arrays.fill(next, -1);
        Arrays.fill(low, -1);
        Arrays.fill(dfs, -1);

        for (List<Integer> edge : connections) {
            int u = edge.get(0);
            int v = edge.get(1);
            addEdge(u, v);
            addEdge(v, u);
        }

        dfs(0, 0);
        return res;
    }

    private void dfs(int node, int parent) {
        if (dfs[node] != -1) {
            return;
        }

        low[node] = dfs[node] = ++time;
        for (int edge = head[node]; edge != -1; edge = next[edge]) {
            int next = to[edge];
            if (dfs[next] == -1) {
                dfs(next, node);
                low[node] = Math.min(low[node], low[next]);
                if (low[next] > dfs[node]) {
                    res.add(Arrays.asList(node, next));
                }
            } else if (next != parent) {
                low[node] = Math.min(low[node], dfs[next]);
            }
        }
    }
}

// tarjan using edge class, 100000 nodes TLE
class Solution {
    // undirected graph edge
    class Edge {
        int a;
        int b;
        public Edge(int a, int b) {
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            this.a = min;
            this.b = max;
        }
        
        @Override
        public boolean equals(Object other) {
            if(other == this) {
                return true;
            }
			
		    if(!(other instanceof Edge)) {
                return false;
            }
			
		    Edge e = (Edge)other;
		    return (e.a == a && e.b == b) || (e.b == a && e.a == b);
        }
        
        @Override
        public int hashCode() {
            // return (new Integer(a)).hashCode() * (new Integer(b)).hashCode(); // deprecated since Java 9
            return (Integer.valueOf(a)).hashCode() * (Integer.valueOf(b)).hashCode();
        }
        
        public List<Integer> toList() {
            return new ArrayList<>(Arrays.asList(a, b));
        }
    }
    
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        // build graph
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for (List<Integer> con : connections) {
            graph.get(con.get(0)).add(con.get(1));
            graph.get(con.get(1)).add(con.get(0));
        }
        
        Set<Edge> cutEdges = new HashSet<>();
        int[] dfs = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dfs, -1);
        Arrays.fill(low, -1);
        Arrays.fill(parent, -1);
        // for (int i = 0; i < n; i++) { // 可能有多个搜索树
        //     if (dfs[i] == -1) {
        //         tarjan(graph, low, dfs, parent, i, cutEdges);
        //     }
        // }
        tarjan(graph, low, dfs, parent, 0, cutEdges);
        
        List<List<Integer>> res = new ArrayList<>();
        for (Edge e : cutEdges) {
            res.add(e.toList());
        }
        return res;
    }
    
    public int time = 0;
    private void tarjan(Map<Integer, Set<Integer>> graph, int[] low, int[] dfs, int[] parent, int cur, Set<Edge> cutEdges) {
        dfs[cur] = low[cur] = ++time;
        for (int nb : graph.get(cur)) {
            if (dfs[nb] == -1) {
                parent[nb] = cur;
                tarjan(graph, low, dfs, parent, nb, cutEdges);
                low[cur] = Math.min(low[cur], low[nb]);
                // bridge
                if (low[nb] > dfs[cur]) {
                    cutEdges.add(new Edge(cur, nb));
                }
            } else if (nb != parent[cur]) {
                // 搜索树而不是父节点
                low[cur] = Math.min(low[cur], dfs[nb]);
            }
        }
    }
}

// without Edge class, 100000 nodes still TLE
class Solution {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        // build graph
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for (List<Integer> con : connections) {
            graph.get(con.get(0)).add(con.get(1));
            graph.get(con.get(1)).add(con.get(0));
        }
        
        Set<List<Integer>> cutEdges = new HashSet<>();
        int[] dfs = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dfs, -1);
        Arrays.fill(low, -1);
        Arrays.fill(parent, -1);
        // for (int i = 0; i < n; i++) { // 可能有多个搜索树
        //     if (dfs[i] == -1) {
        //         tarjan(graph, low, dfs, parent, i, cutEdges);
        //     }
        // }
        tarjan(graph, low, dfs, parent, 0, cutEdges);
        
        return new ArrayList<>(cutEdges);
    }
    
    public int time = 0;
    private void tarjan(Map<Integer, Set<Integer>> graph, int[] low, int[] dfs, int[] parent, int cur, Set<List<Integer>> cutEdges) {
        dfs[cur] = low[cur] = ++time;
        for (int nb : graph.get(cur)) {
            if (dfs[nb] == -1) {
                parent[nb] = cur;
                tarjan(graph, low, dfs, parent, nb, cutEdges);
                low[cur] = Math.min(low[cur], low[nb]);
                // bridge
                if (low[nb] > dfs[cur]) {
                    cutEdges.add(new ArrayList<>(Arrays.asList(cur, nb)));
                }
            } else if (nb != parent[cur]) {
                low[cur] = Math.min(low[cur], dfs[nb]);
            }
        }
    }
}

// simplest tarjan, still TLE
class Solution {
    public static int time = 0; // dfs visit timestamp

    public static List<List<Integer>> bridges;
    public static Map<Integer, Set<Integer>> graph;
    public static int[] dfs;
    public static int[] low;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        graph = new HashMap<>();
        // build graph
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for (List<Integer> con : connections) {
            graph.get(con.get(0)).add(con.get(1));
            graph.get(con.get(1)).add(con.get(0));
        }

        bridges = new ArrayList<>();
        dfs = new int[n];
        low = new int[n]; // dont need to be initialized
        Arrays.fill(dfs, -1);

        tarjan(0, 0);

        return bridges;
    }

    private static void tarjan(int cur, int parent) {
        // boolean flag = false;
        dfs[cur] = low[cur] = ++time;
        for (int nb : graph.get(cur)) {
            // if unvisited yet
            if (dfs[nb] == -1) {
                tarjan(nb, cur);
                low[cur] = Math.min(low[cur], low[nb]);

                // !!! Verify bridges
                if (low[nb] > dfs[cur]) {
                    bridges.add(new ArrayList<>(Arrays.asList(cur, nb)));
                }
            } else if (nb != parent) {
                low[cur] = Math.min(low[cur], dfs[nb]);
            }
            // duplicate edges might exist
            // else {
            //     if (nb == parent) {
            //         if (flag) {
            //             low[cur] = Math.min(low[cur], dfs[nb]);
            //         }
            //         flag = true;
            //     } else {
            //         low[cur] = Math.min(low[cur], dfs[nb]);
            //     }
            // }
        }
    }
}
