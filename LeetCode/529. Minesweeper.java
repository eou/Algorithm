// 529. Minesweeper
// DFS
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

// BFS
class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int m = board.length, n = board[0].length;
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(click);
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0], col = cell[1];
            
            if (board[row][col] == 'M') { // Mine
                board[row][col] = 'X';
            }
            else { // Empty
                // Get number of mines first.
                int count = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) continue;
                        int r = row + i, c = col + j;
                        if (r < 0 || r >= m || c < 0 || c < 0 || c >= n) continue;
                        if (board[r][c] == 'M' || board[r][c] == 'X') count++;
                    }
                }
                
                if (count > 0) { // If it is not a 'B', stop further BFS.
                    board[row][col] = (char)(count + '0');
                }
                else { // Continue BFS to adjacent cells.
                    board[row][col] = 'B';
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (i == 0 && j == 0) continue;
                            int r = row + i, c = col + j;
                            if (r < 0 || r >= m || c < 0 || c < 0 || c >= n) continue;
                            if (board[r][c] == 'E') {
                                queue.add(new int[]{r, c});
                                board[r][c] = 'B'; // Avoid to be added again.
                            }
                        }
                    }
                }
            }
        }
        
        return board;
    }
}