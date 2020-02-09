// 20. Valid Parentheses
class Solution {
    public boolean isValid(String s) {
        char[] validParentheses = new char[256];
        validParentheses['('] = ')';
        validParentheses['['] = ']';
        validParentheses['{'] = '}';
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && validParentheses[stack.peek()] == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }
}

class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        if (s.length() == 0) {
            return true;
        }
        if (s.length() == 1) {
            return false;
        } else {
            int index = 0;
            stack.push(s.charAt(index));
            while (index < s.length() - 1) {
                index++;
                if (!stack.isEmpty() && isMatch(stack.peek(), s.charAt(index))) {
                    stack.pop();
                } else {
                    stack.push(s.charAt(index));
                }
            }
            if (!stack.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }
    
    public boolean isMatch(char a, char b) {
        // if ((a == '(' && b ==')') || (a == ')' && b == '(')) {
        //     return true;
        // }
        // if ((a == '[' && b ==']') || (a == ']' && b == '[')) {
        //     return true;
        // }
        // if ((a == '{' && b =='}') || (a == '}' && b == '{')) {
        //     return true;
        // }
        // 不用两种情况都判断，因为前者在上面代码中肯定是stack.peek()左括号，后者肯定是右括号
        // 鉴于此，还可以用 map 把一个当做key，一个当做value保存，这样更快
        if (a == '(' && b == ')') {
            return true;
        }
        if (a == '[' && b == ']') {
            return true;
        }
        if (a == '{' && b == '}') {
            return true;
        }
        return false;
    }
}