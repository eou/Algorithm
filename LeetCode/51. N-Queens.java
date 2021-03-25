// 51. N-Queens
// DFS backtrack
class Solution {
    public List<List<String>> solveNQueens(int n) {
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[n];
        // 注意下对角线数组下标范围是 -n ~ n 和 0 ~ 2n
        boolean[] diag = new boolean[2 * n];
        boolean[] back = new boolean[2 * n];
        
        List<List<String>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        helper(n, 0, row, col, diag, back, list, res);
        return res;
    }
    
    // 判定位置的四个数组可以省略
    private void helper(int n, int startRow, boolean[] row, boolean[] col, boolean[] diag, boolean[] back, List<Integer> list, List<List<String>> res) {
        if (startRow == n) {
            res.add(draw(list));
            return;
        }
        
        for(int i = 0; i < n; ++i) {
            if (isValidPos(n, startRow, i, row, col, diag, back)) {
                row[startRow] = true;
                col[i] = true;
                diag[startRow - i + n] = true;
                back[startRow + i] = true;
                list.add(i);

                helper(n, startRow + 1, row, col, diag, back, list, res);

                row[startRow] = false;
                col[i] = false;
                diag[startRow - i + n] = false;
                back[startRow + i] = false;
                list.remove(list.size() - 1);
            }
        }
    }
    
    private boolean isValidPos(int n, int x, int y, boolean[] row, boolean[] col, boolean[] diag, boolean[] back) {
        return !row[x] && !col[y] && !diag[x - y + n] && !back[x + y];
    }
    
    private List<String> draw(List<Integer> list) {
        List<String> chess = new ArrayList<>();
        for (Integer n : list) {
            // 应该要用StringBuilder比较好，然后用append和to.String()
            String s = "";
            for (int i = 0; i < n; ++i) {
                s += ".";
            }
            s += "Q";
            for (int i = n + 1; i < list.size(); ++i) {
                s += ".";
            }
            chess.add(s);
        }
        return chess;
    }
}