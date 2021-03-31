// 166. Fraction to Recurring Decimal
// Reminder will repeat. Edge case.
class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        if (denominator == 0) {
            return "";
        }

        if (numerator == 0) {
            return "0";
        }

        StringBuilder res = new StringBuilder();

        // If either one is negative (not both)
        if (numerator < 0 ^ denominator < 0) {
            res.append("-");
        }
        // Convert to Long or else abs(-2147483648) overflows
        long dividend = Math.abs(Long.valueOf(numerator));
        long divisor = Math.abs(Long.valueOf(denominator));

        // 1. reminder larger than 0
        res.append(String.valueOf(dividend / divisor));

        long remainder = dividend % divisor;
        if (remainder == 0) {
            return res.toString();
        }

        // 2. fraction
        res.append(".");
        Map<Long, Integer> map = new HashMap<>();
        while (remainder != 0) {
            if (map.containsKey(remainder)) {
                res.insert(map.get(remainder), "(");
                res.append(")");
                break;
            }

            map.put(remainder, res.length());

            remainder *= 10;
            res.append(String.valueOf(remainder / divisor));
            remainder %= divisor;
        }

        return res.toString();
    }
}