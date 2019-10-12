// 552. Student Attendance Record II
// minus, >= 20, overflow
class Solution {
    public int checkRecord(int n) {
        // 3^n
        return ((int)(Math.pow(3, n)) - dfs("", n)) % 1000000007;
    }
    
    //    A   L   P
    //   A.. L..  P..
    public int dfs(String records, int n) {
        if (records.length() == n) {
            return 0;
        }
        
        int res = 0;
        // ..A..A, LLL (more than one 'A'!!!!!! not continous !!!)
        // add A
        if (records.contains("A")) {
            res += Math.pow(3, (n - records.length() - 1));
        } else {
            res += dfs(records + 'A', n);
        }
        // add L
        if (records.length() >= 2 && records.charAt(records.length() - 1) == 'L' && records.charAt(records.length() - 2) == 'L') {
            res += Math.pow(3, (n - records.length() - 1));
        } else {
            res += dfs(records + 'L', n);
        }
        // add P
        res += dfs(records + 'P', n);
        return res;
    }
}

// direct DFS, >= 100, TLE
class Solution {
    public int checkRecord(int n) {
        // 3^n
        return dfs("", n) % 1000000007;
    }
    
    public int dfs(String records, int n) {
        if (records.length() == n) {
            return 1;
        }
        
        int res = 0;
        // ..A..A, LLL (more than one 'A'!!!!!! not continous !!!)
        // add A
        if (!records.contains("A")) {
            res += dfs(records + 'A', n);
        }
        
        // add L
        if (!(records.length() >= 2 && records.charAt(records.length() - 1) == 'L' && records.charAt(records.length() - 2) == 'L')) {
            res += dfs(records + 'L', n);
        }
        
        // add P
        res += dfs(records + 'P', n);
        return res;
    }
}

// DP
// T(n) = A(n) + P(n) + L(n)
// P(n) = A(n - 1) + P(n - 1) + L(n - 1)
// L(n) = A(n - 1) + P(n - 1) + (A(n - 2) + P(n - 2))
// A(n) = P(n - 1) without A + L(n - 1) without A = P'(n - 1) + L'(n - 1)
// P'(n) = P'(n - 1) + L'(n - 1) = A(n) = P'(n - 1) + P'(n - 2) + P'(n - 3) => A(n) = A(n - 1) + A(n - 2) + A(n - 3)
// L'(n) = P'(n - 1) + (P'(n - 2))
class Solution {
    public int checkRecord(int n) {
        if (n == 1) {
            return 3;
        }
        
        if (n == 2) {
            return 8;
        }
        
        int m = 1000000007;
        int[] A = new int [n];
        int[] P = new int [n];
        int[] L = new int [n];
        
        P[0] = 1;
        L[0] = 1;
        L[1] = 3;
        A[0] = 1;
        A[1] = 2;
        A[2] = 4;
        
        for(int i = 1; i < n; i++) {
            A[i - 1] %= m;
            P[i - 1] %= m;
            L[i - 1] %= m;
            
            P[i] = ((A[i - 1] + P[i - 1]) % m + L[i - 1]) % m;
            
            if(i > 1) {
                L[i] = ((A[i - 1] + P[i - 1]) % m + (A[i - 2] + P[i - 2]) % m) % m;
            }
            
            if(i > 2) {
                A[i] = ((A[i - 1] + A[i - 2]) % m + A[i - 3]) % m;
            }
        }
        
        return ((A[n - 1] % m + P[n - 1] % m) % m + L[n - 1] % m) % m;
    }
}

// 这种 DP 可以用幂运算转化为 O(logn)
class Solution {
    public int checkRecord(int n) {
        final int MOD = 1000000007;

        // Let f[i][j][k] denote the # of valid sequences of length i where:
        // There can be at most j A's in the entire sequence.
        // There can be at most k trailing L's.
        int[][][] f = new int[n + 1][2][3];

        f[0] = new int[][]{{1, 1, 1}, {1, 1, 1}};
        for (int i = 1; i <= n; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 3; k++) {
                    int val = f[i - 1][j][2]; // ...P
                    if (j > 0) val = (val + f[i - 1][j - 1][2]) % MOD; // ...A
                    if (k > 0) val = (val + f[i - 1][j][k - 1]) % MOD; // ...L
                    f[i][j][k] = val;
                }
        
        return f[n][1][2];
    }
}