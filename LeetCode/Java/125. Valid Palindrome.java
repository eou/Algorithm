// 125. Valid Palindrome
class Solution {
    public boolean isPalindrome(String s) {
        if(s == "") {
            return true;
        }
        
        int i = 0, j = s.length() - 1;
        while(i < j) {
            char a = s.charAt(i);
            char b = s.charAt(j);
            
            // 记忆几个常见方法名字
            if(!Character.isLetterOrDigit(a)) {
                i++;
            }
            if(!Character.isLetterOrDigit(b)) {
                j--;
            }
            if(Character.isLetterOrDigit(a) && Character.isLetterOrDigit(b)) {
                if(Character.toLowerCase(a) != Character.toLowerCase(b)) {
                    return false;
                } else {
                    i++;
                    j--;
                }
            }
        }
        
        return true;
    }
}