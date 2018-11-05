// 91. Decode Ways
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
    // dfs会TLE
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
