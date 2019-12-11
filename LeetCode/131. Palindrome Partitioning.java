// 131. Palindrome Partitioning
class Solution {
    // 原函数直接递归；明显有不少重复计算可以剪枝或预处理，比如判断回文串和递归计算 partition(s.substring(i)); 可以用 memoization 记忆化搜索
    public List<List<String>> partition(String s) {
        List<List<String>> results = new ArrayList<>();

        if (s.length() == 0) {
            // 如果s为空也要打入一个空List
            results.add(new ArrayList<>());
            return results;
        }

        // 注意取子字符串substring的时候，区间右端点是开的，左闭右开，所以i == s.length()
        for (int i = 1; i <= s.length(); ++i) {
            String substr = s.substring(0, i);
            if (isPalin(substr)) {
                List<List<String>> tmp = partition(s.substring(i));
                for (List<String> t : tmp) {
                    t.add(0, substr);
                }
                results.addAll(tmp);
            }
        }
        return results;
    }

    private boolean isPalin(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}

class Solution {
    // 加了回文串预处理和 memoization，因此就不能用原函数 partition 中直接递归，参数不够，而且初始化 isPalin 只需一次
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();

        if (s == null) {
            result.add(new ArrayList<>());
            return result;
        }

        boolean[][] isPalin = getAllPalindromes(s);
        Map<Integer, List<List<String>>> memo = new HashMap<>();

        return dfs(isPalin, s, 0, memo);

    }

    private List<List<String>> dfs(boolean[][] isPalin, String s, int start, Map<Integer, List<List<String>>> memo) {
        if (start == s.length()) {
            List<List<String>> list = new ArrayList<>();
            list.add(new ArrayList<>());
            return list;
        }

        // 先查 memo 是否含有从第 start 位开始的字符串的回文串拆解数组
        if (memo.containsKey(start)) {
            return memo.get(start);
        } else {
            List<List<String>> list = new ArrayList<>();
            memo.put(start, list);
        }

        for (int i = start; i < s.length(); i++) {
            if (isPalin[start][i]) {
                List<List<String>> next = dfs(isPalin, s, i + 1, memo);
                for (List<String> path : next) {
                    List<String> list = new ArrayList<>();
                    list.add(s.substring(start, i + 1));
                    list.addAll(path);
                    memo.get(start).add(list);
                }
            }
        }

        return memo.get(start);
    }

    // 预处理回文串，区间型数据prework
    private boolean[][] getAllPalindromes(String s) {
        int n = s.length();
        // // isPalin[i][j] 代表 s.substring(i, j + 1) 是否是回文串
        boolean[][] isPalin = new boolean[n][n];

        isPalin[0][0] = true;
        for (int i = 1; i < n; i++) {
            // 单字符和双字符字符串可以直接得出
            isPalin[i][i] = true;
            isPalin[i - 1][i] = (s.charAt(i) == s.charAt(i - 1));
        }
        // 注意 i 从 n - 3 开始
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                isPalin[i][j] = isPalin[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
            }
        }

        return isPalin;
    }
}