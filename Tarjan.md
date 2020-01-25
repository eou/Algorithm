# Tarjan's strongly connected components algorithm

[Tarjan's strongly connected components algorithm](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm)

Tarjan's algorithm is an algorithm in graph theory for finding the strongly connected components of a directed graph. It runs in linear time, matching the time bound for alternative methods including Kosaraju's algorithm and the path-based strong component algorithm. 

It is usually used for finding **cut vertices** or articulation points and **bridges** in an undirected graph.

## Template

```java
class Tarjan {
    public static int time = 0; // dfs visit timestamp

    public static List<List<Integer>> bridges;
    public static List<Integer> cutVertices;

    public static Map<Integer, Set<Integer>> graph;
    public static int[] dfs; // dfn
    public static int[] low;
    public static int[] parent;

    public static void main(String[] args) {
        int numNodes = 7;
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 }, { 2, 5 }, { 5, 6 }, { 3, 4 } };
        
        // int numNodes = 4;
        // int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 1, 3 } };

        getCutVerticesAndBridges(edges, numNodes);

        System.out.println(cutVertices);
        System.out.println(bridges);
    }

    // initialization
    private static void getCutVerticesAndBridges(int[][] edges, int numNodes) {
        // build graph
        graph = new HashMap<>();
        for (int i = 0; i < numNodes; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        bridges = new ArrayList<>();
        cutVertices = new ArrayList<>();
        low = new int[numNodes]; // don't need to be initialized
        dfs = new int[numNodes];
        parent = new int[numNodes];
        Arrays.fill(dfs, -1);
        Arrays.fill(parent, -1);

        // start running tarjan
        // undirected graph might be built with multiple dfs trees
        // otherwise only 1 dfs entry is enough
   
        // tarjan(0);
        for (int i = 0; i < numNodes; i++) {
            if (dfs[i] == -1) {
                tarjan(i);
            }
        }
    }

    private static void tarjan(int cur) {
        int children = 0;
      	// boolean flag = false; // used for duplicate edges
        dfs[cur] = low[cur] = ++time;
        for (int nb : graph.get(cur)) {
            // if unvisited yet
            if (dfs[nb] == -1) {
                children++;
                parent[nb] = cur;
                tarjan(nb);
                // update low[cur] using low[nb]
                low[cur] = Math.min(low[cur], low[nb]);

                // !!! Verify cut vertices
                // root with 1 more children
                if (parent[cur] == -1 && children > 1) {
                    cutVertices.add(cur);
                }
                // non-root node and low[nb] >= dfs[cur]
                // which means start from this nb node, dfs cannot visit cur node's ancestors
                // without going through cur node (at most visit cur node itself)
                if (parent[cur] != -1 && low[nb] >= dfs[cur]) {
                    cutVertices.add(cur);
                }

                // !!! Verify bridges
                // which means start from this nb node, dfs cannot visit cur node itself and cur
                // node's ancestors
                // if edge(cur, nb) is removed, dfs cannot visit any cur node's ancestors
                // including cur node itself, thus edge(cur, nb) is a bridge
                if (low[nb] > dfs[cur]) {
                    bridges.add(new ArrayList<>(Arrays.asList(cur, nb)));
                }
            } else if (nb != parent[cur]) {
                // nb is part of cur node's dfs tree
                low[cur] = Math.min(low[cur], dfs[nb]);
            }
            // duplicate edges might exist, same result
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
  	
  	// version2
  	// pass 1 more argument to avoid using parent[] array
  	// call tarjan(0, 0) or tarjan(i, i) in for loop
  	private static void tarjan(int cur, int parent) {
        int children = 0;
        dfs[cur] = low[cur] = ++time;
        for (int nb : graph.get(cur)) {
            // if unvisited yet
            if (dfs[nb] == -1) {
                children++;
                tarjan(nb, cur);
                low[cur] = Math.min(low[cur], low[nb]);

                // !!! Verify cut vertices
                // root
                if (cur == parent && children > 1) {
                    cutVertices.add(cur);
                }
                if (cur != parent && low[nb] >= dfs[cur]) {
                    cutVertices.add(cur);
                }

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
```

## Note

Using array or list storing graph is much faster than using `HashMap`! (Test with LeetCode 1192)

```java
class Tarjan {
    public static void main(String[] args) {
        int numNodes = 7;
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 }, { 2, 5 }, { 5, 6 }, { 3, 4 } };

        getCutVerticesAndBridges(edges, numNodes);

        System.out.println(cutVertices);
        System.out.println(bridges);
    }

    public static int edgeIndex = 0;
    public static int[] to;
    public static int[] next;
    public static int[] head;
    public static int[] low;
    public static int[] dfs;
    public static int time = -1;

    public static List<List<Integer>> bridges;
    public static List<Integer> cutVertices;

    private static void addEdge(int u, int v) {
        to[edgeIndex] = v;
        next[edgeIndex] = head[u];
        head[u] = edgeIndex++;
    }

    public static void getCutVerticesAndBridges(int[][] edges, int n) {
        bridges = new ArrayList<>();
        cutVertices = new ArrayList<>();

        low = new int[n];
        dfs = new int[n];
        int m = edges.length;
        to = new int[m * 2];
        head = new int[n];
        next = new int[m * 2];
        Arrays.fill(head, -1);
        Arrays.fill(next, -1);
        Arrays.fill(low, -1);
        Arrays.fill(dfs, -1);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            addEdge(u, v);
            addEdge(v, u);
        }

        tarjan(0, 0);
    }

    private static void tarjan(int node, int parent) {
        if (dfs[node] != -1) {
            return;
        }

        int children = 0;
        low[node] = dfs[node] = ++time;
        for (int edge = head[node]; edge != -1; edge = next[edge]) {
            children++;
            int next = to[edge];
            if (dfs[next] == -1) {
                tarjan(next, node);
                low[node] = Math.min(low[node], low[next]);

                if (node == parent && children > 1) {
                    cutVertices.add(node);
                }
                if (node != parent && low[next] >= dfs[node]) {
                    cutVertices.add(node);
                }

                if (low[next] > dfs[node]) {
                    bridges.add(Arrays.asList(node, next));
                }
            } else if (next != parent) {
                low[node] = Math.min(low[node], dfs[next]);
            }
        }
    }
}
```

## Explanation

- dnf 数组的下标表示顶点的编号，数组中的值表示该顶点在 DFS 中的遍历顺序（或者说时间戳）。每访问到一个未访问过的顶点，访问顺序的值（时间戳）就增加 1。子顶点的 dfn 值一定比父顶点的 dfn 值大，但不一定恰好大1，比如父顶点有两个及两个以上分支的情况。在访问一个顶点后，它的 dfn 的值就确定下来了，不会再改变。
- low 数组的下标表示顶点的编号，数组中的值表示 DFS 中该顶点不通过父顶点能访问到的祖先顶点中最小的顺序值（或者说时间戳）。
- 每个顶点初始的 low 值和 dfn 值应该一样。

割点（非根节点）判断：如果存在至少一个孩子顶点 v 满足`low[v] >= dnf[u]`，就说明顶点 v 访问顶点 u 的祖先顶点，必须通过顶点u，而不存在顶点 v 到顶点 u 祖先顶点的其它路径，所以顶点 u 就是一个割点。

对于没有孩子顶点的顶点，显然不会是割点（如单独的根节点）。

1. u is root of DFS tree and it has at least two children.

2. u is not root of DFS tree and it has a child v such that no vertex in subtree rooted with v has a back edge to one of the ancestors (in DFS tree) of u.

割边：`low[v] > dnf[u]`就说明 v - u 是桥。

此算法时空复杂度都是$O(V + E)$.

## Reference

https://www.acwing.com/blog/content/376

https://www.cnblogs.com/nullzx/p/7968110.html

https://leetcode.com/problems/critical-connections-in-a-network

https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph

https://www.geeksforgeeks.org/bridge-in-a-graph/