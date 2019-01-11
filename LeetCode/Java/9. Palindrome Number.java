// 9. Palindrome Number
class Solution {
    // 时间复杂度为 O(logn)
    public boolean isPalindrome(int x) {
        // 这个特殊判断挺巧妙
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while(x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        
        return x == revertedNumber || x == revertedNumber / 10;
    }
}

class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0) {
            return false;
        }
        
        Deque<Integer> queue = new ArrayDeque<>();
        while(x > 0) {
            queue.add(x % 10);
            x /= 10;
        }
        
        while(queue.size() > 1) {
            int a = queue.pollFirst();
            int b = queue.pollLast();
            if(a != b) {
                return false;
            }
        }
        return true;
    }
}