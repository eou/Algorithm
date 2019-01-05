// 968. Binary Tree Cameras
class Solution {
    // dynamic programming
    public int minCameraCover(TreeNode root) {
        int[] result = helper(root);
        return Math.min(result[1], result[2]);
    }

    // 监控数量的三种情形：
    // 1. 以当前节点为根，除自身以外，所有子孙节点都被覆盖；
    // 2. 以当前节点为根，自身和所有子孙节点都被覆盖，但自身无监控；
    // 3. 以当前节点为根，自身和所有子孙节点都被覆盖，但自身有监控；
    public int[] helper(TreeNode root) {
        if(root == null) {
            return new int[]{0, 0, Integer.MAX_VALUE / 2}; // 注意这里不能直接用 MAX_VALUE，之后会计算中溢出算出负整数导致错误
        }

        int[] left = helper(root.left);
        int[] right = helper(root.right);
        
        // 第一种情形：除自身以外所有子孙节点都被覆盖，即左右子节点无监控
        int state0 = left[1] + right[1];
        // 第二种情况：自身无监控，自身和所有子孙节点都被覆盖，因此左右节点必须至少有一个节点上有监控
        int state1 = Math.min(left[2] + Math.min(right[1], right[2]), 
                              right[2] + Math.min(left[1], left[2]));
        // 第三种情况：自身有监控，自身和所有子孙节点都被覆盖，因此不需要考虑左右子节点是否已经被覆盖，考虑所有情况的最小值即可
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
