// https://www.geeksforgeeks.org/maximum-weight-transformation-of-a-given-string/
// 1. single pair of different adjacent char -> +p
// 2. single char -> +s
// 3. transformation 0 to 1 / 1 to 0 -> -t
// Weight of string = Weight of total pairs + weight of single characters - Total number of toggles.
// Two consecutive characters are considered as pair only if they are different.
// Weight of a single pair(both character are different) = 4 
// Weight of a single character = 1
import java.util.*;

class Solution {
    public static int stringMaxWeight(String s, int[] weight) {
        int[] dp = new int[s.length() + 1];
        for (int i = 1; i <= s.length(); i++) {
            // can be single
            dp[i] = Math.max(dp[i], dp[i - 1] + weight[1]);
            // can be adjacent different pair
            if (i > 1) {
                if (s.charAt(i - 1) != s.charAt(i - 2)) {
                    dp[i] = Math.max(dp[i], dp[i - 2] + weight[0]);
                } else {
                    // transform
                    dp[i] = Math.max(dp[i], dp[i - 2] + weight[0] - weight[2]);
                }
            }
        }

        return dp[s.length()];
    }

    public static int getMaxRec(String str, int i, int n, int[] lookup) {
        // Base case
        if (i >= n) {
            return 0;
        }

        // If this subproblem is already solved
        if (lookup[i] != -1) {
            return lookup[i];
        }

        // Don't make pair, so
        // weight gained is 1
        int ans = 1 + getMaxRec(str, i + 1, n, lookup);

        // If we can make pair
        if (i + 1 < n) {

            // If elements are dissmilar,
            // weight gained is 4
            if (str.charAt(i) != str.charAt(i + 1)) {
                ans = Math.max(4 + getMaxRec(str, i + 2, n, lookup), ans);
            }

            // if elements are similar so for
            // making a pair we toggle any of
            // them. Since toggle cost is
            // 1 so overall weight gain becomes 3
            else {
                ans = Math.max(3 + getMaxRec(str, i + 2, n, lookup), ans);
            }
        }

        // save and return maximum
        // of above cases
        return lookup[i] = ans;
    }

    // Initializes lookup table
    // and calls getMaxRec()
    public static int getMaxWeight(String str) {
        int n = str.length();

        // Create and initialize lookup table
        int[] lookup = new int[n];
        for (int i = 0; i < n; i++) {
            lookup[i] = -1;
        }

        // Call recursive function
        return getMaxRec(str, 0, str.length(), lookup);
    }

    public static void main(String[] args) {
        // String s = "110";
        // int[] w = {4, 1, 1};
        // System.out.println(stringMaxWeight(s, w));

        // String s1 = "00";
        // System.out.println(stringMaxWeight(s1, w));

        // String s2 = "011";
        // System.out.println(stringMaxWeight(s2, w));

        for (int i = 0; i < 100; i++) {
            Random r = new Random();
            String s = "";
            for (int j = 0; j < 100; j++) {
                if (r.nextBoolean()) {
                    s += "1";
                } else {
                    s += "0";
                }
            }

            int r1 = stringMaxWeight(s, new int[] { 4, 1, 1 });
            int r2 = getMaxWeight(s);
            if (r1 != r2) {
                System.out.println(s);
                System.out.println(r1);
                System.out.println(r2);
                System.out.println();
            }
        }
    }
}