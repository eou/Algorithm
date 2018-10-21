// 8. String to Integer (atoi)
// 此题与 65.Valid Number 一样臭名昭著，点赞和点灭数目前为605:4227
class Solution {
    public int myAtoi(String s) {
        s = s.trim();
        if(s.length() == 0) {
            return 0;
        }
        
        int i = 0;
        int sign = 1;
        if(s.charAt(i) == '-' || s.charAt(i) == '+') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        
        int base = 0;
        while(i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            // 这个细节请注意：整数范围为 -2147483648 ~ 2147483647，则 -2147483647 ~ 2147483647 都能直接当做正数算出
            if(base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && s.charAt(i) - '0' > 7)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            base = 10 * base + (s.charAt(i) - '0');
            i++;
        }
        
        return base * sign;
    }
}