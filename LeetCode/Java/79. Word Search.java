// 79. Word Search
class Solution {
    // 时间复杂度为 O((mn) * 4^k)，k 为单词长度
    public boolean exist(char[][] board, String word) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(exist(board, i, j, word, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean exist(char[][] board, int i, int j, String word, int len) {
        if(len == word.length()) {
            return true;
        }
        if(i < 0 || i > board.length - 1 || j < 0 || j > board[0].length - 1 || board[i][j] != word.charAt(len)) {
            return false;
        }

        // avoid reusing characters
        board[i][j] = '#';
        boolean result = exist(board, i - 1, j, word, len + 1) ||
                        exist(board, i, j - 1, word, len + 1) ||
                        exist(board, i, j + 1, word, len + 1) ||
                        exist(board, i + 1, j, word, len + 1);
        board[i][j] = word.charAt(len);
        return result;
    }
}