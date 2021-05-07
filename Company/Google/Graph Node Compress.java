// https://leetcode.com/discuss/interview-question/715406/Facebook-merge-graph-nodes
// The idea is to merge if the node has 1 neighbor and that neighbor indegree is 1

/**
 * Input: F -> U, A -> U, U -> B, B -> C, C -> E, C -> K, K -> M
 * Output: F -> UBC, A -> UBC, UBC -> E, UBC -> KM
 */
class test {
    static class Node {
        public String name;
        public List<Node> children;

        public Node(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }
    }

    private Map<Node, List<Node>> indegree(List<Node> nodes) {
        Map<Node, List<Node>> indegree = new HashMap<>();
        for (Node node : nodes) {
            indegree.putIfAbsent(node, new ArrayList<>());
            for (Node child : node.children) {
                indegree.putIfAbsent(child, new ArrayList<>());
                indegree.get(child).add(node);
            }
        }
        return indegree;
    }

    public List<Node> mergeNodes(List<Node> nodes) {
        Map<Node, List<Node>> indegree = indegree(nodes);

        for (Node node : nodes) {
            // dont need to worry about it not indegree graph
            if (!indegree.containsKey(node))
                continue;

            // node has only 1 child and indegree for its only child is 1
            if (node.children.size() == 1 && indegree.get(node.children.get(0)).size() == 1) {

                StringBuffer rename = new StringBuffer();
                rename.append(node.name);

                // Only child
                Node decend = node.children.get(0);

                // move forward as long as we see 1:1 chain
                while (indegree.get(decend).size() == 1) {
                    rename.append(decend.name);
                    indegree.remove(decend);

                    // make sure we only move forward if it's an only child
                    if (decend.children.size() != 1)
                        break;
                    decend = decend.children.get(0);
                }

                // Rename and update children
                Node merge = new Node(rename.toString());
                merge.children = decend.children;

                // incoming to merge (parents of node)
                indegree.put(merge, indegree.get(node));

                // incoming from merge (to grandchildren of node)
                for (Node grandchild : decend.children) {
                    indegree.put(grandchild, Arrays.asList(merge));
                }

                // set parents by removing current node, and add the new merged node
                for (Node parents : indegree.get(node)) {
                    parents.children.remove(node);
                    parents.children.add(merge);
                }

                // remove node and its only child
                indegree.remove(node);
                // indegree.remove(decend);
            }
        }

        return new ArrayList<>(indegree.keySet());
    }

    private void printGraph(List<Node> nodes) {
        for (Node node : nodes) {
            for (Node child : node.children) {
                System.out.println(node.name + " -> " + child.name);
            }
        }
    }

    /**
     *  C -> E
        C -> K
        B -> C
        U -> B
        F -> U
        A -> U
        K -> M

        UBC -> E
        UBC -> KM
        F -> UBC
        A -> UBC
     */
    public static void main(String[] args) {
        test t = new test();
        Node nodem = new Node("M");
        Node nodee = new Node("E");
        Node nodec = new Node("C");
        Node nodeb = new Node("B");
        Node nodeu = new Node("U");
        Node nodef = new Node("F");
        Node nodea = new Node("A");
        Node nodek = new Node("K");
        nodef.children.add(nodeu);
        nodea.children.add(nodeu);
        nodeu.children.add(nodeb);
        nodeb.children.add(nodec);
        nodec.children.add(nodee);
        nodec.children.add(nodek);
        nodek.children.add(nodem);
        List<Node> graph = Arrays.asList(nodem, nodee, nodec, nodeb, nodeu, nodef, nodea, nodek);

        t.printGraph(graph);

        t.printGraph(t.mergeNodes(graph));
    }
}