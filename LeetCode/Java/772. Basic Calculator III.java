// 772. Basic Calculator III
// 无负数，有加减乘除和括号
class Solution {
    public int calculate(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }

        Deque<Integer> opdStack = new ArrayDeque<>();
        Deque<Character> optStack = new ArrayDeque<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(c == ' ') {
                continue;
            }

            if(Character.isDigit(c)) {
                int number = 0;
                while(i < s.length() && Character.isDigit(s.charAt(i))) {
                    number = number * 10 + (s.charAt(i) - '0');
                    i++;
                }
                i--;
                opdStack.push(number);
            } else if(c == '(') {
                optStack.push(c);
            } else if(c == ')') {
                while(optStack.peek() != '(') {
                    opdStack.push(helper(optStack.pop(), opdStack.pop(), opdStack.pop()));
                }
                optStack.pop(); // pop '('
            } else {
                while (!optStack.isEmpty() && precedence(c, optStack.peek())) {
                    opdStack.push(helper(optStack.pop(), opdStack.pop(), opdStack.pop()));
                }
                optStack.push(c);
            }
        }

        while(!optStack.isEmpty()) {
            opdStack.push(helper(optStack.pop(), opdStack.pop(), opdStack.pop()));
        }

        return opdStack.pop();
    }
    
    private int helper(char opt, int b, int a) {
        switch(opt) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b; // b != 0
        }
        return 0;
    }

    private boolean precedence(char opt1, char opt2) {
        if(opt2 == '(' || opt2 == ')') {
            return false;
        }
        if((opt1 == '*' || opt1 == '/') && (opt2 == '+' || opt2 == '-')) {
            return false;
        }
        return true;
    }
}