// 322. Coin Change
class Solution {
    // DFS，时间复杂度为 O(c^N)，会 TLE
    public int coinChange(int[] coins, int amount) {
        if(amount == 0) {
            return 0;
        }

        int cnt = amount + 1; // just a impossible result to indicate that if 'cnt' has been changed
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

        return cnt == amount + 1 ? -1 : cnt;
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

class Solution {
    // DP 版本，时间复杂度为 O(n^c)
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