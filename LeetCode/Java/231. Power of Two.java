// 231. Power of Two
class Solution {
    public boolean isPowerOfTwo(int n) {
        // base case of recursion
        if(n <= 0) {
            return false;
        }
        if(n == 1) {
            return true;
        }
        if(n % 2 != 0) {
            return false;
        }
        return isPowerOfTwo(n / 2);
    }
}

class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n > 1) {
            while (n % 2 == 0) {
                n /= 2;
            }
        }

        return n == 1;
    }
}

class Solution {
    // power of 2 means only one bit of n is 1
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }

        return Integer.bitCount(n) == 1;
        // return (n & (n - 1)) == 0;
    }
}

class Solution {
    // 时间复杂度为 O(1)，但是实际运行时间偏慢
    public boolean isPowerOfTwo(int n) {
        return new HashSet<>(Arrays.asList(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608,16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824)).contains(n);
    }
}