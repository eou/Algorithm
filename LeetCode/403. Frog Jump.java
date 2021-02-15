// 403. Frog Jump
// [0,1,3,5,6,8,12,17]
// [0,1,2,3,4,8,9,11]
// [0,1,3,6,10,13,15,18]
// !!! then its next jump must be either k - 1, k, or k + 1 units. !!! k cannot be too large or too small
// thus we cannot only record max units a stone can get
// dfs, TLE
class Solution {
    public boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) {
            return false;
        }
        
        if (stones.length > 1 && stones[1] - stones[0] != 1) {
            return false;
        }
        
        return dfs(stones, 1, 1);
    }
    
    public boolean dfs(int[] stones, int start, int step) {
        if (start >= stones.length - 1) {
            return true;
        }
        
        if (stones[start + 1] - stones[start] > step + 1) {
            return false;
        }
        
        // try multiple choices
        boolean res = false;
        for (int i = start + 1; i < stones.length; i++) {
            if (stones[i] - stones[start] <= step + 1 && stones[i] - stones[start] >= step - 1) {
                res = res || dfs(stones, i, stones[i] - stones[start]);
            }
        }
        
        return res;
    }
}

// dfs with memo
class Solution {
    public Boolean[][] memo;
    public boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) {
            return false;
        }
        
        if (stones.length > 1 && stones[1] - stones[0] != 1) {
            return false;
        }
        
        memo = new Boolean[stones.length][stones.length];
        return dfs(stones, 1, 1);
    }
    
    public boolean dfs(int[] stones, int start, int step) {
        if (start >= stones.length - 1) {
            return true;
        }
        
        if (stones[start + 1] - stones[start] > step + 1) {
            return false;
        }
        
        if (memo[start][step] != null) {
            return memo[start][step];
        }
        
        // try multiple choices
        boolean res = false;
        for (int i = start + 1; i < stones.length; i++) {
            if (stones[i] - stones[start] <= step + 1 && stones[i] - stones[start] >= step - 1) {
                res = res || dfs(stones, i, stones[i] - stones[start]);
            }
        }
        
        memo[start][step] = res;
        return res;
    }
}

class Solution {
    public boolean canCross(int[] stones) {
        // cannot even jump out from start point
        if (stones[1] > 1) {
            return false;
        }
        
        
        // dp[i] means if the maximum units frog can have when jumping to the ith stone
        List<Set<Integer>> dp = new ArrayList<>();  // 0,1,...,n
        for(int i = 0; i < stones.length; i++) {
            dp.add(new HashSet<>());
        }
        
        // transition function
        // dp[i] can jumped from any previous stones as long as the units are enough
        // so we can update the units in next accessible stone when frog is arrived at the current stone
        dp.get(1).add(1); // assume the first jump must be 1 unit
        for (int i = 1; i < stones.length; i++) {   // stones array index
            // start from stones[i], update few next stones which are accessible
            for (int j = i + 1; j < stones.length; j++) {   // dp array index
                for (int unit : dp.get(i)) {
                    if (stones[j] <= stones[i] + unit + 1 && stones[j] >= stones[i] + unit - 1) {
                        // reachable
                        dp.get(j).add(stones[j] - stones[i]);
                    }
                }
            }
        }
        
        
        return dp.get(stones.length - 1).size() != 0;
    }
}

// hidden point: the largest unit the frog can get in the whole process cannot exceed stones.length !!!
// since at each step the unit can only increase 1 at most
class Solution {
    public boolean canCross(int[] stones) {
        int N = stones.length;
        // dp[i][j] means at stone i, if frog can have unit j
        boolean[][] dp = new boolean[N][N + 1];
        dp[0][1] = true;

        for (int i = 1; i < N; ++i) {
            // start from previous stone to update current stone
            for (int j = 0; j < i; ++j) {
                int step = stones[i] - stones[j];
                if (step < 0 || step > N || !dp[j][step]) {
                    continue;
                }
                
                dp[i][step] = true;
                if (step - 1 >= 0) {
                    dp[i][step - 1] = true;
                }
                if (step + 1 <= N) {
                    dp[i][step + 1] = true;
                }

                if (i == N - 1) {
                    return true;
                }
            }
        }

        return false;
    }
}