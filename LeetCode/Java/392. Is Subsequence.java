// 392. Is Subsequence
class Solution {
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;
        while(i < s.length() && j < t.length()) {
            if(s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == s.length();
    }
}

class Solution {
    public boolean isSubsequence(String s, String t) {
        List<Integer>[] letter = new List[256];
        for (int i = 0; i < t.length(); i++) {
            if (letter[t.charAt(i)] == null)
                letter[t.charAt(i)] = new ArrayList<>();
            letter[t.charAt(i)].add(i);
        }

        int pre = 0;
        for (int i = 0; i < s.length(); i++) {
            if (letter[s.charAt(i)] == null) {
                return false;
            }

            int j = Collections.binarySearch(letter[s.charAt(i)], pre);
            if (j < 0) {
                j = -j - 1;
            }
            if (j == letter[s.charAt(i)].size()) {
                return false;
            }
            pre = letter[s.charAt(i)].get(j) + 1;
        }
        
        return true;
    }
}