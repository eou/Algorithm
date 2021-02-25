// 8. Rotate String
// 39. Recover Rotated Sorted Array
public class Solution {
    /**
     * @param str: An array of char
     * @param offset: An integer
     * @return: nothing
     */
    public void rotateString(char[] str, int offset) {
        if (str.length == 0) {
            return;
        }

        offset %= str.length;
        reverse(str, str.length - offset, str.length - 1);
        reverse(str, 0, str.length - offset - 1);
        reverse(str, 0, str.length - 1);
    }
    
    private void reverse(char[] str, int start, int end) {
        for (int left = start, right = end;  left >= 0 && left < right; left++, right--) {
            char mid = str[left];
            str[left] = str[right];
            str[right] = mid;
        }
    }
}