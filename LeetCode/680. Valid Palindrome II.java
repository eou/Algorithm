// 680. Valid Palindrome II
class Solution {
    public boolean validPalindrome(String s) {
        // 此版本不需一个个轮流删除字符来测试，而是遇到一个两边不对称的情况就删除左边或者右边的字符继续检测，时间复杂度是O(n)
        for(int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if(s.charAt(i) != s.charAt(j)) {
                return isPalindrome(s.substring(i, j)) || isPalindrome(s.substring(i + 1, j + 1));
            }
        }
        return true;
    }
    
    private boolean isPalindrome(String s) {
        for(int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}