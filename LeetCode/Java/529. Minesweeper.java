// 529. Minesweeper
class Solution {
    public int[] dirx = {-1, -1, -1, 0, 0, 1, 1, 1};
    public int[] diry = {-1, 0, 1, -1, 1, -1, 0, 1};
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        if (board[x][y] == 'M') {
            // mine, gg
            board[x][y] = 'X';
            return board;
        } else if (board[x][y] == 'E') {
            // empty
            int mine = checkAdjacent(board, click);
            if (mine == 0) {
                // recursively reveal
                board[x][y] = 'B';
                for (int i = 0; i < 8; i++) {
                    int[] nextclick = new int[]{x + dirx[i], y + diry[i]};
                    if (boundCheck(board, nextclick[0], nextclick[1])) {
                        updateBoard(board, nextclick);
                    }
                }
            } else {
                board[x][y] = (char)(mine + '0');   // int => char
                return board;
            }
        } else {
            // might be integer, M, E, B, do nothing
            return board;
        }
        return board;
    }
    
    public int checkAdjacent(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        int mine = 0;
        for (int i = 0; i < 8; i++) {
            int xi = x + dirx[i];
            int yi = y + diry[i];
            if (boundCheck(board, xi, yi) && board[xi][yi] == 'M') {
                mine++;
            }
        }
        return mine;
    }
    
    public boolean boundCheck(char[][] board, int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }
}