// 14. Longest Common Prefix
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }

        // 从第一个单词开始，按序两两匹配相互之间共同部分
        // ["flower","flow","flight"] => flower, flow = flow => flow, flight = fl
        String pre = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length() - 1); // 如果不匹配，不断缩短前缀长度
            }
        }
        return pre;
    }
}

class Solution {
    class TrieNode {
        char c;
        boolean isWord;
        TrieNode[] children = new TrieNode[26];
        TrieNode() {}
        TrieNode(char val) {
            c = val;
        }
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }

        TrieNode root = new TrieNode();
        for(String word : strs) {
            if(word.length() == 0) {
                return ""; // 空单词无法存入 Trie
            }
            addWord(root, word);
        }
        
        String prefix = "";
        TrieNode node = root;
        while(true) {
            int branch = 0;
            int index = -1;
            if(node.isWord) { // 到达一个单词的结尾就说明到达最长前缀了
                break;
            }
            for(int i = 0; i < 26; i++) {
                if(node.children[i] != null) {
                    branch++;
                    index = i;
                }
            }
            if(branch == 1) {
                prefix += (char)('a' + index);
                node = node.children[index];
            } else {
                break;
            }
        }
        
        return prefix;
    }
    
    private void addWord(TrieNode root, String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode(c);
            }
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }
}