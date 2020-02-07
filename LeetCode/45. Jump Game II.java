// 45. Jump Game II
// DFS，时间复杂度为 O(2^n)，会 TLE
class Solution {
    int min = Integer.MAX_VALUE;
    public int jump(int[] nums) {
        helper(nums, 0, 0);
        return min;
    }
    
    private boolean helper(int[] nums, int start, int step) {
        if(start == nums.length - 1) {
            min = Math.min(step, min);
        }
        
        int furthest = Math.min(nums[start] + start, nums.length - 1);
        for(int i = start + 1; i <= furthest; i++) {
            if(helper(nums, i, step + 1)) {
                return true;
            }
        }
        return false;
    }
}

// DP, O(n^2)，会 TLE
class Solution {
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= nums[i] + i && j < nums.length; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
        }

        return dp[nums.length - 1];
    }
}

/**
 * BFS, O(n)
 * nodes in step i are all the nodes that can be reached in ith jump
 * [2, 3, 1, 0, 4] => 
 * step 1: 0,      start: 1, end: 2
 * step 2: 1, 2,   start: 3, end: 4
 * step 3: 3, 4,   
 */
class Solution {
    public int jump(int[] nums) {
        // for [0]
        if (nums.length <= 1) {
            return 0;
        }

        // for step i, nums[left] to nums[right] will be tested (left -> right)
        // for step i + 1, right will be set as the furthest point we can reach on step i, left will be set as right + 1 (right + 1 -> furthest)
        // !! we can make sure that all points in (end + 1 -> furthest) can be reached on step i, thus we can expend our steps based on step i
        for (int step = 1, left = 0, right = 0, furthest = 0; 
            step <= nums.length; 
            step++, left = right + 1, right = furthest) {
            // cannot get this start point
            if (left > right) {
                break;
            }

            // furthest point we can reach at current loop
            furthest = 0;
            // test all point in nums(left, right)
            for (int i = left; i <= right; i++) {
                // reach end
                if (nums[i] + i >= nums.length - 1) {
                    return step;
                }
                furthest = Math.max(furthest, nums[i] + i);
            }
        }

        return 0;
    }
}

/**
 * Greedy, O(n)
 * similar with BFS, but dont need to check an interval, only check the furthest point
 * since we can make sure the steps we take for reaching all points before furthest point currently
 * [2, 3, 1, 0, 4] => 
 * 0: furthest: 2, jumps: 1
 * 1: furthest: 4,
 * 2: furthest: 3,
 * 3: furthest: 3,
 * 4, furthest: 4, jumps: 2
 */
class Solution {
    public int jump(int[] nums) {
        for (int i = 0, step = 1, right = 0, furthest = 0; i < nums.length - 1; i++) {
            furthest = Math.max(furthest, nums[i] + i);
            if (furthest >= nums.length - 1) {
                return step;
            }

            // read last furthest, increase step
            if (i == right) {
                step++;
                right = furthest;
            }
        }

        return 0;
    }
}