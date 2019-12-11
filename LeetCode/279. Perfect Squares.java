// 279. Perfect Squares
class Solution {
    // dp[n] = min{dp[n - i*i] + 1}, n - i*i >=0 && i >= 1
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = (int) Math.sqrt(i); j >= 1; j--) {
                dp[i] = Math.min(dp[i], 1 + dp[i - j * j]);
            }
        }

        return dp[n];
    }
}

class Solution {
    public int numSquares(int n) {
        List<Integer> dp = new ArrayList<>();
        dp.add(0);

        while(dp.size() <= n) {
            int size = dp.size();
            int min = Integer.MAX_VALUE;
            for(int i = 1; i * i <= size; i++) {
                min = Math.min(min, dp.get(size - i * i) + 1);
            }
            dp.add(min);
        }

        return dp.get(n);
    }
}
