// 301. Remove Invalid Parentheses
class Solution {
    // 最简洁双指针DFS版本，时间复杂度O(n^2)
    public List<String> removeInvalidParentheses(String s) {
        List<String> results = new ArrayList<>();
        helper(s, 0, 0, '(', ')', results);
        return results;
    }
    
    private void helper(String s, int start, int delete, char open, char close, List<String> results) {
        int openNum = 0, closeNum = 0;
        for(int i = start; i < s.length(); i++) {
            if(s.charAt(i) == open) {
                openNum++;
            }
            if(s.charAt(i) == close) {
                closeNum++;
            }
            if(closeNum > openNum) {
                for(int j = delete; j <= i; j++) {
                    // 删除一个多余的右符号，并且要保证此删除操作不会重复，即之前没有同样的右符号，如"...)))" 只删除第一个)，或者刚好就是第一个字符
                    if(s.charAt(j) == close && (j == delete || s.charAt(j - 1) != close)) {
                        // 下一个从i开始，因为删除了一个字符导致下标前移
                        helper(s.substring(0, j) + s.substring(j + 1, s.length()), i, j, open, close, results);
                    }
                }
                // 注意在这里停止，只有当s调整完毕的时候不会进入这个if语句后才进行下一步
                return;
            }
        }
        
        // 反转非常重要，第一遍处理完毕后反转再处理，第二次处理完毕后反转就又变成正常顺序了
        String reversed = new StringBuilder(s).reverse().toString();
        if(open == '(') {
            helper(reversed, 0, 0, ')', '(', results);
        } else {
            results.add(reversed);
        }
    }
}

class Solution {
    // BFS版本，暴力枚举所有每一步所有可能子串，如果合法就再进行下一步
    // 时间复杂度需要计算，第一步n种情况，判断每个字符串是否合法时间为O(n)，第二步C(n, n-1)种情况（长度为n的字符串中找长度为n-1的字符串），判断每个字符串是否合法时间为O(n-1)……
    // 以此类推，T(n) = C(n, n)*O(n) + C(n, n-1)*O(n-1) + C(n, n-2)*O(n-2) + …… C(n, 1)*O(1)= n * 2^(n-1) = O(n2^n)
    public List<String> removeInvalidParentheses(String s) {
        List<String> results = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        boolean isfind = false;
        visited.add(s);
        queue.offer(s);

        while (!queue.isEmpty()) {
            String t = queue.poll();
            if (isValid(t)) {
                results.add(t);
                isfind = true;
            }
            if (isfind == true) {
                continue;
            }

            for (int i = 0; i < t.length(); i++) {
                if (t.charAt(i) != '(' && t.charAt(i) != ')') {
                    continue;
                }
                String str = t.substring(0, i) + t.substring(i + 1);
                if (!visited.contains(str)) {
                    queue.offer(str);
                    visited.add(str);
                }
            }
        }

        return results;
    }

    private boolean isValid(String s) {
        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                cnt++;
            } else if (s.charAt(i) == ')') {
                cnt--;
                if (cnt < 0) {
                    return false;
                }
            }
        }
        return cnt == 0;
    }
}

class Solution {
    // DFS版本
    public List<String> removeInvalidParentheses(String s) {
        List<String> results = new ArrayList<>();
        int openCnt = 0, closeCnt = 0;
        for (Character c : s.toCharArray()) {
            // 这里的两个计数变量不能用一个代替，因为 ")(" 和 "()" 不一样
            if (c == '(') {
                openCnt++;
            } else if (c == ')') {
                if (openCnt > 0) {
                    openCnt--;
                } else {
                    closeCnt++;
                }
            }
        }

        helper(s, 0, openCnt, closeCnt, results);
        return results;
    }

    private void helper(String s, int start, int openCnt, int closeCnt, List<String> results) {
        if (openCnt == 0 && closeCnt == 0) {
            if (isValid(s)) {
                results.add(s);
                return;
            }
        }

        for (int i = start; i < s.length(); i++) {
            if (openCnt > 0 && s.charAt(i) == '(') {
                if (i == start || s.charAt(i) != s.charAt(i - 1)) {
                    helper(s.substring(0, i) + s.substring(i + 1), i, openCnt - 1, closeCnt, results); // 不能写openCnt--
                }
            }

            if (closeCnt > 0 && s.charAt(i) == ')') {
                if (i == start || s.charAt(i) != s.charAt(i - 1)) {
                    helper(s.substring(0, i) + s.substring(i + 1), i, openCnt, closeCnt - 1, results); // 不能写closeCnt--
                }
            }
        }
    }

    private boolean isValid(String s) {
        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                cnt++;
            } else if (s.charAt(i) == ')') {
                cnt--;
                if (cnt < 0) {
                    return false;
                }
            }
        }
        return cnt == 0;
    }
}
