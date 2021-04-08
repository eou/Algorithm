// 36. Valid Sudoku
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // 27 1~9 numbers
        boolean[][] sign = new boolean[27][10];     // 0 - 9 should be 10 position
        // 0 - 8 rows, 9 - 17, cols, 18 - 26, sub-box
        // (i, j) i rows, j cols, i % 3, i / 3 sub-box
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    if (!valid(sign, i, j, num)) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public boolean valid(boolean[][] sign, int row, int col, int num) {
        // check row
        if (sign[row][num] == false) {
            sign[row][num] = true;
        } else {
            return false;
        }
        
        // check column
        if (sign[col + 9][num] == false) {
            sign[col + 9][num] = true;
        } else {
            return false;
        }
        
        // check sub-box
        int pos = (row / 3) * 3 + (col / 3);
        if (sign[pos + 18][num] == false) {
            sign[pos + 18][num] = true;
        } else {
            return false;
        }
        
        return true;
    }
}

// HashMap, same idea
class Solution {
    public boolean isValidSudoku(char[][] board) {
        Map<Integer, Integer>[] rows = new HashMap[9];
        Map<Integer, Integer>[] columns = new HashMap[9];
        Map<Integer, Integer>[] boxes = new HashMap[9];

        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<Integer, Integer>();
            columns[i] = new HashMap<Integer, Integer>();
            boxes[i] = new HashMap<Integer, Integer>();
        }

        // validate a board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int n = (int) num;
                    int box_index = (i / 3) * 3 + j / 3;

                    // keep the current cell value
                    rows[i].put(n, rows[i].getOrDefault(n, 0) + 1);
                    columns[j].put(n, columns[j].getOrDefault(n, 0) + 1);
                    boxes[box_index].put(n, boxes[box_index].getOrDefault(n, 0) + 1);

                    // check if this value has been already seen before
                    if (rows[i].get(n) > 1 || columns[j].get(n) > 1 || boxes[box_index].get(n) > 1)
                        return false;
                }
            }
        }

        return true;
    }
}