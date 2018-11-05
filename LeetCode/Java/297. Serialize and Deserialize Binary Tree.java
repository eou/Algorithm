// 297. Serialize and Deserialize Binary Tree
public class Codec {
    // BFS版本
    public String serialize(TreeNode root) {
        if (root == null) {
            return "[]";
        }

        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        queue.add(root);

        // 这就是层次遍历的while循环，写成了for循环
        for (int i = 0; i < queue.size(); i++) {
            TreeNode node = queue.get(i);
            if (node == null) {
                continue;
            }
            queue.add(node.left);
            queue.add(node.right);
        }

        // 去掉最后一层的空节点
        while (queue.get(queue.size() - 1) == null) {
            queue.remove(queue.size() - 1);
        }

        // 在Java中String是一种不可改变的对象，无法进行引用传递
        // 对String对象的任何改变都不影响到原对象，相关的任何change操作都会生成新的对象；而StringBuilder不会
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
        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        queue.add(root);
        int index = 0;
        boolean isLeftChild = true;
        for (int i = 1; i < vals.length; i++) {
            if (!vals[i].equals("null")) {
                TreeNode node = new TreeNode(Integer.parseInt(vals[i]));
                if (isLeftChild) {
                    queue.get(index).left = node;
                } else {
                    queue.get(index).right = node;
                }
                queue.add(node);
            }
            // 每遍历两个节点，就换一个父节点
            if (!isLeftChild) {
                index++;
            }
            // vals中每个节点无论非空都需要在左右节点之间来回切换
            isLeftChild = !isLeftChild;
        }
        return root;
    }
}

public class Codec {
    // LeetCode官方给的前序遍历DFS版本，还有详细分析优化
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

        // valueOf内部就用了parseInt
        // 区别在于parseInt直接返回原始int类型数据；而valueOf又包装了下，返回Integer类型
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