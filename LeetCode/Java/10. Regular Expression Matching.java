// 10. Regular Expression Matching
// 类似91.Decode Ways
class Solution {
    // 递归基础版本，四种情况：s和p是否为空字符串
    // case1 和 case3 可以合并为 p.isEmpty()
    // 然后在 firstMatch 中合并一个 !s.Empty() 条件，case2 和 case4.2 于是都在 !firstMatch 中，case2是 不符合 !s.Empty()，case4.2 是不符合后半段(s.charAt(0) == p.charAt(0) || p.charAt(0) == '.')
    // 这样就只剩下 if (p.length() > 1 && p.charAt(1) == '*') {} 一个 if 条件语句
    public boolean isMatch(String s, String p) {
        if (s.isEmpty()) {
            if (p.isEmpty()) {
                // case 1
                return true;
            } else {
                // case 2
                if (p.length() > 1 && p.charAt(1) == '*') {
                    return isMatch(s, p.substring(2));
                } else {
                    return false;
                }
            }
        } else {
            if (p.isEmpty()) {
                // case 3
                return false;
            } else {
                // case 4
                boolean firstMatch = (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
                if (firstMatch) {
                    // case 4.1
                    if (p.length() > 1 && p.charAt(1) == '*') {
                        return isMatch(s, p.substring(2)) || isMatch(s.substring(1), p);
                    } else {
                        return isMatch(s.substring(1), p.substring(1));
                    }
                } else {
                    // case 4.2
                    if (p.length() > 1 && p.charAt(1) == '*') {
                        return isMatch(s, p.substring(2));
                    } else {
                        return false;
                    }
                }
            }
        }
    }
}

class Solution {
    /**
     * "aa" "a"
     * 
     * "mississippi" "mis*is*p*."
     * 
     * "aab" "c*a*b"
     * 
     * "ab" ".*"
     * 
     * "ab" ".*c"
     * 
     * "aa" "a*"
     * 
     * "a" "ab*"
     * 
     * "a" ".*.."
     * 
     * "" "c*c*"
     */
    public boolean isMatch(String s, String p) {
        // use first character of p matching s
        // use first 2 characters of p matching s
        if (p.isEmpty()) {
            if (s.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (s.isEmpty()) {
                if (p.length() >= 2 && p.charAt(1) == '*') {
                    return isMatch(s, p.substring(2));
                } else {
                    return false;
                }
            } else if (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.') {
                if (p.length() > 1 && p.charAt(1) == '*') {
                    boolean res = false;
                    if (p.length() > 2) {
                        res = res || isMatch(s, p.substring(2));
                    }
                    return res || isMatch(s.substring(1), p);
                } else {
                    return isMatch(s.substring(1), p.substring(1));
                }
            } else if (s.charAt(0) != p.charAt(0) && p.length() > 1 && p.charAt(1) == '*') {
                return isMatch(s, p.substring(2));
            } else {
                return false;
            }
        }
    }
}

class Solution {
    // 递归简化版本，时间复杂度计算比较复杂，为O((S+P)*2^(S+P/2))，可以看做O(n2^n)
    public boolean isMatch(String s, String p) {
        if(p.isEmpty()) {
            return s.isEmpty();
        }
        
        boolean singleMatch = (!s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.'));
        
        // 如果模式串长度至少为2
        // 如果模式串第二个字符为*，有两个选择，因为*可以代表任意数量个前个字符，包括0个
        // 所以如果第 2 位为 *，可以当做模式串这两位不存在，继续匹配；也可以当第一位匹配成功，继续用来匹配匹配串的下一位
        if(p.length() > 1 && p.charAt(1) == '*') {
            return (isMatch(s, p.substring(2)) || (singleMatch && isMatch(s.substring(1), p)));
        // 如果模式串第二个字符不为*，或者模式串长度为1，则正常匹配一位
        } else {
            return singleMatch && isMatch(s.substring(1), p.substring(1));
        }
    }
}

class Solution {
    // Bottom-up DP版本，从尾部到头部匹配字符串
    // 此二维DP数组有2个状态，因为不会提前经过未检测的字符
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        // 空字符串匹配
        dp[s.length()][p.length()] = true;

        for (int i = s.length(); i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                // 这一段与递归思路很像
                boolean firstMatch = (i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));

                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    dp[i][j] = (dp[i][j + 2] || firstMatch && dp[i + 1][j]);
                } else {
                    dp[i][j] = (firstMatch && dp[i + 1][j + 1]);
                }
            }
        }

        return dp[0][0];
    }
}

class Solution {
    // Top-down DP版本，时间复杂度O(S*P)
    // 二维DP数组有3个状态，1是匹配，0是未检测，-1是不匹配
    int[][] dp;
    public boolean isMatch(String s, String p) {
        dp = new int[s.length() + 1][p.length() + 1];
        return helper(0, 0, s, p);
    }

    private boolean helper(int i, int j, String s, String p) {
        if (dp[i][j] != 0) {
            return dp[i][j] == 1;
        }

        // 思路跟从前往后遍历的递归版本基本一致，仅仅是最后把结果保存在DP数组中作为中间结果，减少重复验证次数
        boolean result;
        if (j == p.length()) {
            result = i == s.length();
        } else {
            boolean firstMatch = (i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));

            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                result = (helper(i, j + 2, s, p) || (firstMatch && helper(i + 1, j, s, p)));
            } else {
                result = firstMatch && helper(i + 1, j + 1, s, p);
            }
        }

        dp[i][j] = result == true ? 1 : -1;
        return result;
    }
}
