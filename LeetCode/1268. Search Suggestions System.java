// 1268. Search Suggestions System
// https://www.acwing.com/file_system/file/content/whole/index/content/154170
// brute-force
class Solution {
    public Map<String, List<String>> map;
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        map = new HashMap<>();
        // only store product whose name is start with searchWord's first char
        for (String pd : products) {
            if (pd.charAt(0) == searchWord.charAt(0)) {
                store(pd);
            }
        }
        
        List<List<String>> res = new ArrayList<>();
        for (int i = 1; i <= searchWord.length(); i++) {
            List<String> resList = new ArrayList<>();
            String prefix = searchWord.substring(0, i);
            if (map.containsKey(prefix)) {
                List<String> list = map.get(prefix);
                Collections.sort(list);
                for (int j = 0; j < Math.min(list.size(), 3); j++) {
                    resList.add(list.get(j));
                }                
            }
            res.add(resList);
        }
        
        return res;
    }
    
    public void store(String product) {
        for (int i = 1; i <= product.length(); i++) {
            String prefix = product.substring(0, i);
            List<String> list = map.getOrDefault(prefix, new ArrayList<>());
            list.add(product);
            map.put(prefix, list);
        }
    }
}

// two pointer
// sort O(nLlogn), pointer, O(n + mL)
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        List<List<String>> res = new ArrayList();
        int l = 0, r = products.length - 1;

        // match each character in searchWord
        for (int i = 0; i < searchWord.length(); i++) {
            // find range
            while (l <= r && (products[l].length() <= i || products[l].charAt(i) != searchWord.charAt(i))) {
                l++;
            }
            while (l <= r && (products[r].length() <= i || products[r].charAt(i) != searchWord.charAt(i))) {
                r--;
            }
            List<String> prefixMatched = new ArrayList();
            for (int j = l; j < Math.min(l + 3, r + 1); j++) {
                prefixMatched.add(products[j]);
            }
            res.add(prefixMatched);
        }

        return res;
    }
}

// trie tree
// build tree O(L*∑L), query O(mL)
class Solution {
    class TrieNode {
        TrieNode[] next;
        // boolean isWord;
        PriorityQueue<String> pq;
        public TrieNode() {
            this.pq = new PriorityQueue<>();
            this.next = new TrieNode[26];
            // this.isWord = false;
        }
    }
    
    public void addWord(TrieNode trie, String word) {
        TrieNode ptr = trie;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (ptr.next[c - 'a'] == null) {
                ptr.next[c - 'a'] = new TrieNode();
            }
            ptr = ptr.next[c - 'a'];
            ptr.pq.add(word); // word's prefix
        }
        // ptr.isWord = true;
    }
    
    public List<List<String>> getPrefixWords(TrieNode trie, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        TrieNode ptr = trie;
        boolean noPrefix = false;
        int i = 0;
        for (; i < searchWord.length(); i++) {
            char c = searchWord.charAt(i);
            List<String> words = new ArrayList<>();
            if (ptr.next[c - 'a'] != null) {
                ptr = ptr.next[c - 'a'];
                for (int j = 0; j < 3 && ptr.pq.size() > 0; j++) {
                    words.add(ptr.pq.poll());
                }
                res.add(words);
            } else {
                // no such prefix
                noPrefix = true;
                break;
            }
        }
        
        // search breaks on some node of the trie
        if (noPrefix) {
            for (int j = i; j < searchWord.length(); j++) {
                res.add(new ArrayList<>());
            }
        }
        return res;
    }
    
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        TrieNode trie = new TrieNode();
        for (String pd : products) {
            if (pd.charAt(0) == searchWord.charAt(0)) {
                addWord(trie, pd);
            }
        }
        
        return getPrefixWords(trie, searchWord);
    }
}
