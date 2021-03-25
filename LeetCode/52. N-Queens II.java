// 52. N-Queens II
// time complexity O(N!)
class Solution {
    private int res = 0;

    public int totalNQueens(int n) {
        if (n <= 0) {
            return res;
        }

        boolean[] row = new boolean[n];
        boolean[] col = new boolean[n];
        boolean[] diag = new boolean[n * 2];
        boolean[] subd = new boolean[n * 2];

        dfs(n, row, col, diag, subd, 0);
        return res;
    }

    private void dfs(int n, boolean[] row, boolean[] col, boolean[] diag, boolean[] subd, int cur) {
        if (cur == n) {
            res++;
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!row[cur] && !col[i] && !diag[cur - i + n] && !subd[cur + i]) {
                row[cur] = true;
                col[i] = true;
                diag[cur - i + n] = true;
                subd[cur + i] = true;
                dfs(n, row, col, diag, subd, cur + 1);
                row[cur] = false;
                col[i] = false;
                diag[cur - i + n] = false;
                subd[cur + i] = false;
            }
        }
    }
}