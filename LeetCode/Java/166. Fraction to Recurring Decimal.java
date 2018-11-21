// 166. Fraction to Recurring Decimal
// 几个 edge case 需要注意
class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        if(denominator == 0) {
            return "";
        }

        if(numerator == 0) {
            return "0";
        }

        boolean negative = false;
        if((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0)) {
            negative = true;
        }

        StringBuilder result = new StringBuilder();
        if(negative) {
            result.append("-");
        }

        // 注意先转换成 long 再取绝对值，否则对 -2147483648 取绝对值会溢出
        long remainder = Math.abs((long)numerator);
        long divisor = Math.abs((long)denominator);

        // integer part
        result.append(remainder / divisor);
        remainder %= divisor;
        if(remainder == 0) {
            return result.toString();
        }
        
        // fractional part
        result.append(".");
        // remainder => index
        // maps the remainder to its position of the fractional part
        Map<Long, Integer> map = new HashMap<>();
        map.put(remainder, result.length());
        while(remainder != 0) {
            remainder *= 10;
            result.append(remainder / divisor);
            remainder %= divisor;
            if(map.containsKey(remainder)) {
                result.insert(map.get(remainder), "(");
                result.append(")");
                return result.toString();
            } else {
                map.put(remainder, result.length());
            }
        }
        return result.toString();
    }
}