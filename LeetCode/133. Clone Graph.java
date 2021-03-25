// 133. Clone Graph
// BFS, faster than DFS
// 时间复杂度为 O(V + E)，空间复杂度为 O(V)
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return node;
        }

        Map<Node, Node> map = new HashMap<>();
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            // copy nodes
            Node copyNode = map.getOrDefault(cur, new Node(cur.val));
            if (!map.containsKey(cur)) {
                map.put(cur, copyNode);
            }
            for (Node neighbor : cur.neighbors) {
                // copy neighbors
                Node copyNeighbor = map.getOrDefault(neighbor, new Node(neighbor.val));
                if (!map.containsKey(neighbor)) {
                    // first time met this neighbor
                    map.put(neighbor, copyNeighbor);
                    queue.offer(neighbor);
                }
                // copy edges
                if (!copyNode.neighbors.contains(copyNeighbor)) {
                    copyNode.neighbors.add(copyNeighbor);
                }
            }
        }

        return map.get(node);
    }
}

// DFS
class Solution {
    private Map<Node, Node> map = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) {
            return node;
        }

        Node copyNode = map.getOrDefault(node, new Node(node.val));
        map.put(node, copyNode);
        
        for (Node neighbor : node.neighbors) {
            Node copyNeighbor = map.getOrDefault(neighbor, new Node(neighbor.val));
            if (copyNode.neighbors.contains(copyNeighbor)) {
                continue;
            }

            copyNode.neighbors.add(copyNeighbor);
            map.put(neighbor, copyNeighbor);
            cloneGraph(neighbor);
        }
        
        return copyNode;
    }
}

// DFS
class Solution {
    public Node cloneGraph(Node node) {
        return dfs(node, new HashMap<>());
    }

    // Deep copy each node
    private Node dfs(Node node, Map<Node, Node> map) {
        if (node == null) {
            return node;
        }

        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node copyNode = new Node(node.val);
        map.put(node, copyNode);

        for (Node neighbor : node.neighbors) {
            copyNode.neighbors.add(dfs(neighbor, map));
        }

        return copyNode;
    }
}