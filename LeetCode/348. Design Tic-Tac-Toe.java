// 348. Design Tic-Tac-Toe
class TicTacToe {

    /** Initialize your data structure here. */
    public int[][] board;
    public int size;

    public TicTacToe(int n) {
        board = new int[n][n];
        size = n;
    }

    /**
     * Player {player} makes a move at ({row}, {col}).
     * 
     * @param row    The row of the board.
     * @param col    The column of the board.
     * @param player The player, can be either 1 or 2.
     * @return The current winning condition, can be either: 0: No one wins. 1:
     *         Player 1 wins. 2: Player 2 wins.
     */
    public int move(int row, int col, int player) {
        int mark = player == 1 ? 1 : -1;
        board[row][col] = mark;

        boolean res = false;
        // check row
        int rowSum = 0;
        for (int i = 0; i < size; i++) {
            rowSum += board[row][i];
        }
        res = res || rowSum == mark * size;

        // check col
        int colSum = 0;
        for (int i = 0; i < size; i++) {
            colSum += board[i][col];
        }
        res = res || colSum == mark * size;

        // check diagonal
        if (row == col) {
            int diagSum = 0;
            for (int i = 0; i < size; i++) {
                diagSum += board[i][i];
            }
            res = res || diagSum == mark * size;
        }

        if (row + col == size - 1) {
            int antiDiagSum = 0;
            for (int i = 0; i < size; i++) {
                antiDiagSum += board[i][size - i - 1];
            }
            res = res || antiDiagSum == mark * size;
        }

        return res ? player : 0;
    }

}

class TicTacToe {
    private int rows[];
    private int cols[];
    private int diagonals[];
    
    public TicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
        diagonals = new int[2];
    }
    
    // Anyone wins the game should occupy entire row, col or diagonal so we only need to record the state of n rows, n cols and 2 diagonals other than n^2 positions on the board
    public int move(int row, int col, int player) {
        int flag = player == 1 ? 1 : -1;

        int n = rows.length;
        rows[row] += flag;
        cols[col] += flag;
        if(row == col) {
            diagonals[0] += flag;
        }
        if(row == n - col - 1) {
            diagonals[1] += flag;
        }
        
        if(rows[row] == n || cols[col] == n || diagonals[0] == n || diagonals[1] == n) {
            return 1;
        }
        
        if(rows[row] == -n || cols[col] == -n || diagonals[0] == -n || diagonals[1] == -n) {
            return 2;
        }
        
        return 0;
    }
}
