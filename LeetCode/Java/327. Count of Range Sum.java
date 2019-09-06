// 327. Count of Range Sum
// https://leetcode.com/problems/count-of-range-sum/discuss/78006/
// lower <= sum[i] - sum[j] <= upper
// lower + sum[j] <= sum[i] <= upper + sum[j]
// Fenwick Tree
class Solution {
    Map<Long, Integer> map;

    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        if (n == 0)
            return 0;
        // prefix array
        long[] prefixSum = new long[n];
        for (int i = 0; i < n; i++) {
            prefixSum[i] = (i > 0 ? prefixSum[i - 1] : 0) + nums[i];
        }
        long[] sorted = Arrays.copyOf(prefixSum, prefixSum.length);
        Arrays.sort(sorted);
        // binary index tree
        map = new HashMap();
        int idx = 1;
        for (long sum : sorted) {
            if (!map.containsKey(sum))
                map.put(sum, idx++);
        }
        // build tree
        BIT t = new BIT(idx);
        int res = 0;
        // forward or backward
        for (int i = 0; i < n; i++) {
            int l = binarySearch(sorted, prefixSum[i] - upper - 1);
            int r = binarySearch(sorted, prefixSum[i] - lower);
            res += t.sum(r) - t.sum(l);
            if (prefixSum[i] >= lower && prefixSum[i] <= upper) {
                res += 1;
            }
            t.add(map.get(prefixSum[i]), 1);    // add index after calculation, to avoid duplicate
        }
        // for (int i = n - 1; i >= 0; i--) {
        //     t.add(map.get(prefixSum[i]), 1);
        //     long sum = prefixSum[i] - nums[i];
        //     int r = binarySearch(sorted, sum + upper);
        //     int l = binarySearch(sorted, sum + lower - 1);
        //     res += t.sum(r) - t.sum(l);
        // }
        return res;
    }

    // find the last element <= val
    private int binarySearch(long[] arr, long val) {
        int l = 0, r = arr.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2 + 1;
            if (arr[mid] <= val)
                l = mid;
            else
                r = mid - 1;
        }
        if (arr[l] > val)
            return 0;
        return map.get(arr[l]);
    }

    class BIT {
        int[] tree;

        BIT(int n) {
            tree = new int[n];
        }

        public int sum(int i) {
            int res = 0;
            while (i > 0) {
                res += tree[i];
                i -= i & -i;
            }
            return res;
        }

        public void add(int i, int val) {
            while (i < tree.length) {
                tree[i] += val;
                i += i & -i;
            }
        }
    }
}

// Divide and conquer
class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0 || lower > upper)
            return 0;
        return countRangeSumSub(nums, 0, nums.length - 1, lower, upper);
    }

    private int countRangeSumSub(int[] nums, int l, int r, int lower, int upper) {
        if (l == r)
            return nums[l] >= lower && nums[r] <= upper ? 1 : 0; // base case

        int m = l + (r - l) / 2;
        long[] arr = new long[r - m]; // prefix array for the second subarray
        long sum = 0;
        int count = 0;

        for (int i = m + 1; i <= r; i++) {
            sum += nums[i];
            arr[i - (m + 1)] = sum; // compute the prefix array
        }

        Arrays.sort(arr); // sort the prefix array

        sum = 0;
        for (int i = m; i >= l; i--) {
            sum += nums[i];
            count += binarySearch(arr, upper - sum + 0.5) - binarySearch(arr, lower - sum - 0.5);   // aviod duplicate integer
        }

        return countRangeSumSub(nums, l, m, lower, upper) + countRangeSumSub(nums, m + 1, r, lower, upper) + count;
    }

    // binary search function
    private int binarySearch(long[] arr, double val) {
        int l = 0, r = arr.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (arr[m] <= val) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return l;
    }
}

// merge sort
class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0 || lower > upper)
            return 0;

        long[] prefixSum = new long[nums.length + 1];

        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }

        return countRangeSumSub(prefixSum, 0, prefixSum.length - 1, lower, upper);
    }

    private int countRangeSumSub(long[] prefixSum, int l, int r, int lower, int upper) {
        if (l >= r)
            return 0;

        int m = l + (r - l) / 2;

        int count = countRangeSumSub(prefixSum, l, m, lower, upper) + countRangeSumSub(prefixSum, m + 1, r, lower, upper);

        long[] mergedArray = new long[r - l + 1];
        int i = l, j = m + 1, k = m + 1;
        int p = 0, q = m + 1;
        // find lower <= sum[i] - sum[j] <= upper, i < mid and j > mid
        while (i <= m) {
            while (j <= r && prefixSum[j] - prefixSum[i] < lower) {
                j++;
            }
            // prefixSum[j] - prefixSum[i] >= lower
            while (k <= r && prefixSum[k] - prefixSum[i] <= upper) {
                k++;
            }
            // prefixSum[k] - prefixSum[i] <= upper + 1
            count += k - j;

            // merge
            while (q <= r && prefixSum[q] < prefixSum[i]) {
                mergedArray[p++] = prefixSum[q++];
            }
            mergedArray[p++] = prefixSum[i++];
        }

        while (q <= r) {
            mergedArray[p++] = prefixSum[q++];
        }
        System.arraycopy(mergedArray, 0, prefixSum, l, mergedArray.length);

        return count;
    }
}
