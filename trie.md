# Trie Tree

[Trie](https://en.wikipedia.org/wiki/Trie). Can be prefix trees and suffix trees.

[208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)

```java
class TrieNode {
    // R links to node children
    private TrieNode[] children;
    private final int R = 26;   // could be 256, ASCIIs
    private boolean isWord;
    public TrieNode() {
        children = new TrieNode[R];
    }
}

class Trie {
    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie
    public void insert(String word) {
        TrieNode node = root;
        for (Character c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {    // assume inputs are all lowercase letter
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }

    // search a prefix or whole key in trie and returns the node where search ends
    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (Character c : prefix.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                return null;
            }
            node = node.children[c - 'a'];
        }
        return node;
    }

    // Returns if the word is in the trie
    public boolean search(String word) {
       TrieNode node = searchPrefix(word);
       return node && node.isWord;
    }
}
```