// 392. Is Subsequence
class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (t == null || t.length() == 0 || s.length() > t.length()) {
            return false;
        }

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

// follow up
// If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence.
class Solution {
    public boolean isSubsequence(String s, String t) {
        // an Arraylist array
        List<Integer>[] letter = new List[256];
        for (int i = 0; i < t.length(); i++) {
            if (letter[t.charAt(i)] == null) {
                letter[t.charAt(i)] = new ArrayList<>();
            }
            letter[t.charAt(i)].add(i); // store index
        }

        int last = 0;
        for (int i = 0; i < s.length(); i++) {
            if (letter[s.charAt(i)] == null) {
                return false;
            }

            int j = binarySearch(letter[s.charAt(i)], last);
            if (j == -1) {
                return false;
            }
            last = letter[s.charAt(i)].get(j) + 1; // next position in T
        }

        return true;
    }

    public int binarySearch(List<Integer> list, int num) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < num) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        if (list.get(left) >= num) {
            return left;
        } else {
            return -1;
        }
    }
}

class Solution {
    public boolean isSubsequence(String s, String t) {
        // an Arraylist array
        List<Integer>[] letter = new List[256];
        for (int i = 0; i < t.length(); i++) {
            if (letter[t.charAt(i)] == null) {
                letter[t.charAt(i)] = new ArrayList<>();
            } 
            letter[t.charAt(i)].add(i);     // store index
        }

        int last = 0;
        for (int i = 0; i < s.length(); i++) {
            if (letter[s.charAt(i)] == null) {
                return false;
            }

            int j = Collections.binarySearch(letter[s.charAt(i)], last);
            if (j < 0) {
                j = -j - 1;
            }
            if (j == letter[s.charAt(i)].size()) {
                return false;
            }
            last = letter[s.charAt(i)].get(j) + 1;
        }
        
        return true;
    }
}