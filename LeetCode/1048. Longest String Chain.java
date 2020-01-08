// 1048. Longest String Chain
// Map
class Solution {
    // string length must be increasing
    public int longestStrChain(String[] words) {
        List<Integer> lens = new ArrayList<>();
        // Use map group strings of different length
        Map<Integer, List<String>> map = new HashMap<>();
        Map<String, Integer> resMap = new HashMap<>();
        for (String w : words) {
            if (!map.containsKey(w.length())) {
                lens.add(w.length());
            }
            List<String> list = map.getOrDefault(w.length(), new ArrayList<>());
            list.add(w);
            map.put(w.length(), list);
            resMap.put(w, 1); // at least 1 string
        }

        Collections.sort(lens);

        int result = 1;
        for (int i = 0; i < lens.size(); i++) {
            int len = lens.get(i);
            if (map.containsKey(len - 1)) {
                for (String cur : map.get(len)) {
                    for (String pre : map.get(len - 1)) {
                        if (valid(pre, cur)) {
                            resMap.put(cur, Math.max(resMap.get(cur), resMap.get(pre) + 1));
                            result = Math.max(result, resMap.get(cur));
                        }
                    }
                }
            }
        }

        return result;
    }

    public boolean valid(String word1, String word2) {
        if (word1.length() != word2.length() - 1) {
            return false;
        }

        int diff = 0, j = 0, i = 0;
        for (; i < word1.length(); i++) {
            if (j < word2.length() && word1.charAt(i) != word2.charAt(j)) {
                diff++;
                i--;
            }
            j++;
        }
        return (diff == 1 && j == word2.length()) || (j == word2.length() - 1 && i == word1.length());
    }
}

// DFS
class Solution {
    public int longestStrChain(String[] words) {
        return dfs(words, 0, new ArrayList<>());
    }

    public int dfs(String[] words, int level, List<String> cur) {
        boolean end = true;
        int result = 0;
        for (String w : words) {
            if (!cur.contains(w)) {
                if (cur.size() == 0 || cur.size() > 0 && valid(cur.get(cur.size() - 1), w)) {
                    end = false;
                    cur.add(w);
                    result = Math.max(result, dfs(words, level + 1, new ArrayList<>(cur)));
                    cur.remove(cur.size() - 1);
                }
            }
        }

        if (end) {
            return level;
        }

        return result;
    }

    public boolean valid(String word1, String word2) {
        if (word1.length() != word2.length() - 1) {
            return false;
        }

        int diff = 0, j = 0, i = 0;
        for (; i < word1.length(); i++) {
            if (j < word2.length() && word1.charAt(i) != word2.charAt(j)) {
                diff++;
                i--;
            }
            j++;
        }

        return (diff == 1 && j == word2.length()) || (j == word2.length() - 1 && i == word1.length());
    }
}

// DP
class Solution {
    public int longestStrChain(String[] words) {
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());

        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }
}