// 297. Serialize and Deserialize Binary Tree
// BFS and DFS both work.
public class Codec {
    // BFS版本
    public String serialize(TreeNode root) {
        if (root == null) {
            return "[]";
        }

        // This is a list!!!
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);

        // i < queue.size() is correct here
        for (int i = 0; i < queue.size(); i++) {
            TreeNode node = queue.get(i);
            if (node == null) {
                continue;
            }
            queue.add(node.left);
            queue.add(node.right);
        }

        // remove empty nodes
        while (queue.get(queue.size() - 1) == null) {
            queue.remove(queue.size() - 1);
        }

        // In Java, String is a final and immutable class, which makes it the most
        // special. It cannot be inherited, and once created, we can not alter the
        // object.
        // We create a new object each time with +=
        // https://stackoverflow.com/questions/1635659
        // https://stackoverflow.com/questions/22536411
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(queue.get(0).val);
        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i) == null) {
                sb.append(",null");
            } else {
                sb.append(",");
                sb.append(queue.get(i).val);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public TreeNode deserialize(String data) {
        if (data.equals("[]")) {
            return null;
        }

        String[] vals = data.substring(1, data.length() - 1).split(",");

        // List!!!  
        List<TreeNode> queue = new ArrayList<>();

        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        queue.add(root);
        
        int parent = 0;
        boolean isLeftChild = true;
        for (int i = 1; i < vals.length; i++) {
            if (!vals[i].equals("null")) {
                TreeNode node = new TreeNode(Integer.parseInt(vals[i]));
                if (isLeftChild) {
                    queue.get(parent).left = node;
                } else {
                    queue.get(parent).right = node;
                }
                queue.add(node);
            }
            // 每遍历两个节点，就换一个父节点
            if (!isLeftChild) {
                parent++;
            }
            // vals中每个节点无论非空都需要在左右节点之间来回切换
            isLeftChild = !isLeftChild;
        }
        return root;
    }
}

public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        // BFS, -1000 <= Node.val <= 1000
        String res = "[";
        if (root == null) {
            res += "]";
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.val <= 1000) {
                    res += "" + node.val + ",";
                    if (node.left != null) {
                        queue.offer(node.left);
                    } else {
                        // replacement for null child
                        queue.offer(new TreeNode(1001));
                    }

                    if (node.right != null) {
                        queue.offer(node.right);
                    } else {
                        // replacement for null child
                        queue.offer(new TreeNode(1001));
                    }
                } else {
                    res += "null,";
                    // this is leaf, not have to push its 'null' child into queue
                }
                size--;
            }
        }

        return res.substring(0, res.length() - 1) + "]";
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("[]")) {
            return null;
        }

        String[] nodes = data.substring(1, data.length() - 1).split(",");

        Deque<TreeNode> queue = new ArrayDeque<>();
        TreeNode root = new TreeNode(Integer.valueOf(nodes[0]));
        queue.offer(root);

        int ptr = 1;
        while (ptr < nodes.length) {
            // add left and right child for the nodes in queue
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (ptr < nodes.length && !nodes[ptr].equals("null")) {
                    int val = Integer.valueOf(nodes[ptr]);
                    TreeNode left = new TreeNode(val);
                    node.left = left;
                    queue.offer(left);
                }

                ptr++;

                if (ptr < nodes.length && !nodes[ptr].equals("null")) {
                    int val = Integer.valueOf(nodes[ptr]);
                    TreeNode right = new TreeNode(val);
                    node.right = right;
                    queue.offer(right);
                }

                ptr++;

                size--;
            }
        }

        return root;
    }
}

public class Codec {
    // LeetCode官方给的前序遍历DFS版本，还有详细分析优化
    // 实际上输入输出的字符串是不带 "[]" 的
    public String rserialize(TreeNode root, String str) {
        if (root == null) {
            str += "null,";
        } else {
            str += str.valueOf(root.val) + ",";
            str = rserialize(root.left, str);
            str = rserialize(root.right, str);
        }
        return str;
    }

    public String serialize(TreeNode root) {
        return rserialize(root, "");
    }

    public TreeNode rdeserialize(List<String> l) {
        if (l.get(0).equals("null")) {
            l.remove(0);
            return null;
        }

        // valueOf 内部就用了 parseInt
        // 区别在于 parseInt 直接返回原始 int 类型数据；而 valueOf 又包装了下，返回 Integer 类型
        TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
        l.remove(0);
        root.left = rdeserialize(l);
        root.right = rdeserialize(l);

        return root;
    }

    public TreeNode deserialize(String data) {
        String[] data_array = data.split(",");
        List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
        return rdeserialize(data_list);
    }
}