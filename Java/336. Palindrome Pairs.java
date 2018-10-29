// 336. Palindrome Pairs
// 暴力解法时间复杂度是 O(k * n^2)，n为总单词数，k为平均单词长度，会超时
class Solution {
    // 时间复杂度 O(n * k^2)
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> results = new ArrayList<>();
        if(words == null || words.length == 0) {
            return results;
        }
        
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        
        // n个单词，平均一个单词 k 次分隔，每次分隔判断两次是否为回文串
        for(int i = 0; i < words.length; i++) {
            // 注意 j <= word[i].length，最后空串也要判断一次，这样从 "" + s 一直判断到 s + ""
            // // ["a", ""] 返回 [[1, 0], [0, 1]]
            for(int j = 0; j <= words[i].length(); j++) {
                String left = words[i].substring(0, j);
                String right = words[i].substring(j);
                // 左右子串的判空能且仅能留一个，保证s自身为回文串时，s + s', s' + s 和 s' + s, s + s'的情况不重复
                // ["abcd","dcba"] 返回 [[1, 0], [0, 1]]
                // 若均判空，则自身为回文串的情况就被排除，如 "" + abcd 时无法匹配 "dcba" + abcd
                // 若均不判空，则 abcd 或 dcba 每个都能匹配自身为回文串的 [1, 0] 和 [0, 1] 两种情况，4种需要剔除2种
                if(isPalindrome(left)) {
                    String rightReverse = new StringBuilder(right).reverse().toString();
                    // map.get(rightReverse) != i 是为了防止单字符反转仍是自身
                    if(map.containsKey(rightReverse) && map.get(rightReverse) != i) {
                        results.add(Arrays.asList(map.get(rightReverse), i));
                    }
                }
                // 此处判一次空串
                if(right.length() != 0 && isPalindrome(right)) {
                    String leftReverse = new StringBuilder(left).reverse().toString();
                    // map.get(leftReverse) != i 是为了防止单字符反转仍是自身
                    if(map.containsKey(leftReverse) && map.get(leftReverse) != i) {
                        results.add(Arrays.asList(i, map.get(leftReverse)));
                    }
                }
            }
        }
        
        return results;
    }
    
    private boolean isPalindrome(String s) {
        for(int i = 0, j = s.length() - 1; i < j;) {
            if(s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        
        return true;
    }
}

class Solution {
    // Trie Tree 版本，将单词倒着存储在Trie中，时间复杂度 O(n * k^2)
    // 大意为 s1 + s2 形成回文串的时候，由于倒序存储，正序遍历 s1 的时候其实进入的是 s2 存储的树枝，这样形成了头尾对称遍历
    // 当到达 s1 或 s2 其中一个单词的结尾的时候，
    class TrieNode {
        TrieNode[] next;
        int isWord;
        List<Integer> list;

        TrieNode() {
            next = new TrieNode[26];
            isWord = -1;
            list = new ArrayList<>();
        }
    }

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> results = new ArrayList<>();

        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            addWord(root, words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            search(words, i, root, results);
        }

        return results;
    }

    private void addWord(TrieNode root, String word, int isWord) {
        // 倒序存储
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';
            if (root.next[j] == null) {
                root.next[j] = new TrieNode();
            }
            
            if (isPalindrome(word, 0, i)) {
                root.list.add(isWord);
            }

            root = root.next[j];
        }

        root.list.add(isWord);
        root.isWord = isWord;
    }

    private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
        // 正序匹配
        for (int j = 0; j < words[i].length(); j++) {
            // 如是 "cbdd" 匹配 "ddbca" 形成 "ddbcacbdd"：由于倒序存储，则正序遍历 "ddbca" 会进入 "cbdd" 的树枝，当遍历到 "a" 的时候，这里 isWord 标志着 "cbdd" 一个单词的结束，此时判断剩余部分是不是回文串即可
            if (root.isWord >= 0 && root.isWord != i && isPalindrome(words[i], j, words[i].length() - 1)) {
                res.add(Arrays.asList(i, root.isWord));
            }

            root = root.next[words[i].charAt(j) - 'a'];
            if (root == null) {
                return;
            }
        }

        // 如是 "abc" 匹配 "ddbca" 形成"abcddbca"：由于倒序存储，则正序遍历 "abc" 时会进入 "ddbca" 的树枝，当遍历完毕时到达 "dd"，此时判断剩余部分是不是回文串即可
        // 之前初始化的时候在结点中增加了一个 list，保存从这里开始前缀为回文串的字符串下标，遍历这个 list 即可
        for (int j : root.list) {
            if (i == j) {
                continue;
            }
            res.add(Arrays.asList(i, j));
        }
    }

    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) {
                return false;
            }
        }

        return true;
    }
}