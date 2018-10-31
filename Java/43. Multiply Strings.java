// 43. Multiply Strings
class Solution {
    // 按照普通乘法手算规则一位位计算，非常规范的代码
    public String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        int pos[] = new int[len1 + len2];
        
        // 如 123 × 45，从最后的低位开始计算，存储的位置也是从后面的低位开始
        for(int i = len1 - 1; i >= 0; i--) {
            for(int j = len2 - 1; j >= 0; j--) {
                int multiply = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = pos[i + j + 1] + multiply;
                
                // 进位
                pos[i + j] += sum / 10;
                // 本位
                pos[i + j + 1] = sum % 10;
            }
        }
        
        StringBuilder result = new StringBuilder();
        for(int i : pos) {
            // 从高位开始 append，如果结果为空，0就不用写入，首位必须非0
            if(result.length() != 0 || i != 0) {
                result.append(i);
            }
        }
        
        return result.length() == 0 ? "0" : result.toString();
    }
}