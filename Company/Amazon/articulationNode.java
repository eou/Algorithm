// https://www.acwing.com/blog/content/376
// https://www.cnblogs.com/nullzx/p/7968110.html
// https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
// Section 5.9.2 of The Algorithm Design Manual

// Tarjan
// dnf数组的下标表示顶点的编号，数组中的值表示该顶点在DFS中的遍历顺序(或者说时间戳)，每访问到一个未访问过的顶点，访问顺序的值（时间戳）就增加1。
// 子顶点的dfn值一定比父顶点的dfn值大（但不一定恰好大1，比如父顶点有两个及两个以上分支的情况）。在访问一个顶点后，它的dfn的值就确定下来了，不会再改变
// low数组的下标表示顶点的编号，数组中的值表示DFS中该顶点不通过父顶点能访问到的祖先顶点中最小的顺序值（或者说时间戳）
// 每个顶点初始的low值和dfn值应该一样

// 割点：
//（非根节点）判断顶点U是否为割点，用U顶点的dnf值和它的所有的孩子顶点的low值进行比较，
// 如果存在至少一个孩子顶点V满足low[v] >= dnf[u]，就说明顶点V访问顶点U的祖先顶点，必须通过顶点U，而不存在顶点V到顶点U祖先顶点的其它路径，所以顶点U就是一个割点。对于没有孩子顶点的顶点，显然不会是割点。
// 1) u is root of DFS tree and it has at least two children.
// 2) u is not root of DFS tree and it has a child v such that no vertex in subtree rooted with v has a back edge to one of the ancestors (in DFS tree) of u.
import java.util.*;

class Solution {
    public static int time = 0; 
    // O(V+E)
    private static List<Integer> getCriticalNodes(int[][] edges, int numEdges, int numNodes) {
        // init graph
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < numNodes; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // cut-node set
        Set<Integer> res = new HashSet<>();
        int[] low = new int[numNodes];
        int[] dfs = new int[numNodes];
        int parent[] = new int[numNodes];
        Arrays.fill(dfs, -1);
        Arrays.fill(parent, -1);
        // start running tarjan
        time = 0;
        for (int i = 0; i < numNodes; i++) {
            if (dfs[i] == -1) {
                tarjan(graph, low, dfs, parent, i, res);
            }
        }
        return new ArrayList<>(res);
    }

    private static void tarjan(Map<Integer, Set<Integer>> graph, int[] low, int[] dfs, int[] parent, int cur, Set<Integer> res) {
        int children = 0;
        // init timestamp
        dfs[cur] = low[cur] = ++time;
        // accessing neighbors
        for (int nb : graph.get(cur)) {
            if (dfs[nb] == -1) {
                // not traverse before
                children++;
                parent[nb] = cur;
                tarjan(graph, low, dfs, parent, nb, res);
                low[cur] = Math.min(low[cur], low[nb]);
                // root or not
                // if root, should have 1 more children unless it must not be a cut node
                // if not root and low[nb] >= dfs[cur], which means nb cannot accessing any cur's ancestors without going through cur node
                if ((parent[cur] == -1 && children > 1) || (parent[cur] != -1 && low[nb] >= dfs[cur])) {
                    res.add(cur);
                }
            } else if (nb != parent[cur]) {
                // else is enough here
                // nb != parent[cur] is a pruning
                // not current node's parent
                low[cur] = Math.min(low[cur], dfs[nb]);
            }
        }
    }

    // brute-force, O(V*(V+E))
    public static List<Integer> getCriticalNodes2(int[][] edges, int numEdges, int numNodes) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            // i is the edge I'm ignoring
            if (!traverse(edges, i, numNodes)) {
                res.add(i);
            }
        }

        return res;
    }

    // check if all vertices can be visited without the edges starting from the given vertex
    public static boolean traverse(int[][] e, int v, int numNodes) {
        HashSet<Integer> visitedVertices = new HashSet<>();
        boolean alreadyAdd = false;

        for (int i = 0; i < e.length; i++) {
            // ignores edges that have v as starting or ending point
            if (e[i][0] == v || e[i][1] == v)
                continue;

            // adding any edge as the starting point, both vertices
            // will fail if the edges are self edges
            if (!alreadyAdd) {
                visitedVertices.add(e[i][0]);
                visitedVertices.add(e[i][1]);
                alreadyAdd = true;
            }

            // if the next edge has one of the edges already in the set, I can visit it
            if (visitedVertices.contains(e[i][0]) || visitedVertices.contains(e[i][1])) {
                visitedVertices.add(e[i][0]);
                visitedVertices.add(e[i][1]);
            }
        }

        // if total visited elements equals all vertices - removed vertice
        return visitedVertices.size() == numNodes - 1;
    }

    public static void main(String[] args) {
        int numNodes = 7;
        int numEdges = 7;
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 }, { 2, 5 }, { 5, 6 }, { 3, 4 } };
        System.out.println(getCriticalNodes(edges, numEdges, numNodes)); // [2,3,5]
    }
}