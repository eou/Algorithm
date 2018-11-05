// 69. Sqrt(x)
class Solution {
    // binary search，如果结果不是整数就不能用
    public int mySqrt(int x) {
        if(x == 0) {
            return 0;
        }
        
        int left = 1, right = Integer.MAX_VALUE;
        while(true) {
            int mid = left + (right - left) / 2;
            if(mid > x / mid) {
                right = mid - 1;
            } else {
                if(mid + 1 > x / (mid + 1)) {
                    return mid;
                }
                left = mid + 1;
            }
        }
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