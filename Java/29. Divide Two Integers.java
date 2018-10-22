// 29. Divide Two Integers
// 难点在于 -2147483648 / 1 = 2147483647 和 -2147483648 做被除数
// 要么用long，要么全换成负数运算
class Solution {
    public int divide(int dividend, int divisor) {
        int sign = 1;
        if((dividend > 0 && divisor < 0) || (divisor > 0 && dividend < 0)) {
            sign = -1;
        }
        
        // 先变类型再取绝对值，否则 -2147483648 会出错
        // long ldividend = (long) (Math.abs(dividend));
        // long ldivisor = (long) (Math.abs(divisor));
        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);
        // 这个判断不能写在开头，否则遇到正数大于负数的情况会报错，如-1 / 1 = 0
        if(ldivisor > ldividend || ldividend == 0) {
            return 0;
        }
        long lresult = ldivide(ldividend, ldivisor);
        
        if(lresult > Integer.MAX_VALUE) {
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            return (int) (sign * lresult);
        }
    }
    
    private long ldivide(long dividend, long divisor) {
        // 递归出口
        if(divisor > dividend) {
            return 0;
        }
        
        long sum = divisor;
        long result = 1;
        while(sum + sum <= dividend) {
            sum += sum;
            result += result;
        }
        return result + ldivide(dividend - sum, divisor);
    }
}

class Solution {
    // 由于edge case只有一个 Integer.MIN_VALUE / -1 = Integer.MAX_VALUE，所以可以将这个提出来，其余全部计算用整型范围更大的负数计算，不需要long
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
            
        int sign = 1;
        if ((dividend > 0 && divisor < 0) || (divisor > 0 && dividend < 0)) {
            sign = -1;
        }

        int _dividend = (-1) * Math.abs(dividend);
        int _divisor = (-1) * Math.abs(divisor);
        return sign * divideHelper(_dividend, _divisor);
    }

    // 如果全换成正整数运算，需要把除数和被除数是 Integer.MIN_VALUE 的情况都当做 edge cases 单独处理，多余处理太多
    // 由于 int x = Math.abs(x); 当 x = -2147483648 的时候，x 取绝对值还是 -2147483648，所以全部换成负数运算比较方便
    private int divideHelper(int dividend, int divisor) {
        if (divisor < dividend) {
            return 0;
        }
        
        int sum = divisor, result = 1;
        // 注意 sum + sum < 0 是专门为 -2147483648 准备的
        // 因为当 -1 倍增到 -1073741824 的时候，如果是绝对值小于 -2147483648 的数字就会在下一次跳出循环，因为 sum + sum = -2147483648 < dividend, 而 -2147483648会进入然后在下一次判断的时候 sum + sum = 0 死循环
        while (sum + sum >= dividend && sum + sum < 0) {
            sum += sum;
            result += result;
        }

        // 这个判断仅仅是一个优化，不影响结果
        if (dividend - sum > divisor) {
            return result;
        }
        return result + divide(dividend - sum, divisor);

        // 位运算的对应写法
        // int cur = 0, res = 0;
        // while ((divisor << cur) >= dividend && (divisor << cur) < 0 && cur < 31) {
        //     cur++;
        // }

        // res = dividend - (divisor << cur - 1);
        // if (res > divisor) {
        //     return 1 << cur - 1;
        // }
        // return (1 << cur - 1) + divide(res, divisor);
    }
}