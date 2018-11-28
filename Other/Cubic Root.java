/** 
 * Cubic Root.java
 * 开一个数的三次方
*/
import java.io.*;
import java.util.*;
class Solution {
    // y = x^(1/3) 为奇函数，在 (-∞, -1), (-1, 0), (0, 1), (1, +∞) 之间的增减性不同，但是是相对原点对称的
    public double cubicRoot(double num) {
        if(num == 0) {
            return 0;
        }

        int sign = 1;
        double err = 10e-15;
        if(num < 0) {
            sign = -1;
            num *= -1;
        }

        double start = Math.min(1.0, num);
        double end = Math.max(1.0, num);
        double mid = 0;
        while(start <= end) {
            mid = start + (end - start) / 2;
            double diff = mid * mid * mid - num;
            if(-err < diff && diff < err) {
                break;
            }
            // 这里不存在无限循环，因为 mid 为小数，不会向下取整
            if(diff >= err) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if(sign == -1) {
            mid *= sign;
        }
        return mid;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().cubicRoot(8.12288));
        System.out.println(new Solution().cubicRoot(0.40033));
        System.out.println(new Solution().cubicRoot(-0.40033));
        System.out.println(new Solution().cubicRoot(-10.40033));
    }
}