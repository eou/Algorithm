// 52. N-Queens II
class Solution {
    public int res = 0;
    public int totalNQueens(int n) {
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[n];
        boolean[] diag = new boolean[2 * n];
        boolean[] antiDiag = new boolean[2 * n];
        
        dfs(row, col, diag, antiDiag, 0, n);
        return res;
    }
    
    // step means put a queen on row step
    public void dfs(boolean[] row, boolean[] col, boolean[] diag, boolean[] antiDiag, int step, int size) {
        if (step == size) {
            res++;
            return;
        }
        
        for (int i = 0; i < size; i++) {
            // test all columns, i is col, step is row
            if (!col[i] && 
                !diag[(step - i + diag.length) % diag.length] && 
                !antiDiag[(step + i) % antiDiag.length]) {
                // can place a queen
                row[step] = true;
                col[i] = true;
                diag[(step - i +  diag.length) % diag.length] = true;
                antiDiag[(step + i) % antiDiag.length] = true;
                dfs(row, col, diag, antiDiag, step + 1, size);
                // backtracking
                row[step] = false;
                col[i] = false;
                diag[(step - i +  diag.length) % diag.length] = false;
                antiDiag[(step + i) % antiDiag.length] = false;
            }
        }
    }
}