// 692. Top K Frequent Words
// max-heap
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>(){
           public int compare(String s1, String s2) {
               if (map.get(s1) == map.get(s2)) {
                   return s1.compareTo(s2);
               }
               return map.get(s2) - map.get(s1);
           } 
        });
        for (String word : map.keySet()) {
            pq.offer(word);
        }
        
        List<String> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.add(pq.poll());
        }
        return res;
    }
}

// min-heap
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            public int compare(String s1, String s2) {
                if (map.get(s1) == map.get(s2)) {
                    return s2.compareTo(s1);
                }
                return map.get(s1) - map.get(s2);
            }
        });
        for (String word : map.keySet()) {
            pq.offer(word);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.add(pq.poll());
        }
        Collections.reverse(res);
        return res;
    }
}

// sort
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> count = new HashMap();
        for (String word : words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }

        List<String> candidates = new ArrayList(count.keySet());
        Collections.sort(candidates,
                (w1, w2) -> count.get(w1).equals(count.get(w2)) ? w1.compareTo(w2) : count.get(w2) - count.get(w1));

        return candidates.subList(0, k);
    }
}

// use trie tree counting frequency
class Solution {
    class TrieNode {
        TrieNode[] nodes;
        int freq;
        String word;

        public TrieNode() {
            nodes = new TrieNode[26];
            freq = 0;
            word = "";
        }
    }

    public List<String> topKFrequent(String[] words, int k) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0 || k <= 0) {
            return res;
        }

        TrieNode root = new TrieNode();
        List<String>[] buckets = new ArrayList[words.length + 1];
        for (int i = 0; i < words.length; i++) {
            addWord(words[i], root, 0);
        }

        getCount(root, buckets);

        int allowed = 1;
        // most frequency is words.length
        for (int i = words.length; i >= 0; i--) {
            if (buckets[i] != null) {
                List<String> outputs = buckets[i];
                for (int j = 0; j < outputs.size(); j++) {
                    res.add(outputs.get(j));
                    if (allowed == k) {
                        return res;
                    }
                    allowed++;
                }
            }
        }
        return res;
    }

    public void addWord(String word, TrieNode node, int pos) {
        if (pos == word.length()) {
            node.freq = node.freq + 1;
            node.word = word;
            return;
        }
        char c = word.charAt(pos);
        if (node.nodes[c - 'a'] == null) {
            node.nodes[c - 'a'] = new TrieNode();
        }
        addWord(word, node.nodes[c - 'a'], pos + 1);
    }

    public void getCount(TrieNode node, List<String>[] buckets) {
        TrieNode[] nodes = node.nodes;

        if (node.freq > 0) {
            int count = node.freq;
            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }
            buckets[count].add(node.word);
        }

        for (int i = 0; i < 26; i++) {
            if (nodes[i] != null) {
                getCount(nodes[i], buckets);
            }
        }
    }
}