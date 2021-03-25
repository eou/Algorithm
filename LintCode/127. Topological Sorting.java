// 127. Topological Sorting
// 1. Calculate indegree for each vertex.
// 2. Find the first vertex of topo sort whose indegree is 0
// 3. BFS or DFS starting from first vertex
// 3-1. After traversing one vertex, "remove it from graph", reduce its neighbors' indegree
// 3-2. If a vertex's indegree becomes 0, it is the next one in topo sort

// BFS, recommend
public class Solution {
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> res = new ArrayList<>();

        if (graph == null || graph.size() == 0) {
            return res;
        }

        // Indegree of vertex V is the number of edges which are coming into the vertex V
        Map<DirectedGraphNode, Integer> inDegree = new HashMap<>();
        
        for (DirectedGraphNode vertex : graph) {
            for (DirectedGraphNode neighbor : vertex.neighbors) {
                Integer degree = inDegree.getOrDefault(neighbor, 0) + 1;
                inDegree.put(neighbor, degree);
            }
        }
        
        Deque<DirectedGraphNode> queue = new ArrayDeque<>();
        for (DirectedGraphNode vertex : graph) {
            // Find the vertex whose indegree is 0, which is head of topo sort
            if (!inDegree.containsKey(vertex)) {
                queue.offer(vertex);
            }
        }
        
        int num = 0;
        while (!queue.isEmpty()) {
            DirectedGraphNode vertex = queue.poll();
            res.add(vertex);
            num++;
            for (DirectedGraphNode neighbor : vertex.neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                // next vertex of topo sort
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // this graph has a complete topo sort
        if (num == graph.size()) {
            return res;
        }
        
        return new ArrayList<>();
    }
}

// DFS, same idea
public class Solution {
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> res = new ArrayList<>();

        if (graph == null || graph.size() == 0) {
            return res;
        }

        Map<DirectedGraphNode, Integer> inDegree = new HashMap<>();

        for (DirectedGraphNode vertex : graph) {
            for (DirectedGraphNode neighbor : vertex.neighbors) {
                Integer degree = inDegree.getOrDefault(neighbor, 0) + 1;
                inDegree.put(neighbor, degree);
            }
        }

        Deque<DirectedGraphNode> queue = new ArrayDeque<>();
        for (DirectedGraphNode vertex : graph) {
            // Find the vertex whose indegree is 0, which is head of topo sort
            // use graph.size() != res.size() to optimize, not neccessary
            if (!inDegree.containsKey(vertex) && graph.size() != res.size()) {
                dfs(vertex, res, inDegree);
            }
        }

        return res;
    }

    private void dfs(DirectedGraphNode vertex, ArrayList<DirectedGraphNode> res, Map<DirectedGraphNode, Integer> inDegree) {
        res.add(vertex);

        if (vertex.neighbors.size() == 0) {
            return;
        }

        for (DirectedGraphNode neighbor : vertex.neighbors) {
            inDegree.put(neighbor, inDegree.get(neighbor) - 1);
            // next vertex of topo sort
            if (inDegree.get(neighbor) == 0) {
                dfs(neighbor, res, inDegree);
            }
        }
    }
}