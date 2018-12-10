/**
 * Surrounded in board.java
 * 判断棋盘上的棋子是否被围住
 */
import java.io.*;
import java.util.*;

class Solution {
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};
    // BFS
    public boolean isCaptured(int x, int y, char[][] board) {
        if(x < 0 && y < 0 && x >= board.length && y >= board[0].length) {
            return false;
        }
        if(board[x][y] != 'O') {
            return false;
        }

        int rows = board.length;
        int cols = board[0].length;
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(x * cols + y);
        visited.add(x * cols + y);
        while(!queue.isEmpty()) {
            int point = queue.poll();
            for(int i = 0; i < 4; i++) {
                int px = point / cols + dx[i];
                int py = point % cols + dy[i];
                if(px < 0 || py < 0 || px >= rows || py >= cols) {
                    // not surrounded by 'X'
                    return false;
                } else {
                    if(board[px][py] != 'X' && visited.add(px * cols + py)) {
                        queue.offer(px * cols + py);
                    }
                }
            }
        }

        return true;
    }

    // DFS
    public boolean isCaptured(int x, int y, char[][] board) {
        if(x < 0 && y < 0 && x >= board.length && y >= board[0].length) {
            return false;
        }
        if(board[x][y] != 'O') {
            return false;
        }

        // copy original data so that keep original board unchanged
        char[][] copy = new char[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j  < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return helper(x, y, copy);
    }

    private boolean helper(int x, int y, char[][] board) {
        if(x < 0 || y < 0 || x >= board.length ||  y >= board[0].length) {
            return false;
        }
        if(board[x][y] =='X') {
            return true;
        }

        board[x][y] = 'X';
        boolean isCaptured = true;
        for(int i = 0; i < 4; i++) {
            isCaptured &= helper(x + dx[i], y + dy[i], board);
        }
        return isCaptured;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        char[][] board = {
            {'.', '.', 'X', 'X', '.'},
            {'.', 'X', 'O', 'O', 'X'},
            {'.', 'O', 'X', 'O', 'X'},
            {'X', 'X', 'O', 'X', 'O'},
            {'.', '.', 'X', 'X', 'O'}};
        char[][] board2 = {
            {'.', '.', 'X', 'X', '.'},
            {'.', 'X', '.', 'O', 'X'},
            {'X', '.', 'O', '.', 'X'},
            {'X', 'X', '.', 'X', '.'},
            {'.', '.', 'X', '.', '.'}};
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                System.out.print(s.isCaptured(i, j, board2) + ", ");
            }
            System.out.println();
        }
    }
}