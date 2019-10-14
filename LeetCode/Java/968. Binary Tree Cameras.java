// 968. Binary Tree Cameras
// 树形 DP，注意有三种状态！根源来自于每个节点与其父母、儿子都有联系，共三层的关系
class Solution {
    public Map<TreeNode, int[]> map;
    public int minCameraCover(TreeNode root) {
        map = new HashMap<>();
        dfs(root);
        return Math.min(map.get(root)[1], map.get(root)[2]);
    }

    public void dfs(TreeNode root) {
        int[] dp = new int[3];

        // base case
        if (root == null) {
            dp[0] = dp[1] = 0;
            dp[2] = Integer.MAX_VALUE / 2;
            map.put(null, dp);
            return;
        }

        dfs(root.left);
        dfs(root.right);

        // Backtracking
        dp[0] = map.get(root.left)[1] + map.get(root.right)[1];

        dp[1] = Math.min(map.get(root.left)[2] + Math.min(map.get(root.right)[1], map.get(root.right)[2]),
                map.get(root.right)[2] + Math.min(map.get(root.left)[1], map.get(root.left)[2]));

        dp[2] = 1 + Math.min(map.get(root.left)[0], Math.min(map.get(root.left)[1], map.get(root.left)[2]))
                + Math.min(map.get(root.right)[0], Math.min(map.get(root.right)[1], map.get(root.right)[2]));

        map.put(root, dp);
    }
}

class Solution {
    // 类似滚动数组 sliding array，仅用左右子树的 dp 状态来更新当前根的 dp 状态
    public int minCameraCover(TreeNode root) {
        int[] result = dfs(root);
        return Math.min(result[1], result[2]);
    }

    // 监控数量的三种情形：
    // 1. 以当前节点为根，除自身以外，所有子孙节点都被覆盖；（第一种情况存在的意义在于，当根上有监控时，儿子可以不放监控也不被监控）
    // 2. 以当前节点为根，自身和所有子孙节点都被覆盖，但自身无监控；
    // 3. 以当前节点为根，自身和所有子孙节点都被覆盖，但自身有监控；
    public int[] dfs(TreeNode root) {
        if(root == null) {
            // 空节点的第三种情况是不存在的
            return new int[]{0, 0, Integer.MAX_VALUE / 2}; // 注意这里不能直接用 MAX_VALUE，之后会计算中溢出算出负整数导致错误
        }

        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        
        // backtracking
        // first scenario: no monitor on root, not cover root, but cover all children
        // there exist no monitor on left or right child
        int state0 = left[1] + right[1];
        // second scenario: no monitor on root, but cover root and children
        // there must exist 1 monitor on left or right child
        int state1 = Math.min(left[2] + Math.min(right[1], right[2]), 
                              right[2] + Math.min(left[1], left[2]));
        // third scenario: monitor on root, cover root and children
        int state2 = Math.min(left[0], Math.min(left[1], left[2])) + 
                     Math.min(right[0], Math.min(right[1], right[2])) + 1;
        return new int[]{state0, state1, state2};
    }
}

class Solution {
    // 贪心算法，把监控放在所有叶子的父亲上，同时去除所有被覆盖的节点
    int result = 0;
    public int minCameraCover(TreeNode root) {
        return (dfs(root) < 1 ? 1 : 0) + result;
    }

    // 叶子节点就返回0，在叶子的父亲放置监控就返回1，此节点无监控，但已经被覆盖就返回2
    public int dfs(TreeNode root) {
        int left = root.left == null ? 2 : dfs(root.left);
        int right = root.right == null ? 2 : dfs(root.right);
        if(left == 0 || right == 0) {
            result++;
            return 1;
        }

        return left == 1 || right == 1 ? 2 : 0;
    }
}
