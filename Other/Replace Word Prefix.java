/**
 * Replace Word Prefix.java
 * 如果句子中各个单词的部分前缀在单词数组中查得到，就用前缀替换整个单词
 * */
import java.util.*;
import java.io.*;

public class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }

    public String replacePrefix(String[] rootWords, String s) {
        String[] words = s.split(" ");
        TrieNode root = new TrieNode();

        // add rootWords into trie tree
        TrieNode cur;
        for(String word : rootWords) {
            cur = root;
            for(char c : word.toCharArray()) {
                if(cur.children[c - 'a'] == null) {
                    cur.children[c - 'a'] = new TrieNode();
                }
                cur = cur.children[c - 'a'];
            }
            cur.isWord = true;
        }

        // search prefix in the trie tree
        for(int i = 0; i < words.length; i++) {
            cur = root;
            for (int j = 0; j < words[i].length(); j++) {
                char c = words[i].charAt(j);
                if (cur.children[c - 'a'] == null) {
                    break;
                } else if(cur.children[c - 'a'].isWord) {
                    words[i] = words[i].substring(0, j + 1);
                    break;
                } else {
                    cur = cur.children[c - 'a'];
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for(String word : words) {
            result.append(word);
            result.append(" ");
        }

        return result.toString().trim();
    }

    public static void main(String[] args) {
        String[] rootWords = {"abc", "car", "race"};
        String sentence = "abcde cars ca bounse";
        Solution s = new Solution();
        System.out.println(s.replacePrefix(rootWords, sentence));
        return;
    }
}