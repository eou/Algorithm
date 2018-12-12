// 212. Word Search II
class Solution {
    // 时间复杂度为 O(m * n * 4^k), k 为 单词长度
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String isWord;
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        List<String> results = new ArrayList<>();
        TrieNode root = buildTrieTree(words);
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                helper(board, root, i, j, results);
            }
        }
        
        return results;
    }

    private void helper(char[][] board, TrieNode root, int i, int j, List<String> results) {
        char c = board[i][j];
        if(c == '.' || root.children[c - 'a'] == null) {
            return;
        }
        root = root.children[c - 'a'];
        if(root.isWord != null) {
            results.add(root.isWord);
            root.isWord = null;
        }

        board[i][j] = '.';
        if(i > 0) {
            helper(board, root, i - 1, j, results);
        }
        if(j > 0) {
            helper(board, root, i, j - 1, results);
        }
        if(i < board.length - 1) {
            helper(board, root, i + 1, j, results);
        }
        if(j < board[0].length - 1) {
            helper(board, root, i, j + 1, results);
        }
        board[i][j] = c;
    }

    private TrieNode buildTrieTree(String[] words) {
        TrieNode root = new TrieNode();
        for(String word : words) {
            TrieNode node = root;
            for(char c : word.toCharArray()) {
                if(node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.isWord = word;
        }

        return root;
    }
}