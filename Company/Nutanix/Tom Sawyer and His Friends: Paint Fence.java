// https://leetcode.com/discuss/interview-question/403548/Nutanix-or-OA-2019-or-Paint-Fence
// https://www.geeksforgeeks.org/ways-to-divide-a-binary-array-into-sub-arrays-such-that-each-sub-array-contains-exactly-one-1/

// Just count 0s between 1s and increment it.
// Then multiply all counts it willl given number of ways
// for eg:- arr = 0 0 1 0 0 0 1 0 0 0 0 1 0
// count 0s between 1's will be cnt0[] = 3 4
// increment them by 1 cnt0[] = 4 5
// result will be 4*5 ,i.e,20
import java.util.*;

class Solution {
    public static int paintFence(int n, int[] arr) {
        int[] dp = new int[arr.length + 1];
        // if arr[i] = 1, itself can be new piece, or it can combine with previous 0
        // if arr[i] = 0, it must be divided with previous piece
        // start from 1, avoid index out of bounds for 0
        dp[0] = 1;
        int numOne = 0;
        for (int i = 1; i <= arr.length; i++) {
            if (arr[i - 1] == 1) {
                numOne++;
                // itself can be one piece
                dp[i] += dp[i - 1];
                // combined with previous 0, must have previous 1
                if (numOne > 1) {
                    for (int j = i - 2; j >= 0; j--) {
                        if (arr[j] == 0) {
                            dp[i] += dp[j];
                        } else {
                            break;
                        }
                    }
                }
            } else {
                dp[i] = dp[i - 1];
            }
        }

        // for (int i : dp) {
        // System.out.print(i + ", ");
        // }
        // System.out.println();
        return numOne == 0 ? 0 : dp[arr.length];
    }

    public static int paintFence2(int n, int[] arr) {
        int prevOneIdx = -1;
        int numWays = 0;
        for (int i = 0; i < n; ++i) {
            if (arr[i] == 1) {
                if (prevOneIdx != -1) {
                    int numOnes = i - prevOneIdx;
                    numWays *= numOnes;
                } else {
                    // there is at least one in the input
                    numWays = 1;
                }
                prevOneIdx = i;
            }
        }
        return numWays;
    }
    
    public static int countWays(int n, int arr[]) {
        int pos[] = new int[n];
        int p = 0, i;

        // for loop for saving the
        // positions of all 1s
        for (i = 0; i < n; i++) {
            if (arr[i] == 1) {
                pos[p] = i + 1;
                p++;
            }
        }

        // If array contains only 0s
        if (p == 0)
            return 0;

        int ways = 1;
        for (i = 0; i < p - 1; i++) {
            ways *= pos[i + 1] - pos[i];
        }

        // Return the total ways
        return ways;
    }

    public static void main(String[] args) {
        // int[] arr = { 0, 0, 1, 1, 1, 0, 1, 1, 1, 0};
        // System.out.println(paintFence(10, arr));

        for (int i = 0; i < 10; i++) {
            int[] arr2 = new int[100];
            for (int j = 0; j < 100; j++) {
                Random r = new Random();
                if (r.nextBoolean()) {
                    arr2[j] = 0;
                } else {
                    arr2[j] = 1;
                }
            }

            // for (int j : arr2) {
            // System.out.print(j + ", ");
            // }
            System.out.println();
            System.out.println(paintFence(100, arr2));
            System.out.println(countWays(100, arr2));
            System.out.println();
            // if (paintFence(100, arr2) != countWays(100, arr2)) {
            // for (int j : arr2) {
            // System.out.print(j + ", ");
            // }
            // System.out.println();
            // System.out.println(paintFence(100, arr2));
            // System.out.println(countWays(100, arr2));
            // System.out.println();
            // }
        }
    }
}