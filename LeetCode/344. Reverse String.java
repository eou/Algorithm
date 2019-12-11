// 344. Reverse String
class Solution {
    public String reverseString(String s) {
        StringBuilder result = new StringBuilder();
        for(int i = s.length() - 1; i >= 0; i--) {
            result.append(s.charAt(i));
        }
        return result.toString();
    }
}

class Solution {
    // in-place
    public String reverseString(String s) {
        char[] word = s.toCharArray();
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            char c = word[i];
            word[i] = word[j];
            word[j] = c;
        }
        return new String(word);
    }
}