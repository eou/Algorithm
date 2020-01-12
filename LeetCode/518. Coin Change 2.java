// 518. Coin Change 2
// the number of combinations that make up that amount
// Coin Change 的做法不适合，无法防止重复情况，只能得出最小情况
// DFS, WA
class Solution {
    public int change(int amount, int[] coins) {
        // coin change 1
        if (amount == 0) {
            return 1;
        }
        
        int res = 0;
        for (int c : coins) {
            if (amount >= c) {
                res += change(amount - c, coins);
            }
        }
        return res;
    }
}

class Solution {
    public int change(int amount, int[] coins) {
        // dp[i][j] means number of combinations that make up j using first i coins
        int[][] dp = new int[coins.length + 1][amount + 1];

        // dp[0][j] = 0
        // dp[i][0] = 1
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                // decide if should take coins[i]
                if (j >= coins[i - 1]) {
                    // take
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
                // not take
                dp[i][j] += dp[i - 1][j];
            }
        }

        return dp[coins.length][amount];
    }
}

class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }
}

// pseudo DFS with memo, TLE
// use int[] (object) as map's key, map will use this object's address as identifier but not the value of object!
class Solution {
    public int change(int amount, int[] coins) {
        Map<int[], Integer> memo = new HashMap<>(); // amount, coin => ways
        return dfs(amount, coins, 0, memo);
    }

    public int dfs(int amount, int[] coins, int coinIndex, Map<int[], Integer> memo) {
        // exit
        if (amount == 0) {
            return 1;
        } else if (coinIndex >= coins.length) {
            return 0;
        }

        // memoization
        if (memo.containsKey(new int[] { amount, coinIndex })) {
            return memo.get(new int[] { amount, coinIndex });
        }

        int cur = coins[coinIndex], res = 0;
        // decide how many current coins we can use
        for (int i = 0; i * cur <= amount; i++) {
            res += dfs(amount - i * cur, coins, coinIndex + 1, memo);
        }

        memo.put(new int[] { amount, coinIndex }, res);
        return res;
    }
}

// DFS with memo
// use array not map
class Solution {
    public int change(int amount, int[] coins) {
        Integer[][] memo = new Integer[amount + 1][coins.length + 1];
        return dfs(amount, coins, 0, memo);
    }

    public int dfs(int amount, int[] coins, int coinIndex, Integer[][] memo) {
        // exit
        if (amount == 0) {
            return 1;
        } else if (coinIndex >= coins.length) {
            return 0;
        }

        // memoization
        if (memo[amount][coinIndex] != null) {
            return memo[amount][coinIndex];
        }

        int cur = coins[coinIndex], res = 0;
        // decide how many current coins we can use
        for (int i = 0; i * cur <= amount; i++) {
            res += dfs(amount - i * cur, coins, coinIndex + 1, memo);
        }

        memo[amount][coinIndex] = res;
        return res;
    }
}

// DFS with memo
// override class's hashCode() and equals()
class Solution {
    public class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 1337 * result + x;
            result = 1337 * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Pair && this.x == ((Pair) obj).x && this.y == ((Pair) obj).y;
        }
    }

    public int change(int amount, int[] coins) {
        Map<Pair, Integer> memo = new HashMap<>(); // amount, coin => ways
        return dfs(amount, coins, 0, memo);
    }

    public int dfs(int amount, int[] coins, int coinIndex, Map<Pair, Integer> memo) {
        // exit
        if (amount == 0) {
            return 1;
        } else if (coinIndex >= coins.length) {
            return 0;
        }

        // memoization
        if (memo.containsKey(new Pair(amount, coinIndex))) {
            return memo.get(new Pair(amount, coinIndex));
        }

        int cur = coins[coinIndex], res = 0;
        // decide how many coins we can use
        for (int i = 0; i * cur <= amount; i++) {
            res += dfs(amount - i * cur, coins, coinIndex + 1, memo);
        }

        memo.put(new Pair(amount, coinIndex), res);
        return res;
    }
}