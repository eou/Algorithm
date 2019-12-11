// 186. Reverse Words in a String II
class Solution {
    public void reverseWords(char[] str) {
        reverse(str, 0, str.length);
        int start = 0;
        for (int i = 0; i < str.length; ++i) {
            if (str[i] == ' ') {
                reverse(str, start, i);
                start = i + 1;
            }
        }
        reverse(str, start, str.length);    // last part
    }

    public void reverse(char[] str, int start, int end) {
        int i = start, j = end - 1;
        while (i < j) {
            char tmp = str[i];
            str[i] = str[j];
            str[j] = tmp;
            ++i;
            --j;
        }
    }
}