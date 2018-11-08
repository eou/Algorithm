/**
 * Shuffle Binary Tree.java
 * 打乱一棵二叉树的结点，保持结构不变
 * 可能有些结点值仍没变，需要多 shuffle 几次确认一下
 */
class Solution {
    // 保存二叉树结构，重新生成一棵树
    // 这个 WrapperNode 类完全可以用原 TreeNode 类代替
    class WrapperNode {
        TreeNode node;
        WrapperNode left;
        WrapperNode right;

        public WrapperNode(TreeNode node) {
            this.node = node;
            this.left = null;
            this.right = null;
        }
    }

    public TreeNode shuffleTreeNodes(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        WrapperNode wroot = buildWrapperTree(root, list);
        Collections.shuffle(list);
        return shuffleTree(wroot, list.iterator());
    }
    
    private WrapperNode buildWrapperTree(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return null;
        }
        WrapperNode wroot = new WrapperNode(root);
        list.add(root);
        WrapperNode left = buildWrapperTree(root.left, list);
        WrapperNode right = buildWrapperTree(root.right, list);
        wroot.left = left;
        wroot.right = right;
        return wroot;
    }

    private TreeNode shuffleTree(WrapperNode wroot, Iterator<TreeNode> it) {
        if (wroot == null) {
            return null;
        }
        TreeNode root = it.next();
        TreeNode left = shuffleTree(wroot.left, it);
        TreeNode right = shuffleTree(wroot.right, it);
        root.left = left;
        root.right = right;
        return root;
    }
}

class Solution {
    // 上一个版本的改进，不定义新的类
    public TreeNode shuffleTreeNodes(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        // 建一棵与原树结构完全一样的树，结点值不用考虑，原结点保存在 list 即可
        TreeNode structureRoot = buildStructure(root, list);
        Collections.shuffle(list);
        // 用原结点按照结构树重构一棵树
        return shuffleTree(structureRoot, list.iterator());
    }

    private TreeNode buildStructure(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return null;
        }
        
        list.add(root);
        TreeNode structureRoot = new TreeNode(-1);
        structureRoot.left = buildStructure(root.left, list);
        structureRoot.right = buildStructure(root.right, list);
        return structureRoot;
    }

    private TreeNode shuffleTree(TreeNode structureRoot, Iterator<TreeNode> it) {
        if (structureRoot == null) {
            return null;
        }

        TreeNode root = it.next();
        root.left = shuffleTree(structureRoot.left, it);
        root.right = shuffleTree(structureRoot.right, it);
        return root;
    }
}

class Solution {
    // in-place 改动，只改变结点值
    public TreeNode shuffleTreeNodes(TreeNode root) {
        List<TreeNode> list = storeNode(root);
        Collections.shuffle(list);
        return shuffleNode(root, list);
    }

    private List<TreeNode> storeNode(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            result.add(new TreeNode(node.val)); // 不能直接放入原结点
            node = node.right;
        }
        return result;
    }

    private TreeNode shuffleNode(TreeNode root, List<TreeNode> list) {
        Deque<TreeNode> stack = new ArrayDeque<>();

        TreeNode node = root;
        int cnt = 0;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node.val = list.get(cnt++).val;
            node = node.right;
        }

        return root;
    }
}