//342. Power of Four
class Solution {
    public boolean isPowerOfFour(int n) {
        if(n <= 0) {
            return false;
        }
        if(n == 1) {
            return true;
        }
        if(n % 4 != 0) {
            return false;
        }
        
        return isPowerOfFour(n / 4);
    }
}

class Solution {
    public boolean isPowerOfFour(int n) {
        if (n > 1) {
            while (n % 4 == 0) {
                n /= 4;
            }
        }

        return n == 1;
    }
}

class Solution {
    // 0x55555555 is to get rid of those power of 2 but not power of 4 so that the single 1 bit always appears at the odd position 
     public boolean isPowerOfFour(int num) {
        return num > 0 && (num & (num - 1)) == 0 && (num & 0x55555555) != 0;
        // return num > 0 && (num & (num - 1)) == 0 && (num - 1) % 3 == 0;
    }
}