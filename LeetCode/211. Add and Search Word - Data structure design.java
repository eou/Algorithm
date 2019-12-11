// 211. Add and Search Word - Data structure design
// 重点在Trie Tree 字典树
class WordDictionary {
    public class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public boolean isWord;
    }
    
    public TrieNode root = new TrieNode();
    
//     public WordDictionary() {
//     }
    
    public void addWord(String word) {
        TrieNode node = root;
        if(word.length() != 0) {
            for(char c : word.toCharArray()) {
                if(node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.isWord = true;
        }
    }
    
    public boolean search(String word) {
        return helper(root, word, 0);
    }
    
    private boolean helper(TrieNode root, String word, int start) {
        if(start == word.length()) {
            return root.isWord;
        }
        
        if(word.charAt(start) != '.') {
            if(root.children[word.charAt(start) - 'a'] != null) {
                return helper(root.children[word.charAt(start) - 'a'], word, start + 1);
            } else {
                return false;
            }
        } else {
            for (int i = 0; i < root.children.length; i++) {
                if (root.children[i] != null && helper(root.children[i], word, start + 1)) {
                    return true;
                }
            }
            return false;
            // 不能这样写，如果前面返回false可能会错过后面的true
            // for(int i = 0; i < root.children.length; i++) {
            //     if(root.children[i] != null) {
            //         return helper(root.children[i], word, start + 1);
            //     }
            // }
            // return false;
        }
    }
}