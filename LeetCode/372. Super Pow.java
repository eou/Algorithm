// 372. Super Pow
class Solution {
    // a^1234567 % k = ((a^(123456 * 10) % k) * (a^7 % k)) % k = ((a^123456 % k)^10 % k * (a^7 % k)) % k
    // Time complexity is O(n), where n is the length of b.
    public int superPow(int a, int[] b) {
        return superPow(a, b, b.length);
    }
    
    // java 对于数组的修改不是很方便，所以利用重载 (Overload) 再写一个包含数组长度的同名函数
    private int superPow(int a, int[] b, int length) {
        if (length == 1) {
            return powMod(a, b[0], 1337);
        }
        
        return powMod(superPow(a, b, length - 1), 10, 1337) * powMod(a, b[length - 1], 1337) % 1337;
    }
    
    //x^y mod k，快速幂版本
    private int powMod(int x, int y, int k) {
        x %= k;
        int pow = 1;
        for (int i = y; i > 0; i /= 2) {
            if(i % 2 == 1) {
                pow = (pow * x) % k;
            }
            x = (x * x) % k;
        }

        return pow; 
    }
}

class Solution {
    // 更快的版本用到了欧拉定理，也就是费马小定理的拓展形式
    // phi(1337) = phi(7) * phi(191) = 6 * 190 = 1140
    // if a has both divisor 7 and 191, that's a % 1337 == 0, answer is 0
    // if a has neither divisor 7 nor 191, that's a and 1337 are coprime, so a^b % 1337 = a^(b % phi(1337)) % 1337 = a^(b % 1140) % 1337
    // if a could has either divisor 7 or 191, Let a = (7^n)x, and let b = 1140p + q, where 0 < q <= 1140, a^b % 1337 = a^q % 1337
    public int superPow(int a, int[] b) {
        if (a % 1337 == 0) {
            return 0;
        }
            
        int p = 0;
        for (int i : b) {
            p = (p * 10 + i) % 1140;
        }
        if (p == 0) {
            p += 1440;
        }
            
        return powMod(a, p, 1337);
    }

    // 快速幂另一种写法
    private int powMod(int x, int y, int k) {
        x %= k;

        int pow = 1;
        while (y > 0) {
            if ((y & 1) != 0) {
                pow = pow * x % k;
            }
            x = (x * x) % k;
            y >>= 1;
        }

        return pow;
    }
    // private int powMod(int x, int y, int k) {
    //     x %= k;
    //     int pow = 1;
    //     for (int i = y; i > 0; i /= 2) {
    //         if (i % 2 == 1) {
    //             pow = (pow * x) % k;
    //         }
    //         x = (x * x) % k;
    //     }

    //     return pow;
    // }
}