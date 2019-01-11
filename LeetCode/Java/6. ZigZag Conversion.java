// 6. ZigZag Conversion
// 单纯 zigzag 找规律
class Solution {
    public String convert(String s, int numRows) {
        if(s.length() <= 1 || numRows <= 1) {
            return s;
        }

        StringBuilder result = new StringBuilder();
        int cycle = 2 * (numRows - 1);
        for(int i = 0; i < numRows; i++) {
            StringBuilder str = new StringBuilder();
            int index = i;
            int gap = i * 2;
            while(index < s.length()) {
                gap = cycle - gap;
                if(gap == 0) {
                    continue;
                }
                str.append(s.charAt(index));
                index += gap;
            }
            result.append(str);
        }
        
        return result.toString();
    }
}