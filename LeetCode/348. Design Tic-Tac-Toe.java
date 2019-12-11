// 348. Design Tic-Tac-Toe
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

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */