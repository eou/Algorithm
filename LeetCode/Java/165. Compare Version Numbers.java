// 165. Compare Version Numbers
class Solution {
    public int compareVersion(String version1, String version2) {
        int num1 = 0, num2 = 0;
        int len1 = version1.length(), len2 = version2.length();
        int i = 0, j = 0;
        while (i < len1 || j < len2) {
            num1 = 0;
            num2 = 0;
            while (i < len1 && version1.charAt(i) != '.') {
                num1 = num1 * 10 + version1.charAt(i) - '0';
                i++;
            }
            while (j < len2 && version2.charAt(j) != '.') {
                num2 = num2 * 10 + version2.charAt(j) - '0';
                j++;
            }

            if (num1 > num2) {
                return 1;
            } else if (num1 < num2) {
                return -1;
            } else {
                i++;
                j++;
            }
        }

        return 0;
    }
}

class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int len = Math.max(v1.length, v2.length);
        for (int i = 0; i < len; ++i) {
            Integer num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            Integer num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            int cmp = num1.compareTo(num2);
            if (cmp != 0) {
                return cmp;
            }
        }

        return 0;
    }
}