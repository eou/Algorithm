// 723. Candy Crush
class Solution {
    public int[][] candyCrush(int[][] board) {
        int row = board.length, col = board[0].length;
        boolean found = true;

        while (found) {
            found = false;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int val = Math.abs(board[i][j]);
                    if (val == 0) {
                        continue;
                    }

                    if (j + 2 < col && Math.abs(board[i][j + 1]) == val && Math.abs(board[i][j + 2]) == val) {
                        found = true;
                        int k = j;
                        while (k < col && Math.abs(board[i][k]) == val) {
                            board[i][k++] = -val;
                        }
                    }
                    if (i < row - 2 && Math.abs(board[i + 1][j]) == val && Math.abs(board[i + 2][j]) == val) {
                        found = true;
                        int k = i;
                        while (k < row && Math.abs(board[k][j]) == val) {
                            board[k++][j] = -val;
                        }
                    }
                }
            }

            if (found) {
                for (int j = 0; j < col; j++) {
                    int k = row - 1;
                    for (int i = row - 1; i >= 0; i--) {
                        if (board[i][j] > 0) {
                            board[k--][j] = board[i][j];
                        }
                    }
                    for (int i = k; i >= 0; i--) {
                        board[i][j] = 0;
                    }
                }
            }
        }
        return board;
    }
}