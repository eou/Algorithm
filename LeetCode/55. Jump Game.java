// 55. Jump Game
// BFS, TLE
class Solution {
    public boolean canJump(int[] nums) {
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        visited.add(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer next = queue.poll();
                for (int j = 0; j <= nums[next]; j++) {
                    if (next + j == nums.length - 1) {
                        return true;
                    }
                    if (next + j < nums.length && !visited.contains(next + j)) {
                        queue.offer(next + j);
                        visited.add(next + j);
                    }
                }
            }
        }

        return false;
    }
}

class Solution {
    // DFS，时间复杂度为 O(2^n)，会 TLE
    public boolean canJump(int[] nums) {
        return helper(nums, 0);
    }

    private boolean helper(int[] nums, int start) {
        if(start == nums.length - 1) {
            return true;
        }

        int furthest = Math.min(nums[start] + start, nums.length - 1);
        for(int next = start + 1; next <= furthest; next++) {
            if(helper(nums, next)) {
                return true;
            }
        }

        return false;
    }
}

class Solution {
    // DFS with memoization，top down，时间复杂度为 O(n^2)
    public boolean canJump(int[] nums) {
        Boolean[] jump = new Boolean[nums.length];
        jump[jump.length - 1] = true;
        return helper(nums, 0, jump);
    }

    private boolean helper(int[] nums, int start, Boolean[] jump) {
        if(jump[start] != null) {
            return jump[start];
        }

        int furthest = Math.min(nums[start] + start, nums.length - 1);
        for (int next = start + 1; next <= furthest; next++) {
            if (helper(nums, next, jump)) {
                jump[start] = true;
                return true;
            }
        }

        jump[start] = false;
        return false;
    }
}

// DP 版本，bottom up, O(n^2)
class Solution {
    public boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i]) {
                for (int j = i; j <= i + nums[i] && j < nums.length; j++) {
                    dp[j] = true;
                }
            }
        }

        return dp[nums.length - 1];
    }
}

// DP
class Solution {
    public boolean canJump(int[] nums) {
        Boolean[] dp = new Boolean[nums.length];
        dp[nums.length - 1] = true;

        for (int i = nums.length - 2; i >= 0; i--) {
            int furthest = Math.min(nums[i] + i, nums.length - 1);
            for (int j = i + 1; j <= furthest; j++) {
                if (dp[j] != null && dp[j] == true) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0] != null && dp[0] == true;
    }
}

// Greedy, O(n)
// !!! If a position is reachable, then all the position on the left side are reachable.
class Solution {
    public boolean canJump(int[] nums) {
        int reachable = 0;
        for (int i = 0; i < nums.length; ++i) {
            // cannot reach
            if (i > reachable) {
                return false;
            }
            reachable = Math.max(reachable, i + nums[i]);
        }
        return true;
    }
}

class Solution {
    public boolean canJump(int[] nums) {
        int reachable = 0;
        for (int i = 0; i <= reachable; i++) {
            reachable = Math.max(reachable, i + nums[i]);
            if (reachable >= nums.length - 1)
                return true;
        }
        return false;
    }
}