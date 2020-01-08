// string circular rotation
class Solution {
    public static String getShiftedString(String str, int leftShifts, int rightShifts) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (leftShifts - rightShifts == 0) {
            return str;
        }

        int shift = (leftShifts - rightShifts + str.length()) % str.length();   // get remainder
        return str.substring(shift) + str.substring(0, shift);
    }

    public static void main(String[] args) {
        String str = "abcde";
        System.out.println(getShiftedString(str, 2, 1));
        System.out.println(getShiftedString(str, 1, 2));
    }
}