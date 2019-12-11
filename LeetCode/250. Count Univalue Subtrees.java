// 250. Count Univalue Subtrees
class Solution {
    public int countUnivalSubtrees(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        // 其实没必要对左右子树都讨论一遍
        if(root.left != null && root.right != null) {
            if(isUnivalSubtree(root.left) && isUnivalSubtree(root.right) && 
               root.val == root.left.val && root.val == root.right.val) {
                return 1 + countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
            } else {
                return countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
            }
        } else if(root.left != null) {
            if(isUnivalSubtree(root.left) && root.val == root.left.val) {
                return 1 + countUnivalSubtrees(root.left);
            } else {
                return countUnivalSubtrees(root.left);
            }
        } else if(root.right != null) {
            if(isUnivalSubtree(root.right) && root.val == root.right.val) {
                return 1 + countUnivalSubtrees(root.right);
            } else {
                return countUnivalSubtrees(root.right);
            }
        } else {
            return 1;  
        }
    }
    
    private boolean isUnivalSubtree(TreeNode root) {
        if(root.left != null && root.right != null) {
            if(isUnivalSubtree(root.left) && isUnivalSubtree(root.right) && 
               root.val == root.left.val && root.val == root.right.val) {
                return true;
            } else {
                return false;
            }
        } else if(root.left != null) {
            if(isUnivalSubtree(root.left) && root.val == root.left.val) {
                return true;
            } else {
                return false;
            }
        } else if(root.right != null) {
            if(isUnivalSubtree(root.right) && root.val == root.right.val) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;  
        }
    }
}

class Solution {
    // 简化上个版本
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int count = isUnivalSubtree(root) ? 1 : 0;
        return count + countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
    }

    private boolean isUnivalSubtree(TreeNode root) {
        if (root.left != null && root.right != null) {
            if (isUnivalSubtree(root.left) && isUnivalSubtree(root.right) && root.val == root.left.val
                    && root.val == root.right.val) {
                return true;
            } else {
                return false;
            }
        } else if (root.left != null) {
            if (isUnivalSubtree(root.left) && root.val == root.left.val) {
                return true;
            } else {
                return false;
            }
        } else if (root.right != null) {
            if (isUnivalSubtree(root.right) && root.val == root.right.val) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}

class Solution {
    // 继续简化上个版本
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int count = isUnivalSubtree(root) ? 1 : 0;
        return count + countUnivalSubtrees(root.left) + countUnivalSubtrees(root.right);
    }

    // 简化
    private boolean isUnivalSubtree(TreeNode root) {
        boolean result = true;
        if (root.left != null) {
            result &= root.val == root.left.val;
            result &= isUnivalSubtree(root.left);
        }
        if (root.right != null) {
            result &= root.val == root.right.val;
            result &= isUnivalSubtree(root.right);
        }
        return result;
    }
}