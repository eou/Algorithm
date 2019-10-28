import java.util.*;

// Find how many numbers have even digit in a list
class Solution {
    public int numberHaveEvenDigit(int[] a) {
        int res = 0;
        for (int i : a) {
            while (i > 0) {
                if ((i % 10) % 2 == 0) {
                    res++;
                    break;
                }
                i /= 10;
            }
        }
        return res;
    }

    public static void main(String[] args) { 
        Solution s = new Solution();
        int[] num = new int[]{12, 3, 5, 3456};
        System.out.println(s.numberHaveEvenDigit(num));
    }
}
