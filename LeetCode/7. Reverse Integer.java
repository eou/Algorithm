// 7. Reverse Integer
class Solution {
    public int reverse(int x) {
        int n = x;
        // 用 long 存储可能int溢出的数字，但是不是最佳的方法
        long res = 0;
        while (n != 0) {
            res *= 10;
            res += n % 10;
            n /= 10;
        }
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) res;
    }
}

class Solution {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            // 这种处理判断溢出也很巧妙
            if ((newResult - tail) / 10 != result) {
                return 0;
            }
            result = newResult;
            x = x / 10;
        }

        return result;
    }
}

class Solution {
    // LeetCode官方solution
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            // 最大最小值为 2147483647, -2147483648，这种解法连整个判断过程中都不会算到溢出
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }
}