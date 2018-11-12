// 91. Decode Ways
// numDecodings(s) = numDecodings(s.substring(1)) + numDecodings(s.substring(2)) => Fibonacci sequence
class Solution {
    // 只能用DP做
    int num = 0;
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int[] dp = new int[s.length() + 1];
        // 要预设两个元素值
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for(int i = 2; i <= s.length(); i++) {
            int first = Integer.parseInt(s.substring(i - 1, i));
            int second = Integer.parseInt(s.substring(i - 2, i));
            if(first >= 1 && first <= 9) {
                dp[i] += dp[i - 1];
            }
            if(second >= 10 && second <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[s.length()];
    }
}

class Solution {
    // 更简洁的DP版本，斐波那契数列双值迭代
    // .....a,   b,  c
    // .....c2, c1,
    // .........c2,  c1 
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }

        // c2: decode ways of s[i-2] , c1: decode ways of s[i-1]
        int c1 = 1;
        int c2 = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                c1 = 0;
            }
            // possible two-digit letter, so new c1 is sum of both while new c2 is the old c1
            if (s.charAt(i - 1) == '1' || (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')) {
                c1 = c1 + c2;
                c2 = c1 - c2;
            } else {
                c2 = c1;
            }
        }

        return c1;
    }
}

class Solution {
    // DFS时间复杂度为 O(2^n)，会超时：（T(n) = T(n - 1) + T(n - 2) = ... = 2^(n - 2) * T(1)）
    public int numDecodings(String s) {
        if (s.isEmpty()) {
            return 1;
        }

        if (s.charAt(0) == '0') {
            return 0;
        }

        if (s.length() == 1)
            return 1;

        int sub1 = numDecodings(s.substring(1));
        int sub2 = 0;

        if (Integer.parseInt(s.substring(0, 2)) <= 26) {
            sub2 = numDecodings(s.substring(2));
        }

        return sub1 + sub2;
    }
}

class Solution {
    // 注意 int num = 0; 如果想在dfs函数中改变值，只能当全局变量，或者作为返回值
    // java中只有传值调用，没有传址调用；所以在java方法中改变参数的值是行不通的。但是可以改变引用变量的属性值
    int num = 0;
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        helper(s);
        return num;
    }
    
    public void helper(String s) {
        if (s.length() == 0) {
            num++;
            return;
        }
        
        for(int i = 0; i <= 1 && i < s.length(); i++) {
            String t = s.substring(0, i + 1);
            if (isValid(t)) {
                helper(s.substring(i + 1));
            }
        }
    }
    
    public boolean isValid(String s) {
        if (s.charAt(0) == '0') {
            return false;
        }
        
        int n = Integer.parseInt(s);
        return n >= 1 && n <= 26;
    }
}
