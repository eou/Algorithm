// 738. Monotone Increasing Digits
class Solution {
    public int monotoneIncreasingDigits(int N) {
        String str = Integer.toString(N);
        char[] arr = str.toCharArray();
        
        int j = arr.length;
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i] >= arr[i - 1]) {
                continue;
            }
            
            arr[i - 1]--;
            j = i;
        }
        
        for (int i = j; i < arr.length; i++) {
            arr[i] = '9';
        }
        
        return Integer.parseInt(new String(arr));
    }
}