// 472. Concatenated Words
// dfs
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> set = new HashSet<>();
        for (String word : words) {
            set.add(word);
        }
        
        List<String> res = new ArrayList<>();
        for (String word : words) {
            if (dfs(word, set)) {
                res.add(word);
            }
        }
        
        return res;
    }
    
    public boolean dfs(String word, Set<String> set) {
        boolean res = false;
        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);
            
            if (set.contains(prefix) && set.contains(suffix)) {
                return true;
            } else if (set.contains(suffix)) {
                res = res || dfs(prefix, set);
            } else if (set.contains(prefix)) {
                res = res || dfs(suffix, set);
            }
        }
        
        return res;
    }
}

// dfs with memo, faster
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> set = new HashSet<>();
        for (String word : words) {
            set.add(word);
        }

        Map<String, Boolean> memo = new HashMap<>();
        List<String> res = new ArrayList<>();
        for (String word : words) {
            if (dfs(word, set, memo)) {
                res.add(word);
            }
        }

        return res;
    }

    public boolean dfs(String word, Set<String> set, Map<String, Boolean> memo) {
        if (memo.containsKey(word)) {
            return memo.get(word);
        }

        boolean res = false;
        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);

            if (set.contains(prefix) && set.contains(suffix)) {
                memo.put(word, true);
                return true;
            } else if (set.contains(suffix)) {
                res = res || dfs(prefix, set, memo);
            } else if (set.contains(prefix)) {
                res = res || dfs(suffix, set, memo);
            }
        }

        memo.put(word, res);
        return res;
    }
}

// dfs with prunning
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> set = new HashSet<>();
        List<String> res = new ArrayList<>();

        int min = Integer.MAX_VALUE;
        for (String word : words) {
            if (word.length() == 0) {
                continue;
            }
            set.add(word);
            min = Math.min(min, word.length());
        }

        for (String word : words) {
            if (dfs(set, word, 0, min)) {
                res.add(word);
            }
        }
        return res;
    }

    private boolean dfs(Set<String> set, String word, int start, int min) {
        for (int i = start + min; i <= word.length() - min; i++) {
            if (set.contains(word.substring(start, i)) && (set.contains(word.substring(i)) || dfs(set, word, i, min))) {
                return true;
            }
        }
        return false;
    }
}

// Trie
class Solution {
    class TrieNode {
        boolean isWord;
        TrieNode[] next;

        public TrieNode() {
            isWord = false;
            next = new TrieNode[26];
        }
    }

    private TrieNode root;
    private void insert(String word) {
        if (word == null || word.length() == 0)
            return;

        TrieNode cur = root;
        for (char ch : word.toCharArray()) {
            if (cur.next[ch - 'a'] == null) {
                cur.next[ch - 'a'] = new TrieNode();
            }
            cur = cur.next[ch - 'a'];
        }
        cur.isWord = true;
    }

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) {
            return res;
        }

        root = new TrieNode();
        for (String word : words) {
            insert(word);
        }

        for (String word : words) {
            boolean[] dp = new boolean[word.length() + 1];
            dp[0] = true;

            for (int i = 0; i < word.length(); i++) {
                // is prefix?
                if (!dp[i]) {
                    continue;
                }
                    
                TrieNode cur = root;
                for (int j = i; j < word.length(); j++) {
                    int idx = word.charAt(j) - 'a';
                    if (cur.next[idx] == null) {
                        break;
                    }
                    cur = cur.next[idx];

                    if (i == 0 && j == word.length() - 1) {
                        continue;
                    }
                        
                    if (cur.isWord) {
                        dp[j + 1] = true;
                    }
                }

                if (dp[word.length()]) {
                    res.add(word);
                    break;
                }
            }
        }

        return res;
    }
}