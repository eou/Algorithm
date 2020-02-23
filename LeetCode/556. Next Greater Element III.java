// 556. Next Greater Element III
// next permutation
class Solution {
    public int nextGreaterElement(int n) {
        String str = Integer.toString(n);
        // for future modification
        StringBuilder s = new StringBuilder(str);
        
        // from right to left, lower numbers to higher numbers
        // skip first non-decresasing sequence, ...2,4,4,3,2,1
        int i = s.length() - 2;
        while (i >= 0 && s.charAt(i + 1) <= s.charAt(i)) {
            i--;
        }
        
        if (i == -1) {
            return -1;
        }
        
        int j = i + 1;
        while (j < s.length() && s.charAt(j) > s.charAt(i)) {
            j++;
        }
        
        // swap str[i] and str[j - 1]
        char tmp = s.charAt(i);
        s.setCharAt(i, s.charAt(j - 1));
        s.setCharAt(j - 1, tmp);
        
        // make the sequence after i non-decreasing
        // reverse, ...3,4,4,2,1,2
        for (int left = i + 1, right = str.length() - 1; left < right; left++, right--) {
            tmp = s.charAt(left);
            s.setCharAt(left, s.charAt(right));
            s.setCharAt(right, tmp);
        }
        
        long res = Long.valueOf(s.toString());
        return res > Integer.MAX_VALUE ? -1 : (int)res;
    }
}