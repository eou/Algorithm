// 1025. Divisor Game
class Solution {
    public boolean divisorGame(int N) {
        boolean[] dp = new boolean[N + 1];  // 1..N
        dp[1] = false;
        for (int i = 1; i <= N; i++) {
            // find 0 < j < i, i % j == 0
            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    dp[i] = dp[i] || (!dp[i - j]);   // i - j is Bob result
                }
            }
        }
        
        return dp[N];
    }
}

// The key is whoever gets 1 looses. So if you got an even number and keep
// choosing 1 then your opponent will always get odd number (no matter what
// her/his choice of number is) and eventually that odd number will become 1. So
// for the player who gets even number, choosing 1 is bound to winning and thus optimal
class Solution {
    public boolean divisorGame(int N) {
        return N % 2 == 0;
    }
}