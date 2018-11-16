// 269. Alien Dictionary
// Topological sorting
class Solution {
    // BFS版本，时间复杂度为 O(V + E)，空间复杂度为 O(V)，其中 V <= 26
    public String alienOrder(String[] words) {
        // node => neighbors
        Map<Character, Set<Character>> graph = new HashMap<>();
        // node => indegree
        Map<Character, Integer> indegree = new HashMap<>();
        StringBuilder result = new StringBuilder();

        if(words == null || words.length == 0) {
            return "";
        }

        // initialize indegree
        for(String s : words) {
            for(char c : s.toCharArray()) {
                indegree.put(c, 0);
            }
        }

        // build graph
        for(int i = 0; i < words.length - 1; i++) {
            String cur = words[i];
            String next = words[i + 1];
            int len = Math.min(cur.length(), next.length());
            for(int j = 0; j < len; j++) {
                char c1 = cur.charAt(j);
                char c2 = next.charAt(j);
                // find the first different character in the adjacent words
                if(c1 != c2) {
                    Set<Character> set = new HashSet<>();
                    if(graph.containsKey(c1)) {
                        set = graph.get(c1);
                    }
                    if(!set.contains(c2)) {
                        set.add(c2);
                        graph.put(c1, set);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        // BFS the graph
        Deque<Character> queue = new ArrayDeque<>();
        for(char c : indegree.keySet()) {
            // 如有多个入度为0的节点，全打入队列中
            if(indegree.get(c) == 0) {
                queue.offer(c);
            }
        }

        while(!queue.isEmpty()) {
            char c = queue.poll();
            result.append(c);
            if(graph.containsKey(c)) {
                for(char c2 : graph.get(c)) {
                    indegree.put(c2, indegree.get(c2) - 1);
                    // find the next character
                    if(indegree.get(c2) == 0) {
                        queue.offer(c2);
                    }
                }
            }
        }

        // exist cycles
        if(result.length() != indegree.size()) {
            return "";
        }

        return result.toString();
    }
}