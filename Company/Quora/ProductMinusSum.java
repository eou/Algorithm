import java.util.*;

// 求一个数字所有位数的乘积减去所有位数的和（负数？）
class Solution {
    public int ProductMinusSum(int num) {
        if (num == 0) {
            return 0;
        }
        int product = 1, sum = 0;
        while (num > 0) {
            product *= num % 10;
            sum += num % 10;
            num /= 10;
        }
        return product - sum;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.ProductMinusSum(100));
        System.out.println(s.ProductMinusSum(123));
        System.out.println(s.ProductMinusSum(0));
        System.out.println(s.ProductMinusSum(19));
    }
}
