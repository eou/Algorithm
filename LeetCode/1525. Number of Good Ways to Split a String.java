// 1525. Number of Good Ways to Split a String
// !!! 2021 Google SWE New Grad Online Assessment
// int[26] is faster than HashMap, memory use are similar
class Solution {
    public int numSplits(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int leftDistinct = 0, rightDistinct = 0;
        int[] leftChar = new int[26];
        int[] rightChar = new int[26];
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            rightChar[c - 'a']++;
            if (rightChar[c - 'a'] == 1) {
                rightDistinct++;
            }
        }
        
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            leftChar[c - 'a']++;
            if (leftChar[c - 'a'] == 1) {
                leftDistinct++;
            }
            rightChar[c - 'a']--;
            if (rightChar[c - 'a'] == 0) {
                rightDistinct--;
            }
            
            res += leftDistinct == rightDistinct ? 1 : 0;
        }
        
        return res;
    }
}


// Only use one array, but the actual runtime doesn't increase too much
// !!! for left side, we can only use an integer to represent the number of distinct characters
// !!! for right side, since we need to remove the frequency of some characters, we need one array
class Solution {
    public int numSplits(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int leftDistinct = 0, rightDistinct = 0;
        int[] cnt = new int[26];

        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            rightDistinct |= 1 << idx;
            cnt[idx]++;
        }

        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            cnt[idx]--;

            if (cnt[idx] == 0) {
                rightDistinct ^= 1 << idx;
            }

            leftDistinct |= 1 << idx;
            if (numberOfOne(leftDistinct) == numberOfOne(rightDistinct)) {
                res++;
            }
        }
        return res;
    }

    private int numberOfOne(int x) {
        int res = 0;
        while (x != 0) {
            res++;
            x = x & (x - 1);
        }
        return res;
    }

    private final int[] oneNumberTable = {
        0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3,
        4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
        5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5,
        6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2,
        3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4,
        5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4,
        5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6,
        7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8 };

    private int numberOfOne(int n) {
        int count = 0;
        for (int i = 0; i < 32; i += 8) {
            count += oneNumberTable[(n >> i) & 0xFF];
        }
        return count;
    }

    // fastest way to get number of 1
    private int numberOfOne(int n) {
        n = n - ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n + (n >>> 4)) & 0x0F0F0F0F;
        return (n * 0x01010101) >>> 24;
    }
}

/*
    Test Cases:
    aa, 1
    aab, 1
    ab, 1
    aca, 0
    bababab, 4
    abb, 1
    aaabbb, 1
    acaac, 2
    abcc, 0
    bbccbb, 1
    abbabb, 2
    aaa, 2,
    adddb, 2
    zzz, 2
    xyz, 0
*/