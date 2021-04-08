// 402. Remove K Digits
// brute-force, O(NK)
class Solution {
    public String removeKdigits(String num, int k) {
        if (num == null || num.length() == 0 || k == 0) {
            return num;
        }
        
        if (num.length() == k) {
            return "0";
        }
        
        String res = num;
        for (int i = 0; i < k; i++) {
            res = removeOneDigit(res);
        }
        
        int i = 0;
        for (; i < res.length(); i++) {
            if (res.charAt(i) != '0') {
                break;
            }
        }
        return i == res.length() ? "0" : res.substring(i);
    }
    
    private String removeOneDigit(String num) {
        // find the first increase sequence then remove the last digit of the sequence
        for (int i = 0; i < num.length() - 1; i++) {
            if (num.charAt(i) > num.charAt(i + 1)) {
                return num.substring(0, i) + num.substring(i + 1);
            }
        }
        
        return num.substring(0, num.length() - 1);
    }
}

// Monotonic stack, O(n)
class Solution {
    public String removeKdigits(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < num.length(); ++i) {
            char digit = num.charAt(i);
            while (!stack.isEmpty() && k > 0 && stack.peek() > digit) {
                stack.pop();
                k--;
            }
            stack.push(digit);
        }
        
        // pop more to pop k numbers
        for (int i = 0; i < k; ++i) {
            stack.pop();
        }
        
        StringBuilder res = new StringBuilder();
        boolean leadingZero = true;
        while (!stack.isEmpty()) {
            char digit = stack.pollLast();
            if (leadingZero && digit == '0') {
                continue;
            }
            leadingZero = false;
            res.append(digit);
        }

        return res.length() == 0 ? "0" : res.toString();
    }
}