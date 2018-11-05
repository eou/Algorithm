// 433. Minimum Genetic Mutation
class Solution {
    // 题目中出现了 the minimum number of mutations needed to mutate from "start" to "end"，第一反应必然是BFS求最短路径
    // AAACC
    // AAACC CAACC GAACC TAACC
    // AAACC ACACC... CAACC CCACC... GAACC GCACC... TAACC TCACC...
    // AAACC...
    // 用 DFS 和 回溯backtracking 也能做，但比较复杂
    // 从第n步到第n + 1步，除了改第n步的字符串匹配bank里的字符串，还可以反过来遍历bank里的字符串考察哪一个能改成第n步的字符串
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

class Solution {
    // DFS的一个较简洁版本，思路是反过来从bank数组中出发，找一个只需要当前字符串改变一个字符的匹配字符串
    int count = Integer.MAX_VALUE;

    public int minMutation(String start, String end, String[] bank) {
        Set<String> visited = new HashSet<>();
        helper(start, end, bank, 0, visited);
        return count == Integer.MAX_VALUE ? -1 : count;
    }

    private void helper(String start, String end, String[] bank, int path, Set<String> visited) {
        // intern() decrease the memory use
        // the same as if (start.equals(end))
        // DFS是遍历出所有可能路径，求最短路径的话需要每次都更新最小路径
        if (start.intern() == end.intern()) {
            count = Math.min(count, path);
        }

        for (String s : bank) {
            int diff = 0;
            for (int i = 0; i < s.length(); i++) {
                if (start.charAt(i) != s.charAt(i)) {
                    diff++;
                    if (diff > 1) {
                        break;
                    } 
                }
            }
            if (diff == 1 && !visited.contains(s)) {
                // add and then remove 基本就是 DFS 解排列组合题的必需套路
                // 这里如果不用visited辅助，直接修改bank不行，因为在遍历bank的循环中修改容易报错
                visited.add(s);
                helper(s, end, bank, path + 1, visited); // 注意传参数同时加减的时候，不能path++ 或者 path--
                visited.remove(s);
            }
        }
    }
}

class Solution {
    // 根据上面的DFS，改了BFS的另一个思路版本
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

        // use BFS to find the correct mutation sequence
        Deque<String> queue = new ArrayDeque<>();
        queue.offer(start);
        int level = 0;

        while (!queue.isEmpty()) {
            // need to figure out level at current loop
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String cur = queue.poll();
                if (cur.equals(end)) {
                    return level;
                } else {
                    // 此处从bank数组出发找下一个合法字符串，不用visited数组，因为有bankSet
                    for (String s : bank) {
                        int diff = 0;
                        for (int j = 0; j < s.length(); j++) {
                            if (cur.charAt(j) != s.charAt(j)) {
                                diff++;
                                if (diff > 1) {
                                    break;
                                }
                            }
                        }
                        if (diff == 1 && bankSet.contains(s)) {
                            queue.offer(s);
                            bankSet.remove(s);
                        }
                    }
                }
            }
            level++;
        }

        return -1;
    }
}