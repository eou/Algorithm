// 45. Jump Game II
class Solution {
    // DFS，时间复杂度为 O(2^n)，会 TLE
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

class Solution {
    /**
     * BFS 版本，时间复杂度为 O(n)
     * nodes in level i are all the nodes that can be reached in ith jump => level + 1 = step
     * [2, 3, 1, 0, 4] => 
     * level 0: 0,      start: 1, end: 2
     * level 1: 1, 2,   start: 3, end: 4
     * level 2: 3, 4,   
     */
    public int jump(int[] nums) {
        if(nums.length <= 1) {
            return 0;
        }

        int start = 0;
        int end = 0;
        for(int level = 0; level < nums.length; level++) {
            if(start > end) {
                break;
            }
            int furthest = 0;
            for(int j = start; j < end + 1; j++) {
                if(nums[j] + j >= nums.length - 1) {
                    return level + 1;
                }
                furthest = Math.max(furthest, nums[j] + j);
            }
            start = end + 1;
            end = furthest;
        }

        return -1;
    }
}

class Solution {
    /**
     * Greedy 版本，时间复杂度为 O(n)
     * [2, 3, 1, 0, 4] => 
     * 0: furthest: 2, jumps: 1
     * 1: furthest: 4,
     * 2: furthest: 3,
     * 3: furthest: 3,
     * 4, furthest: 4, jumps: 2
     */
    public int jump(int[] nums) {
        int jumps = 0;
        int end = 0;
        int furthest = 0;
        for(int i = 0; i < nums.length - 1; i++) {
            furthest = Math.max(furthest, nums[i] + i);
            if(i == end) { // 到达上次能跳的最远地方，增加一次 jumps
                jumps++;
                end = furthest;
            }
        }

        return jumps;
    }
}