// 1134. Armstrong Number
class Solution {
    public boolean isArmstrong(int N) {
        int sum = 0, num = N, k = 0;
        while (num > 0) {
            k++;
            num /= 10;
        }
        num = N;
        while (num > 0) {
            sum += Math.pow(num % 10, k);
            num /= 10;
        }
        
        return sum == N;
    }
}

// string trick
class Solution {
    public boolean isArmstrong(int N) {
        String str = String.valueOf(N);
        int k = str.length();
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += (int) Math.pow(c - '0', k);
        }
        return sum == N;
    }
}