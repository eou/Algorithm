// 28. Implement strStr()
class Solution {
    // naive 版本，时间复杂度 O(m * n)
    public int strStr(String haystack, String needle) {
        if(haystack == null || needle == null || (haystack.length() < needle.length())) {
            return -1;
        }
        if(needle.length() == 0) {
            return 0;
        }
        
        // 注意 i 的上限
        for(int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            int j = 0;
            for(; j < needle.length(); j++) {
                if(haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            
            if(j == needle.length()) {
                return i;
            }
        }
        
        return -1;
    }
}

class Solution {
    // Rabin–Karp Algorithm，时间复杂度近似 O(m + n)
    // 最坏情况下，文本中所有长度为 m 的子串 (一共 n - m + 1 个) 都和模式串匹配，所以算法复杂度为 O((n - m + 1) * m)
    // 然而实际情况下，需要进一步比对的子串个数总是有限的（假设为c个），那么算法的期望匹配时间就变成O((n - m + 1) + c * m) = O(n + m)
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || (haystack.length() < needle.length())) {
            return -1;
        }
        if (needle.length() == 0) {
            return 0;
        }

        int mod = Integer.MAX_VALUE / 31;

        int needleHash = 0;
        // 注意负数取余仍是负数，所以如果 int 溢出后取余会成负数
        for (int i = 0; i < needle.length(); i++) {
            needleHash = (needleHash * 31 + needle.charAt(i) - 'a') % mod;
            if (needleHash < 0) {
                needleHash += mod;
            }
        }

        int power = 1;
        // 由于 mod 特意取值为Integer.MAX_VALUE / 31，这里不会在计算中溢出为负数
        for (int i = 0; i < needle.length(); i++) {
            power = (power * 31) % mod;
        }

        int haystackHash = 0;
        for (int i = 0; i < haystack.length(); i++) {
            haystackHash = (haystackHash * 31 + haystack.charAt(i) - 'a') % mod;

            if (i >= needle.length()) {
                haystackHash = (haystackHash - (haystack.charAt(i - needle.length()) - 'a') * power) % mod;
            }
            
            // 这里也要考虑溢出为负数
            if (haystackHash < 0) {
                haystackHash += mod;
            }

            // 如果长度不足不能比较
            if (i < needle.length() - 1) {
                continue;
            } else {
                if (haystackHash == needleHash) {
                    // 这里需要 double-check ，但是不写也能AC
                    // if (needle.equals(haystackHash.substring(i - needle.length() + 1, i + 1))) {}
                    return i - needle.length() + 1;
                }
            }
        }

        return -1;
    }
}

public class Solution {
    // KMP Algorithm，时间复杂度 O(m + n)
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || (haystack.length() < needle.length())) {
            return -1;
        }
        if (needle.length() == 0) {
            return 0;
        }

        char[] ndl = needle.toCharArray();
        char[] hay = haystack.toCharArray();

        int[] next = makeNext(ndl);

        int k = -1;
        for (int i = 0; i < hay.length; i++) {
            while (k > -1 && ndl[k + 1] != hay[i]) {
                k = next[k];
            }
            if (ndl[k + 1] == hay[i]) {
                k++;
                if (k == ndl.length - 1) {
                    return i - k;
                }
            }
        }

        return -1;
    }

    private int[] makeNext(char[] ndl) {
        int[] next = new int[ndl.length];
        next[0] = -1;
        int k = -1;

        for (int i = 1; i < ndl.length; i++) {
            while (k > -1 && ndl[k + 1] != ndl[i]) {
                k = next[k];
            }
            if (ndl[k + 1] == ndl[i]) {
                k++;
            }
            next[i] = k;
        }

        return next;
    }
}