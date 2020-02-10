// 1349. Maximum Students Taking Exam
// https://leetcode.com/problems/maximum-students-taking-exam/discuss/503686/A-simple-tutorial-on-this-bitmasking-problem
// bit-masking, Time O(m * 4^n), space O(m * 2^n)
class Solution {
    public int maxStudents(char[][] seats) {
        int m = seats.length, n = seats[0].length;
        int[] validRows = new int[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                validRows[i] = (validRows[i] << 1) + (seats[i][j] == '.' ? 1 : 0);
            } 
        }

        int state = 1 << n; // There are 2^n states for n columns in binary format
        int[][] dp = new int[m][state];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < state; j++) {
                if (((j & validRows[i]) == j) && ((j & (j << 1)) == 0)) {
                    if (i == 0) {
                        dp[i][j] = Integer.bitCount(j);
                    } else {
                        for (int k = 0; k < state; k++) {
                            if (((k << 1) & j) == 0 && ((j << 1) & k) == 0 && dp[i-1][k] != -1)  {
                                dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + Integer.bitCount(j));
                            }
                        }
                    }
                    res = Math.max(res, dp[i][j]);
                }
            }
        }
        return res;
    }
}