// 509. Fibonacci Number
class Solution {
    public int fib(int n) {
        if (n < 0) {
            return -1;
        }
        
        if (n == 0) {
            return 0;
        }
        
        if (n == 1) {
            return 1;
        }
        
        return fib(n - 1) + fib(n - 2);
    }
}

class Solution {
    private Map<Integer, Integer> map = new HashMap<>();
    public int fib(int n) {
        if (n < 0) {
            return -1;
        }
        
        if (n == 0) {
            return 0;
        }
        
        if (n == 1) {
            return 1;
        }
        
        if (map.containsKey(n)) {
            return map.get(n);
        }
        
        int res = fib(n - 1) + fib(n - 2);
        map.put(n, res);
        
        return res;
    }
}

class Solution {
    public int fib(int n) {
        if (n < 0) {
            return -1;
        }

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}