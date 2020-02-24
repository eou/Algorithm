// 32. Longest Valid Parentheses
// brute force, O(n^3), TLE
class Solution {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0 ) {
            return 0;
        }
        
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                res = checkValidity(s.substring(i, j)) ? Math.max(res, j - i) : res;
            }
        }
        
        return res;
    }
    
    public boolean checkValidity(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (Character c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else {
                if (stack.isEmpty() || stack.peek() != '(') {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }
}

// 2d-dp, O(n^3), TLE
// 1d-dp, O(n), only consider ending character
class Solution {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int res = 0;
        // dp[i] means longest valid parentheses end with s[i]
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {  // start with 1
            // only possible with ')'
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // ...()
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    // ..((...))
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }
}

// stack, O(n)
// this idea for check (...) is to substract index of '(' and ')' 
class Solution {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    // need a index indicating ending index
                    stack.push(i);
                } else {
                    res = Math.max(res, i - stack.peek());
                }
            }
        }

        return res;
    }
}

// O(1) space, tricky
class Solution {
    public int longestValidParentheses(String s) {
        int left = 0, right = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                res = Math.max(res, 2 * right);
            } else if (right >= left) {
                left = right = 0;
            }
        }

        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                res = Math.max(res, 2 * left);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return res;
    }
}