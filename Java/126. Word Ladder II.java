// 126. Word Ladder II
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> results = new ArrayList<>();
        Set<String> dic = new HashSet<>(wordList);
        // endWord 必须要在 wordList 中
        if (!dic.contains(endWord)) {
            return results;
        }

        // bfs 也要判断出 beginWord 到 endWord 的最短距离
        dic.add(beginWord);
        Map<String, Integer> map = new HashMap<>();
        minStepsToEnd(map, endWord, dic);

        List<String> curStep = new ArrayList<>();
        curStep.add(beginWord);
        helper(beginWord, endWord, dic, curStep, results, map);

        return results;
    }

    private void helper(String beginWord, String endWord, Set<String> dic, List<String> curStep,
            List<List<String>> results, Map<String, Integer> map) {
        if (beginWord.equals(endWord)) {
            results.add(new ArrayList<>(curStep));
            return;
        }

        for (String s : nextWord(beginWord, dic)) {
            if (map.get(s) != -1 && map.get(s) < map.get(beginWord)) {
                curStep.add(s);
                helper(s, endWord, dic, curStep, results, map);
                curStep.remove(curStep.size() - 1);
            }
        }
    }

    private void minStepsToEnd(Map<String, Integer> map, String endWord, Set<String> dic) {
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
        List<String> results = new ArrayList<>();
        StringBuilder strBuilder = new StringBuilder(curWord);

        for (int i = 0; i < strBuilder.length(); ++i) {
            char old = strBuilder.charAt(i);
            for (char c = 'a'; c <= 'z'; ++c) {
                if (c != old) {
                    strBuilder.setCharAt(i, c);
                    if (dic.contains(strBuilder.toString())) {
                        results.add(strBuilder.toString());
                    }
                }
            }
            strBuilder.setCharAt(i, old);
        }
        return results;
    }
}