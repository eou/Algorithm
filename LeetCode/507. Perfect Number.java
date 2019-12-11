// 507. Perfect Number
// TLE
class Solution {
    public boolean checkPerfectNumber(int num) {
        int sum = 0;
        for (int i = num; i >= Math.sqrt(num); --i) {
            if (num % i == 0) {
                sum += (num / i + i);
            }
        }

        return sum == 2 * num;
    }
}

class Solution {
    public boolean checkPerfectNumber(int num) {
        if (num <= 1) {
            return false;
        }

        int sum = 0;
        for (int i = num / 2; i >= Math.sqrt(num); --i) {
            if (num % i == 0) {
                sum += (num / i + i);
            }
        }

        return sum == num - 1;
    }
}