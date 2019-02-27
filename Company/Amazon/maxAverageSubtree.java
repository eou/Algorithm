import java.util.ArrayList;

class maxAverageSubtree {
    class ComponentNode {
        public int value;
        public ArrayList<ComponentNode> components;
        public ComponentNode() {
            components = new ArrayList<ComponentNode>();
        }
        public ComponentNode(int numOfLinesChanged) {
            this.value = numOfLinesChanged;
            this.components = new ArrayList<ComponentNode>();
        }
    }

    class Auxiliary {
       ComponentNode node;
       int sum;
       int size;
       public Auxiliary(ComponentNode node, int sum, int size) {
           this.node = node;
           this.sum = sum;
           this.size = size;
       }
    }

    public Auxiliary aux = null;

    public ComponentNode solution(ComponentNode root) {
        if (root == null) {
            return null;
        }

        Auxiliary aux = helper(root);
        return aux.node;
    }

    public Auxiliary helper(ComponentNode root) {
        if (root == null) {
            return new Auxiliary(null, 0, 0);
        }

        ArrayList<Auxiliary> results = new ArrayList<>();
        for (ComponentNode node : root.components) {
            results.add(helper(node));
        }

        int componentSum = 0;
        int componentSize = 0;
        for (Auxiliary a : results) {
            componentSum += a.sum;
            componentSize += a.size;
        }

        Auxiliary current = new Auxiliary(root, componentSum + root.value, componentSize + 1);
        if (aux == null || current.sum * aux.size > current.size * aux.sum) {
            if (current.size > 1) {
                aux = current;
            }
        }

        return current;
    }
 
    public static void main(String[] args) {

    }
}