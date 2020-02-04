// 425. Word Squares
// b a l l
// a r e a
// l e a d
// l a d y
// !!!
// really important characters are the characters around diagonal
//   o x - i
// o   x - i
// x x   - i
// - - -   i
// i i i i

// brute-force, find all possible permutation then check them, TLE, 12/16
class Solution {
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> res = new ArrayList<>();
        for (List<String> square :  wordSquarePermutation(words)) {
            if (validWordSquare(square)) {
                res.add(square);
            }
        }
        return res;
    }
    
    public List<List<String>> wordSquarePermutation(String[] words) {
        List<List<String>> res = new ArrayList<>();
        dfs(words, new ArrayList<>(), res);
        
        return res;
    }

    public void dfs(String[] words, List<String> permutation, List<List<String>> res) {
        if (permutation.size() == words[0].length()) {
            res.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < words.length; i++) {
            // improvement 1, check prefix
            permutation.add(words[i]);
            // improvement 2, validation
            dfs(words, permutation, res);
            permutation.remove(permutation.size() - 1);
        }
    }
    
    // LeetCode 422 
    public boolean validWordSquare(List<String> words) {
        if (words.size() == 0 || words == null) {
            return true;
        }

        for (int i = 0; i < words.size(); i++) {
            String tmp = words.get(i);
            for (int j = 0; j < tmp.length(); j++) {
                // too long
                if (j >= words.size()) {
                    return false;
                }

                // too short to form a correct vertical string
                if (words.get(j).length() <= i) {
                    return false;
                }

                // letter not equal
                if (tmp.charAt(j) != words.get(j).charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}

// pruning before dfs, TLE, 13/16
class Solution {
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> res = new ArrayList<>();
        dfs(words, new ArrayList<>(), res);

        return res;
    }

    public void dfs(String[] words, List<String> permutation, List<List<String>> res) {
        // exit
        if (permutation.size() == words[0].length()) {
            res.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < words.length; i++) {
            permutation.add(words[i]);
            // check if it is a valid intermediate state
            if (checkValid(permutation)) {
                dfs(words, permutation, res);
            }
            permutation.remove(permutation.size() - 1);
        }
    }

    public boolean checkValid(List<String> unfinishedSquare) {
        // check if word in ith column is the prefix of the word in ith row
        for (int i = 0; i < unfinishedSquare.size(); i++) {
            // word in the ith row
            String rowWord = unfinishedSquare.get(i);
            StringBuilder strBuilder = new StringBuilder();
            // word in the ith c
            for (int j = 0; j < unfinishedSquare.size(); j++) {
                strBuilder.append(unfinishedSquare.get(j).charAt(i));
            }

            if (!rowWord.startsWith(strBuilder.toString())) {
                return false;
            }
        }

        return true;
    }

    // check process improvement, still TLE
    public boolean checkValid(List<String> unfinishedSquare) {
        // check if word in ith column is the prefix of the word in ith row
        // WE ONLY NEED TO CHECK if word on last row has prefix on ith column
        int lastRowIndex = unfinishedSquare.size() - 1;
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < unfinishedSquare.size(); i++) {
            strBuilder.append(unfinishedSquare.get(i).charAt(lastRowIndex));
        }
        if (!unfinishedSquare.get(lastRowIndex).startsWith(strBuilder.toString())) {
            return false;
        }

        return true;
    }
}

// use Trie
class Solution {
    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        public TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }

        public void add(String word) {
            TrieNode ptr = this;
            for (char c : word.toCharArray()) {
                if (ptr.children[c - 'a'] == null) {
                    ptr.children[c - 'a'] = new TrieNode();
                }
                ptr = ptr.children[c - 'a'];
            }
            ptr.isWord = true;
        }

        public List<String> getWordsWithPrefix(String prefix) {
            List<String> res = new ArrayList<>();
            TrieNode ptr = this;
            for (char c : prefix.toCharArray()) {
                if (ptr.children[c - 'a'] != null) {
                    ptr = ptr.children[c - 'a'];
                } else {
                    return new ArrayList<>();
                }
            }
            return dfs(prefix, res, ptr);
        }

        public List<String> dfs(String cur, List<String> res, TrieNode node) {
            boolean end = true;
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    end = false;
                    dfs(new String(cur + ((char) ('a' + i) + "")), res, node.children[i]);
                }
            }

            if (end) {
                res.add(new String(cur));
            }

            return res;
        }
    }

    public TrieNode trie = new TrieNode();

    public List<List<String>> wordSquares(String[] words) {
        // preprocessing, add all words into trie tree
        for (String word : words) {
            trie.add(word);
        }

        List<List<String>> res = new ArrayList<>();
        dfs(words, new ArrayList<>(), res);

        return res;
    }

    public void dfs(String[] words, List<String> permutation, List<List<String>> res) {
        // exit
        if (permutation.size() == words[0].length()) {
            res.add(new ArrayList<>(permutation));
            return;
        }

        StringBuilder strBuilder = new StringBuilder();
        int lastRowIndex = permutation.size() - 1;
        for (int i = 0; i < permutation.size(); i++) {
            strBuilder.append(permutation.get(i).charAt(lastRowIndex + 1));
        }

        for (String word : trie.getWordsWithPrefix(strBuilder.toString())) {
            permutation.add(word);
            dfs(words, permutation, res);
            permutation.remove(permutation.size() - 1);
        }
    }
}

// much faster, storing words in each trie node
class Solution {
    class TrieNode {
        TrieNode[] children;
        List<String> words;

        public TrieNode() {
            children = new TrieNode[26];
            words = new ArrayList<>(); // storing word whose prefix is current trie tree path
        }

        public void add(String str, int index) {
            if (index == str.length()) {
                return;
            }

            int pos = str.charAt(index) - 'a';
            if (children[pos] == null) {
                children[pos] = new TrieNode();
            }

            words.add(str);
            children[pos].add(str, index + 1);

            return;
        }
    }

    public TrieNode trie = new TrieNode();

    public List<List<String>> wordSquares(String[] words) {
        // preprocessing, add all words into trie tree
        for (String word : words) {
            trie.add(word, 0);
        }

        List<List<String>> res = new ArrayList<>();
        dfs(words, new ArrayList<>(), res);

        return res;
    }

    public void dfs(String[] words, List<String> permutation, List<List<String>> res) {
        // exit
        if (permutation.size() == words[0].length()) {
            res.add(new ArrayList<>(permutation));
            return;
        }

        TrieNode ptr = trie;
        int lastRowIndex = permutation.size() - 1;
        for (int i = 0; i < permutation.size(); i++) {
            char c = permutation.get(i).charAt(lastRowIndex + 1);
            if (ptr.children[c - 'a'] != null) {
                ptr = ptr.children[c - 'a'];
            } else {
                return;
            }
        }

        if (ptr.words.size() == 0) {
            return;
        }

        for (String word : ptr.words) {
            permutation.add(word);
            dfs(words, permutation, res);
            permutation.remove(permutation.size() - 1);
        }
    }
}
