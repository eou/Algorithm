// 227. Basic Calculator II
// 无负数，只有加减乘除，无括号
class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int number = 0;
        char preChar = '+';
        
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(c == ' ') {
                continue;
            }
            if(Character.isDigit(c)) {
                number = number * 10 + c - '0';
            }
            if((!Character.isDigit(c)) || i == s.length() - 1) {
                if(preChar == '-') {
                    stack.push(-1 * number);
                }
                if(preChar == '+') {
                    stack.push(1 * number);
                }
                if(preChar == '*') {
                    stack.push(stack.pop() * number);
                }
                if(preChar == '/') {
                    stack.push(stack.pop() / number);
                }
                preChar = c;
                number = 0;
            }
        }

        int result = 0;
        for(Integer i : stack) {
            result += i;
        }
        return result;
    }
}

class Solution {
    // 不用栈的版本，result 保存上上个数字的结果，preVal 保存上个数字，curVal 保存当前数字
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        s = s.trim().replaceAll(" +", ""); // 其中的 + 是正则表达式，不是算式
        int result = 0;
        long preVal = 0;
        long curVal = 0;
        char preChar = '+';
        int i = 0;
        while (i < s.length()) {
            curVal = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                curVal = curVal * 10 + (s.charAt(i) - '0');
                i++;
            }
            if (preChar == '+') {
                result += preVal;
                preVal = curVal;
            } else if (preChar == '-') {
                result += preVal;
                preVal = -1 * curVal;
            } else if (preChar == '*') {
                preVal = preVal * curVal;
            } else if (preChar == '/') {
                preVal = preVal / curVal;
            }

            if (i < s.length()) {
                preChar = s.charAt(i);
                i++;
            }
        }

        result += preVal;
        return result;
    }
}