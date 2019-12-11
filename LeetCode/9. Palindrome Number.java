// 9. Palindrome Number
class Solution {
    // O(log_10(n))
    public boolean isPalindrome(int x) {
        // good check
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

// compare head and tail using double-ended queue
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

// follow up: Coud you solve it without converting the integer to a string?
class Solution {
    public boolean isPalindrome(int x) {
        String str = Integer.toString(x);
        return isPalindrome(str);
    }

    public boolean isPalindrome(String str) {
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
