// 168. Excel Sheet Column Title
// 测几个edge case: 1, 25, 26, 27就行
class Solution {
    public String convertToTitle(int n) {
        if(n < 1) {
            return "";
        }
        
        StringBuilder str = new StringBuilder();
        while(n != 0) {
            // 注意这里先减一再取余
            n--;
            str.insert(0, (char)('A' + (n % 26)));
            n /= 26;
        }
        return str.toString();
    }
}