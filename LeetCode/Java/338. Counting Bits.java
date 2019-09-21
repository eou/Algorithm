// 338. Counting Bits
class Solution {
    // 0 0
    // 1 1
    // 2 10
    // 3 11
    // 4 100
    // what if I get the bit counts from 0 to i, and I want to know the bit counts from i + 1 to n
    // actually every number can be represented by binary codes, so n = a1 * 2^0 + a2 * 2^1 + ... + ak * 2^k
    public int[] countBits(int num) {
        int[] dp = new int[num + 1];
        dp[0] = 0;
        for (int i = 1; i <= num; i++) {
            dp[i] += (dp[i - lowerbit(i)] + 1);
        }
        return dp;
    }

    // 5 0110 => 0010
    public int lowerbit(int num) {
        return num & (-num);
    }
}

class Solution {
    public int[] countBits(int num) {
        int[] ans = new int[num + 1];
        for (int i = 1; i <= num; ++i)
            ans[i] = ans[i >> 1] + (i & 1); // x / 2 is x >> 1 and x % 2 is x & 1
        return ans;
    }
}