// 322. Coin Change
// the fewest number of coins that you need to make up that amount
// Coin Change 2 求总组合数的 DFS 不适合，不方便用 memo
// DFS，时间复杂度为 O(c^N)，会 TLE，过了 15/182 个 testcases
class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount == 0) {
            return 0;
        }

        int res = amount + 1; // just a impossible result to indicate that if 'res' has been changed
        for(int c : coins) {
            int cur = 0;
            if(amount >= c) {
                int next = coinChange(coins, amount - c);
                if(next >= 0) { // next != -1
                    cur = next + 1;
                }
            }
            if(cur > 0) {
                res = Math.min(res, cur);
            }
        }

        return res == amount + 1 ? -1 : res;
    }
}

// DFS，时间复杂度为 O(c^N)，会 TLE，但比上面的 DFS 更优化一点，过了 93/182 个 testcases
// 这个不方便用 memo，因为要求最小组合硬币数，不能保证第一次求出的 memo 是最优的
// 但 Coin Change 2 求组合总数，可以用 memo 累加
class Solution {
    public int coinChange(int[] coins, int amount) {
        return dfs(coins, amount, 0, 0);
    }

    public int dfs(int[] coins, int amount, int index, int num) {
        if (amount == 0) {
            return num;
        } else if (index >= coins.length) {
            return -1;
        }

        int res = Integer.MAX_VALUE;
        // pick some current coins
        int value = coins[index];
        for (int i = 0; i * value <= amount; i++) {
            int n = dfs(coins, amount - i * value, index + 1, num + i);
            if (n != -1) {
                res = Math.min(res, n);
            }
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }
}

class Solution {
    // DFS with memoization，时间复杂度为 O(n^c)
    Map<Integer, Integer> map = new HashMap<>();
    public int coinChange(int[] coins, int amount) {
        if(amount == 0) {
            return 0;
        }
        if(map.containsKey(amount)) {
            return map.get(amount);
        }

        int cnt = amount + 1;
        for(int c : coins) {
            int cur = 0;
            if(amount >= c) {
                int next = coinChange(coins, amount - c);
                if(next >= 0) {
                    cur = next + 1;
                }
            }
            if(cur > 0) {
                cnt = Math.min(cnt, cur);
            }
        }

        map.put(amount, cnt == amount + 1 ? -1 : cnt);
        return map.get(amount);
    }
}

// DP 版本，时间复杂度为 O(n^2)
class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount == 0) {
            return 0;
        }
        int[] dp = new int[amount + 1];
        int total = 1;
        while(total <= amount) {
            dp[total] = amount + 1;
            for(int c : coins) {
                if(total >= c && dp[total - c] != amount + 1) {
                    dp[total] = Math.min(dp[total], dp[total - c] +  1);
                }
            }
            total++;
        }

        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}

// DP 版本，时间复杂度为 O(n^2)
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 0;  // no combinations
        // start from 1
        for (int i = 1; i <= amount; i++) {
            dp[i] = amount + 1; // impossible number
            for (int c : coins) {
                if (i >= c && dp[i - c] != amount + 1) {
                    dp[i] = Math.min(dp[i], dp[i - c] + 1);
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}