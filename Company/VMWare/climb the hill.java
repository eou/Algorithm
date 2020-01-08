// climb the hill!
// modify the array, make it non-increasing or non-decreasing, return minimum cost
// https://codeforces.com/problemset/problem/13/C
// https://iamayushanand.github.io/cp/dp/2016/05/20/Dynamic-Programming-13C-codeforces.html
// https://www.geeksforgeeks.org/minimum-incrementdecrement-to-make-array-non-increasing/
// https://oi.men.ci/haoi2006-sequence/
// https://yhx-12243.github.io/OI-transit/records/lydsy1049%3Blg2501.html#finished
// https://blog.csdn.net/V5ZSQ/article/details/79605704
// https://codeforces.com/blog/entry/47821
// https://codeforces.com/contest/713/problem/C
import java.util.*;

class Solution {
    // O(nlogn)
    public static int minCost(int[] nums) {
        // max-heap
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        // min-heap
        PriorityQueue<Integer> pq2 = new PriorityQueue<>();

        // non-decreasing
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!pq.isEmpty() && pq.peek() > nums[i]) {
                sum += Math.abs(nums[i] - pq.peek());
                pq.remove();
                pq.add(nums[i]);
            }
            pq.add(nums[i]);
        }

        // non-increasing
        int sum2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!pq2.isEmpty() && pq2.peek() < nums[i]) {
                sum2 += Math.abs(nums[i] - pq2.peek());
                pq2.remove();
                pq2.add(nums[i]);
            }
            pq2.add(nums[i]);
        }

        return Math.min(sum, sum2);
    }

    // The element at nth position in the final array formed by the minimal number of moves is at most the maximum number in the initial array.
    // let’s say an array b is a clone of a with the only diffrence that it is sorted and f(i,j) is the minimal number of moves required to make the first i numbers in the initial array non-decreasing with each of them at most bj.
    // base case: it is obvious that f(1,1)= |a1-b1|
    // when i=1: f(1,j)= min(f(1,j-1),|aj-bj|)
    // when j=1: f(i,1)= |ai-b1| +f(i-1,1)
    // when i,j>1:f(i,j)= min(f(i,j-1),f(i-1,j)+|ai-bj|)

    // ff[]为原始序列，f[i]为原始序列的排序。dp[x][y]为把前x个数以j为标杆（0<=j<=y）变成非递减的最优值。
    // 答案就是dp[m-1][m-1]
    // dp[x][y]=min(dp[x][y-1],dp[x-1][y]+abs(ff[x]-f[y]))
    // 优化空间。dp[j]为以第0-j个为标杆的把前i个数变成非递减的最小费用的最优值
    // dp[x]=min(dp[x-1],dp[x]+abs(ff[i]-f[x]));
    // O(n^2)
    public static int minCostDP(int[] nums) {
        int n = nums.length;
        // int[] sortNums = new int[n];
        Integer[] sortNums = new Integer[n];
        for (int i = 0; i < nums.length; i++) {
            sortNums[i] = nums[i];
        }

        Arrays.sort(sortNums);
        // Arrays.sort(sortNums, (a, b) -> b - a);

        // rolling array
        // dp[i] means f(k, i)
        int[] dp = new int[n];
        dp[0] = Math.abs(sortNums[0] - nums[0]);

        // f(1, j)
        for (int i = 1; i < n; i++){
            dp[i] = Math.min(Math.abs(nums[0] - sortNums[i]), dp[i - 1]);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    dp[j] = Math.abs(nums[i] - sortNums[1]) + dp[1];
                } else {
                    dp[j] = Math.min(dp[j - 1], dp[j] + Math.abs(nums[i] - sortNums[j]));
                }
            }
        }

        int result = dp[n - 1];

        // Arrays.sort(sortNums);
        Arrays.sort(sortNums, (a, b) -> b - a);

        // rolling array
        // dp[i] means f(k, i)
        dp[0] = Math.abs(sortNums[0] - nums[0]);

        // f(1, j)
        for (int i = 1; i < n; i++) {
            dp[i] = Math.min(Math.abs(nums[0] - sortNums[i]), dp[i - 1]);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    dp[j] = Math.abs(nums[i] - sortNums[1]) + dp[1];
                } else {
                    dp[j] = Math.min(dp[j - 1], dp[j] + Math.abs(nums[i] - sortNums[j]));
                }
            }
        }

        return Math.min(result, dp[n - 1]);
    }

    public static int minCostDP2(int[] nums) {
        int n = nums.length;
        // int[] sortNums = new int[n];
        Integer[] sortNums = new Integer[n];
        for (int i = 0; i < nums.length; i++) {
            sortNums[i] = nums[i];
        }

        // Arrays.sort(sortNums);
        Arrays.sort(sortNums, (a, b) -> b - a);

        // rolling array
        // dp[i] means f(k, i)
        int[] dp = new int[n];
        dp[0] = Math.abs(sortNums[0] - nums[0]);

        // dp[j]为以第j个为标杆的把前i个数变成非递减的最小费用
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[j] += Math.abs(nums[i] - sortNums[j]);
                dp[j] = j != 0 && dp[j] > dp[j - 1] ? dp[j - 1] : dp[j]; // 更新值，dp[j]是以0-j为标杆中把前i个变成非递减的最小费用最优值
            }
        }

        int result = dp[n - 1];

        Arrays.sort(sortNums);
        // Arrays.sort(sortNums, (a, b) -> b - a);
        dp = new int[n];
        // rolling array
        // dp[i] means f(k, i)
        dp[0] = Math.abs(sortNums[0] - nums[0]);

        // dp[j]为以第j个为标杆的把前i个数变成非递减的最小费用
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[j] += Math.abs(nums[i] - sortNums[j]);
                dp[j] = j != 0 && dp[j] > dp[j - 1] ? dp[j - 1] : dp[j]; // 更新值，dp[j]是以0-j为标杆中把前i个变成非递减的最小费用最优值
            }
        }
           
        return Math.min(result, dp[n - 1]);
    }

    public static void main(String[] args) {
        int[] a = new int[] { 6, 9, 7, 6, 2, 8 };
        // int[] a = new int[] { 3, 2, -1, 2, 11 };
        // int[] a = new int[] { 1,1,1,7,1,1,1 };
        // int[] a = new int[] { 4, 3, 2, 1, 4, 5, 7, 9 };
        // int[] a = new int[] { 9, 8, 6, 1, 0, 4, 3 };
        // 0, 1, 6, 8, 9
        // 

        Random rand = new Random(); 
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 6; i++) {
                a[i] = rand.nextInt(10);
                System.out.print(a[i] + ", ");
            }
            int[] b = a.clone();
            Arrays.sort(b);
            System.out.println();
            for (int i = 0; i < 6; i++) {
                System.out.print(b[i] + ", ");
            }
            System.out.println();
            System.out.println(minCost(a));
            System.out.println(minCostDP(a));
            System.out.println(minCostDP2(a));
            System.out.println("----------");
        }
    }
}