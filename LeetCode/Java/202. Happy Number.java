// 202. Happy Number
// most important is to detect infinite loop, use Set or Floyd cycle detect algorithm (fast slow pointer)
class Solution {
    public boolean isHappy(int n) {
        int slow, fast;
        slow = fast = n;
        do {
            slow = digitSquareSum(slow);
            fast = digitSquareSum(fast);
            fast = digitSquareSum(fast);
        } while(slow != fast);
        if (slow == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    public int digitSquareSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += (n % 10) * (n % 10);
            n /= 10;
        }
        return sum;
    }
}