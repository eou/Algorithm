// 702 · Concatenated String with Uncommon Characters of Two Strings
public class Solution {
    public String concatenetedString(String s1, String s2) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            if (s2.indexOf(c) == -1) {
                res.append(c);
            }
        }

        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            if (s1.indexOf(c) == -1) {
                res.append(c);
            }
        }

        return res.toString();
    }
}