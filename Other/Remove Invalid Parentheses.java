/**
 * Remove Invalid Parentheses.java
 * 此题是 301. Remove Invalid Parentheses 的简化版，求出一个 valid parentheses的解即可
 */
class Solution {
    // two pass 版本，需要左右方向各遍历一遍，两遍代码其实一样，所以可以简化
    public String removeInvalidParentheses(String s, char c1, char c2) {
        if (s == null || s.length() == 0) {
            return s;
        }
            
        int left = 0;
        int right = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == c1) {
                left++;
                stringBuilder.append(c);
            } else if (c == c2) {
                if(left > right) {
                    right++;
                    stringBuilder.append(c);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        if(left != right) {
            return new StringBuilder(removeInvalidParentheses(stringBuilder.reverse().toString(), c2, c1)).reverse().toString();
        } else {
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().removeInvalidParentheses("(b()))a()", '(', ')'));
    }
}

class Solution {
    // one pass 版本，需要 stack 辅助，保存 invalid parentheses 下标最后删除
    public String removeInvalidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder(s);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                if (!stack.isEmpty() && s.charAt(stack.peek()) == '(') {
                    stack.pop();
                } else {
                    stack.push(i);
                }
            }
        }

        while (!stack.isEmpty()) {
            // 删除的时间复杂度有 O(n)
            stringBuilder.deleteCharAt(stack.pop());
        }

        return stringBuilder.toString();
    }
}

class Solution {
    // one pass 版本，需要中间缓存辅助，不需要最后删除 invalid parentheses
    public String removeInvalidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        StringBuilder buffer = new StringBuilder();
        List<StringBuilder> list = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                if (count >= list.size()) {
                    list.add(new StringBuilder(buffer));
                } else {
                    list.set(count, new StringBuilder(buffer));
                }

                buffer.setLength(0);
                count++;
            } else if (c == ')') {
                if (count > 0) {
                    StringBuilder tmp = new StringBuilder(buffer);
                    buffer.setLength(0);
                    buffer.append(new StringBuilder(list.get(count - 1)));
                    buffer.append("(");
                    buffer.append(tmp);
                    buffer.append(")");
                    count--;
                }
            } else {
                buffer.append(c);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(list.get(i));
        }
        return result.append(buffer).toString();
    }
}

class Solution {
    // 这是另一个 follow up，仅需计算 invalid parentheses 个数
    public int calculateInvalidParentheses(String s) {
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                if (left != 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        return left + right;
    }
}