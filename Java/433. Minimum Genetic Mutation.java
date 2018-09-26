// 433. Minimum Genetic Mutation
class Solution {
    // 题目中出现了 the minimum number of mutations needed to mutate from "start" to "end"，必然是BFS求最短路径
    // AAACC
    // AAACC CAACC GAACC TAACC
    // AAACC ACACC... CAACC CCACC... GAACC GCACC... TAACC TCACC...
    // AAACC...
    // 不过感觉用 DFS 和 回溯backtracking 也能做，但比较复杂
    public static char[] genes = { 'A', 'C', 'G', 'T' };

    public int minMutation(String start, String end, String[] bank) {
        if (start.equals(end)) {
            return 0;
        }

        // pre-processing the bank to reduce the string comparing time
        Set<String> bankSet = new HashSet<>();
        for (String str : bank) {
            bankSet.add(str);
        }

        Set<String> visited = new HashSet<>();

        // use BFS to find the correct mutation sequence
        Deque<String> queue = new ArrayDeque<>();
        queue.offer(start);
        visited.add(start);
        int level = 0;

        while (!queue.isEmpty()) {
            // need to figure out level at current loop
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String cur = queue.poll();
                if (cur.equals(end)) {
                    return level;
                } else {
                    for (int j = 0; j < cur.length(); ++j) {
                        // change one position in cur string each time
                        for (char c : genes) {
                            // only StringBuilder has method to change one position in String
                            StringBuilder strBuilder = new StringBuilder(cur);
                            strBuilder.setCharAt(j, c);
                            String tmp = strBuilder.toString();

                            // another way to change a char in String
                            // char[] currChars = cur.toCharArray();
                            // currChars[i] = c;
                            // String tmp = new String(currChars);

                            // find out if tmp is in the bank and if we have already created tmp before to prevent infinite loop
                            if (bankSet.contains(tmp) && !visited.contains(tmp)) {
                                queue.offer(tmp);
                                visited.add(tmp);
                                // *if we don't want to use the visited Set, we can remove the tmp from the bankSet each time, this is more concise
                                // bankSet.remove(tmp);
                            }
                        }
                    }
                }
            }
            level++;
        }

        return -1;
    }
}