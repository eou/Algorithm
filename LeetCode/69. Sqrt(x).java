// 69. Sqrt(x)
class Solution {
    public int mySqrt(int x) {
        int left = 0, right = x;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            // Use x / mid instead of mid * mid!!!
            if (mid == x / mid) {
                return mid;
            } else if (mid > x / mid) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        
        return right * right <= x ? right : left;
    }
}

// Still use mid * mid
class Solution {
    public int mySqrt(int x) {
        int left = 0, right = 1;
        while (right * right > 0) {
            right += 1;
        }
        right--;

        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (mid * mid == x) {
                return mid;
            } else if (mid * mid > x) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        
        return right * right <= x ? right : left;
    }
}

class Solution {
    // 牛顿迭代法，Newton's method，相当于求 k^2 = x 的解，迭代公式即 k_i+1 = (k_i + x / k_i) / 2
    public int mySqrt(int x) {
        long r = x;
        
        while (r * r > x) {
             r = (r + x / r) / 2;
        }
           
        return (int) r;
    }
}