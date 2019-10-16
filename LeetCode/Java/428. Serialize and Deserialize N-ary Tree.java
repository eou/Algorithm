// 428. Serialize and Deserialize N-ary Tree
class Codec {
    public String serialize(Node root) {
        if(root == null) {
            return "";
        }
        
        List<String> list = new ArrayList<>();
        rserialize(root, list);
        return String.join(",", list);
    }

    public Node deserialize(String data) {
        if(data == "") {
            return null;
        }

        String[] data_array = data.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(data_array));
        return rdeserialize(list);
    }

    private void rserialize(Node root, List<String> list) {
        if(root == null) {
            return;
        }

        list.add(String.valueOf(root.val));
        // 需要把儿子个数序列化，解序列化的时候要用
        list.add(String.valueOf(root.children.size()));
        for(Node child : root.children) {
            rserialize(child, list);
        }
    }

    public Node rdeserialize(List<String> list) {
        Node root = new Node();
        root.val = Integer.parseInt(list.get(0));
        list.remove(0);
        int size = Integer.parseInt(list.get(0));
        list.remove(0);
        root.children = new ArrayList<Node>();
        for(int i = 0; i < size; i++) {
            root.children.add(rdeserialize(list));
        }

        return root;
    }
}