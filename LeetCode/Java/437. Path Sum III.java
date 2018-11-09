// 437. Path Sum III
// 时间复杂度 O(n^2)，每个结点都要枚举起点和终点
class Solution {
    private int sum;
    // 去重
    Set<TreeNode> set;
    public int pathSum(TreeNode root, int sum) {
        this.sum = sum;
        set = new HashSet<>();
        return helper(root, sum, true);
    }
    
    // 对于一棵树的 pathSum，有四种可能：左右子树有 sum - root.val 的路径，左右子树有 sum 的路径
    // 但是如果对于每个结点都判断四次，会有重复情况：从当前结点的任意祖先结点都会递归到当前结点，然后从此结点开始计算一次 sum
    // 如 [-1,null,0,null,1,null,2,null,3]，3：本只有[0,1,2], [1,2], [3]，但从-1开始 0，1，3都能递归一次，从0开始0，1，3又都能递归一次，以此类推，3+3+2+2+1=11，重复计算8次
    private int helper(TreeNode root, int sum, boolean isStart) {
        if(root == null) {
            return 0;
        }
        
        if(sum == this.sum && isStart) {
            if(set.contains(root)) {
                return 0;
            } else {
                set.add(root);
            }
        }
        
        return ((sum == root.val) ? 1 : 0) 
                + helper(root.left, sum - root.val, false) + helper(root.right, sum - root.val, false) 
                + helper(root.left, this.sum, true) + helper(root.right, this.sum, true);
    }
}

class Solution {
    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        return pathSumFrom(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }

    private int pathSumFrom(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
            
        return (root.val == sum ? 1 : 0) + pathSumFrom(root.left, sum - root.val)
                + pathSumFrom(root.right, sum - root.val);
    }
}