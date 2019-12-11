// 50. Pow(x, n)
// Pow(x, n) = Pow(x, n / 2) * Pow(x, n / 2) (if n % 2 = 0)
// = Pow(x, n / 2) * Pow(x, n / 2) * x (if n % 2 = 1)
class Solution {
    // brute force 时间复杂度 O(n)，会超时
    public double myPow(double x, int n) {
        long N = n;
        if(N < 0) {
            x = 1 / x;
            N = -N;
        }
        
        double result = 1;
        for(long i = 0; i < N; i++) {
            result *= x;
        }
        
        return result;
    }
}

class Solution {
    // Fast Power Algorithm 非递归版本，时间复杂度 O(logn)
    public double myPow(double x, int n) {
        long N = n;
        if(N < 0) {
            x = 1 / x;
            N = -N; // 注意不能写成 N = -n
        }
        
        double result = 1;
        double current = x;
        // N = 1101010..1...010100011 = ∑(2^i * b) => x^N = x^(∑(2^i * b)), (b = 0, 1)
        // 将 N 转换为二进制，从低位到高位，遇到 1 就乘一次
        for(long i = N; i > 0; i /= 2) {
            if(i % 2 == 1) {
                result *= current;
            }
            // x = x^2
            current *= current;
        }
        
        return result;
    }
}

class Solution {
    // Fast Power Algorithm 递归版本
    public double myPow(double x, int n) {
        long N = n;
        if(N < 0) {
            x = 1 / x;
            N = -N;
        }
        
        return helper(x, N);
    }
    
    public double helper(double x, long n) {
        if(n == 0) {
            return 1.0;
        }
        
        double half = helper(x, n / 2);
        if(n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
}

class Solution {
    // Fast Power Algorithm 递归版本
    public double myPow(double x, int n) {
        if(n == 0) {
            return 1;
        }
        if(n == 1) {
            return x;
        }

        int N = n / 2;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double result = myPow(x, N);
        if(n % 2 == 0) {
            return result * result;
        }
        return result * result * x;
    }
}

class Solution {
    // Fast Power Algorithm 递归另一版本
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }

        double half = myPow(x, n / 2);

        if (n % 2 == 0) {
            return half * half;
        } else if (n > 0) {
            return half * half * x;
        } else {
            return half * half / x;
        }
    }
}