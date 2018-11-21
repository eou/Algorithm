/**
 * Factorial of a large number
 * https://www.geeksforgeeks.org/factorial-large-number/
 */
import java.io.*;
import java.util.*;

class Solution {
    public int length = 1;

    public String factorial(int x) {
        StringBuilder strBuilder = new StringBuilder();
        int[] result = new int[500];
        result[0] = 1;
        length = 1;

        for (int i = 2; i <= x; i++) {
            multiply(i, result);
        }

        for (int i = length - 1; i >= 0; i--) {
            strBuilder.append(result[i]);
        }

        return strBuilder.toString();
    }

    public void multiply(int x, int[] result) {
        int carry = 0;
        for (int i = 0; i < length; i++) {
            int product = result[i] * x + carry;
            result[i] = product % 10;
            carry = product / 10;
        }

        while (carry != 0) {
            result[length] = carry % 10;
            carry /= 10;
            length++;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().factorial(100));
    }
}
