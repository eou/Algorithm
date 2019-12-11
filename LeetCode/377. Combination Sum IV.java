// 377. Combination Sum IV
// 这个只需返回 results 个数，所以之前一一返回 subset 的算法显然重复计算了很多，会TLE
// DFS, TLE
class Solution {
    int result = 0;
    public int combinationSum4(int[] nums, int target) {
        Arrays.sort(nums);
        dfs(nums, target, 0);
        return result;
    }
    
    public void dfs(int[] nums, int target, int sum) {
        if (sum == target) {
            ++result;
            return;
        }
        
        for (int i = 0; i < nums.length; ++i) {
            if (sum + nums[i] <= target) {
                dfs(nums, target, sum + nums[i]);
            }
        }
    }
}

class Solution {
    Map<Integer, Integer> map = new HashMap<>(); // sum : frequency

    public int combinationSum4(int[] nums, int target) {
        int count = 0; // result
        if (target < 0) {
            return 0;
        }
        if (target == 0) {
            return 1;
        }
        // 剪枝
        if (map.containsKey(target)) {
            return map.get(target);
        }
            
        for (int num : nums) {
            count += combinationSum4(nums, target - num);
        }

        map.put(target, count);
        return count;
    }
}

class Solution {
    // dynamic programming解法
    public int combinationSum4(int[] nums, int target) {
        // dp[i] means how many ways to get sum == i
        int[] dp = new int[target + 1];
        // initialization with smallest problems
        dp[0] = 1;
        // dp[0], dp[1], ...dp[target]
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (i >= num) {
                    dp[i] += dp[i - num];
                }
            }
        }
        return dp[target];
    }
}