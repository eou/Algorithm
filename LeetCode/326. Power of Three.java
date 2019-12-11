class Solution {
    public boolean isPowerOfThree(int n) {
        if(n <= 0) {
            return false;
        }
        if(n == 1) {
            return true;
        }
        if(n % 3 != 0) {
            return false;
        }
        
        return isPowerOfThree(n / 3);
    }
}

class Solution {
    public boolean isPowerOfThree(int n) {
        if (n > 1) {
            while (n % 3 == 0) {
                n /= 3;
            }
        }

        return n == 1;
    }
}

class Solution {
    public boolean isPowerOfThree(int n) {
        // 1162261467 is 3^19, 3^20 is bigger than int
        return n > 0 && 1162261467 % n == 0;
    }
}

class Solution {
    public boolean isPowerOfThree(int n) {
        return new HashSet<>(Arrays.asList(1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441, 1594323,
                4782969, 14348907, 43046721, 129140163, 387420489, 1162261467)).contains(n);
    }
}
