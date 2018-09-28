// 679. 24 Game
class Solution {
    // 4个数字所有的计算方法共9216种，所以理论上要遍历所有组合来检查是否合法，必然是DFS
    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int n : nums) {
            list.add((double) n);
        }
        return helper(list);
    }
    
    private boolean helper(List<Double> nums) {
        if (nums.size() == 1) {
            return Math.abs(nums.get(0) - 24) < 1e-6; //精确度不一定
        }
        
        for (int i = 0; i < nums.size(); ++i) {
            for (int j = i + 1; j < nums.size(); ++j) {
                // save remaining numbers in order to calculate in next recursion
                List<Double> tmp = new ArrayList<>();
                for (int k = 0; k < nums.size(); ++k) {
                    if (k != i && k != j) {
                        tmp.add(nums.get(k));
                    }
                }
                
                // calculate
                for (int k = 0; k < 6; ++k) {
                    double n = 0; // 注意必须初始化，double n; 会报错
                    if (k == 0) {
                        n = nums.get(i) + nums.get(j);
                    } else if (k == 1) {
                        n = nums.get(i) * nums.get(j);
                    } else if (k == 2) {
                        n = nums.get(i) - nums.get(j);
                    } else if (k == 3) {
                        n = nums.get(j) - nums.get(i);
                    } else if (k == 4 && nums.get(j) != 0) {
                        n = nums.get(i) / nums.get(j);
                    } else if (nums.get(i) != 0) {
                        n = nums.get(j) / nums.get(i);
                    }
                    tmp.add(n);
                    // 如果这种组合可以，就返回true
                    if (helper(tmp)) {
                        return true;
                    }
                    tmp.remove(n);
                }
            }
        }
        
        return false;
    }
}