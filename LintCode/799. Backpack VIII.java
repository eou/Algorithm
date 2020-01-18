// 799. Backpack VIII
// Give some coins of different value and their quantity.
// Find how many values which are in range 1 ~ n can these coins be combined
class Solution {
    /**
     * @param n:      the value from 1 - n
     * @param value:  the value of coins
     * @param amount: the number of coins
     * @return: how many different value from 1 - n
     */
    // bounded knapsack problem
    // similar with Backpack VII
    public static int backPackVIII(int n, int[] value, int[] amount) {
        // dp[i][j] means if j can be formed with first i items
        boolean[][] dp = new boolean[value.length + 1][n + 1];
        // dp[i][0] = true
        for (int i = 0; i < value.length + 1; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= value.length; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= amount[i - 1]; k++) {
                    if (j >= k * value[i - 1]) {
                        dp[i][j] |= dp[i - 1][j - k * value[i - 1]];
                    } else {
                        break;
                    }
                }
            }
        }

        int res = 0;
        // start from 1, problem description
        for (int i = 1; i <= n; i++) {
            if (dp[value.length][i]) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(backPackVIII(10, new int[] { 1, 2, 4 }, new int[] { 2, 1, 1 })); // 8
    }
}

class Solution {
    // space optimization
    public static int backPackVIII(int n, int[] value, int[] amount) {
        // dp[j] means if j can be formed with first i items
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= value.length; i++) {
            for (int j = n; j >= 0; j--) {
                for (int k = 0; k <= amount[i - 1]; k++) {
                    if (j >= k * value[i - 1]) {
                        dp[j] |= dp[j - k * value[i - 1]];
                    }
                }
            }
        }

        int res = 0;
        // start from 1, problem description
        for (int i = 1; i <= n; i++) {
            if (dp[i]) {
                res++;
            }
        }
        return res;
    }
}

class Solution {
    // use space trade time
    public static int backPackVIII(int n, int[] value, int[] amount) {
        // dp[j] means if j can be formed with first i items
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        int res = 0;
        for (int i = 1; i <= value.length; i++) {
            int[] count = new int[n + 1]; // count[x] is for current i, how many item i s have been used to get x
            for (int k = value[i - 1]; k <= n; k++) {
                if (dp[k] == false && dp[k - value[i - 1]] == true && count[k - value[i - 1]] < amount[i - 1]) {
                    dp[k] = true;
                    count[k] = count[k - value[i - 1]] + 1;
                    res++;
                }
            }
        }

        return res;
    }
}