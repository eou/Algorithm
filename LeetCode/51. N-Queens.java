// 51. N-Queens
// 典型的DFS回溯
class Solution {
    public List<List<String>> solveNQueens(int n) {
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[n];
        // 注意下对角线数组下标范围是 -n ~ n 和 0 ~ 2n
        boolean[] diag = new boolean[2 * n];
        boolean[] back = new boolean[2 * n];
        
        List<List<String>> results = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        helper(n, 0, row, col, diag, back, result, results);
        return results;
    }
    
    // 判定位置的四个数组可以省略
    private void helper(int n, int startRow, boolean[] row, boolean[] col, boolean[] diag, boolean[] back, List<Integer> result, List<List<String>> results) {
        if (startRow == n) {
            results.add(draw(result));
            return;
        }
        
        for(int i = 0; i < n; ++i) {
            if (isValidPos(n, startRow, i, row, col, diag, back)) {
                row[startRow] = true;
                col[i] = true;
                diag[startRow - i + n] = true;
                back[startRow + i] = true;
                result.add(i);
                helper(n, startRow + 1, row, col, diag, back, result, results);
                row[startRow] = false;
                col[i] = false;
                diag[startRow - i + n] = false;
                back[startRow + i] = false;
                result.remove(result.size() - 1);
            }
        }
    }
    
    private boolean isValidPos(int n, int x, int y, boolean[] row, boolean[] col, boolean[] diag, boolean[] back) {
        if (!row[x] && !col[y] && !diag[x - y + n] && !back[x + y]) {
            return true;
        }
        return false;
    }
    
    private List<String> draw(List<Integer> result) {
        List<String> chess = new ArrayList<>();
        for (Integer n : result) {
            // 应该要用StringBuilder比较好，然后用append和to.String()
            String s = "";
            for (int i = 0; i < n; ++i) {
                s += ".";
            }
            s += "Q";
            for (int i = n + 1; i < result.size(); ++i) {
                s += ".";
            }
            chess.add(s);
        }
        return chess;
    }
}