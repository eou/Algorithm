// 680. Valid Palindrome II
class Solution {
    // 此版本不需一个个轮流删除字符来测试，而是遇到一个两边不对称的情况就删除左边或者右边的字符继续检测，时间复杂度是O(n)
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) {
                return helper(s, i + 1, j) || helper(s, i, j - 1);
            } else {
                i++;
                j--;
            }
        }
        
        return true;
    }
    
    // helper 与主函数代码非常相似
    private boolean helper(String s, int left, int right){
        int i = left, j = right;
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            } else {
                i++;
                j--;
            }
        }
        
        return true;
    }
}