// 126. Word Ladder II
// acwing.com/solution/LeetCode/content/217/
// BFS + DFS
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> dic = new HashSet<>(wordList);
        // endWord 必须要在 wordList 中
        if (!dic.contains(endWord)) {
            return res;
        }

        // bfs 也要判断出 beginWord 到 endWord 的最短距离
        dic.add(beginWord);
        // !!! <word, the distance between word and endWord>
        Map<String, Integer> map = new HashMap<>();
        bfs(map, endWord, dic);

        List<String> curList = new ArrayList<>();
        curList.add(beginWord);
        dfs(beginWord, endWord, dic, curList, res, map);

        return res;
    }

    private void dfs(String beginWord, String endWord, Set<String> dic, List<String> curList,
            List<List<String>> res, Map<String, Integer> map) {
        if (beginWord.equals(endWord)) {
            res.add(new ArrayList<>(curList));
            return;
        }

        for (String s : nextWord(beginWord, dic)) {
            if (map.get(s) < map.get(beginWord)) {
                curList.add(s);
                dfs(s, endWord, dic, curList, res, map);
                curList.remove(curList.size() - 1);
            }
        }
    }

    private void bfs(Map<String, Integer> map, String endWord, Set<String> dic) {
        Deque<String> queue = new ArrayDeque<>();
        // 从 endWord 倒着 bfs
        queue.offer(endWord);
        map.put(endWord, 0);

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (String next : nextWord(cur, dic)) {
                if (!map.containsKey(next)) {
                    map.put(next, map.get(cur) + 1);
                    queue.offer(next);
                }
            }
        }
    }

    private List<String> nextWord(String curWord, Set<String> dic) {
        List<String> res = new ArrayList<>();
        StringBuilder strBuilder = new StringBuilder(curWord);

        for (int i = 0; i < strBuilder.length(); ++i) {
            char old = strBuilder.charAt(i);
            for (char c = 'a'; c <= 'z'; ++c) {
                if (c != old) {
                    strBuilder.setCharAt(i, c);
                    if (dic.contains(strBuilder.toString())) {
                        res.add(strBuilder.toString());
                    }
                }
            }
            strBuilder.setCharAt(i, old);
        }
        return res;
    }
}

// Doesn't save the distance between each word and end word, TLE
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        if (beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || wordList == null
                || wordList.size() == 0) {
            return res;
        }

        Set<String> wordSet = new HashSet<>(wordList);
        int shortest = bfs(beginWord, endWord, wordSet);

        List<String> curList = new ArrayList<>();
        curList.add(beginWord);
        dfs(res, shortest, endWord, wordSet, curList, new HashSet<>());

        return res;
    }

    private int bfs(String beginWord, String endWord, Set<String> wordSet) {
        Set<String> visited = new HashSet<>();

        Deque<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        visited.add(beginWord);

        int res = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            res++;
            for (int i = 0; i < size; i++) {
                String curWord = queue.poll();
                List<String> nextWords = createNextWordList(curWord, wordSet);
                for (String nextWord : nextWords) {
                    if (nextWord.equals(endWord)) {
                        return res;
                    }
                    if (!visited.contains(nextWord)) {
                        queue.offer(nextWord);
                        visited.add(nextWord);
                    }
                }
            }
        }

        return res;
    }

    private void dfs(List<List<String>> res, int shortest, String endWord, Set<String> wordSet, List<String> curList,
            Set<String> visited) {
        if (curList.size() > shortest) {
            return;
        }

        String curWord = curList.get(curList.size() - 1);
        if (curWord.equals(endWord)) {
            res.add(new ArrayList<>(curList));
            return;
        }

        List<String> nextWords = createNextWordList(curWord, wordSet);
        for (String nextWord : nextWords) {
            if (!visited.contains(nextWord)) {
                visited.add(nextWord);
                curList.add(nextWord);
                dfs(res, shortest, endWord, wordSet, curList, visited);
                visited.remove(nextWord);
                curList.remove(curList.size() - 1);
            }
        }
    }

    private List<String> createNextWordList(String curWord, Set<String> wordSet) {
        List<String> res = new ArrayList<>();

        char[] arr = curWord.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char old = arr[i];
            for (int j = 0; j < 26; j++) {
                arr[i] = (char) ('a' + j);
                String nextWord = String.valueOf(arr);
                if (wordSet.contains(nextWord) && !curWord.equals(nextWord)) {
                    res.add(nextWord);
                }
            }
            arr[i] = old;
        }

        return res;
    }
}