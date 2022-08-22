// 367. Valid Perfect Square
// Similar with 69. Sqrt(x)
class Solution {
    public boolean isPerfectSquare(int num) {
        int left = 1, right = num;
        // Use left <= right, don't have to test left == right seperately
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (mid * mid == num) {
                return true;
            } else if (mid * mid > num) {
                right = (int)mid - 1;
            } else {
                left = (int)mid + 1;
            }
        }
        
        return false;
    }
}

class Solution {
    public boolean isPerfectSquare(int num) {
        int left = 1, right = num;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if ((double)num / mid == mid) {
                return true;
            } else if ((double)num / mid < mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return (double)num / left == left;
    }
}

class Solution {
    public boolean isPerfectSquare(int num) {
        int left = 1, right = num;
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (mid * mid == num) {
                return true;
            } else if (mid * mid > num) {
                right = (int)mid - 1;
            } else {
                left = (int)mid + 1;
            }
        }
        
        return left * left == num;
    }
}