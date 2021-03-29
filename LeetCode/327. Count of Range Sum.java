// 327. Count of Range Sum
// https://leetcode.com/problems/count-of-range-sum/discuss/78006/
// !!! https://leetcode-cn.com/problems/count-of-range-sum/solution/qu-jian-he-de-ge-shu-by-leetcode-solution/
// !!! https://leetcode-cn.com/problems/count-of-range-sum/solution/xian-ren-zhi-lu-ru-he-xue-xi-ke-yi-jie-jue-ben-ti-/
// lower <= sum[i] - sum[j] <= upper
// lower + sum[j] <= sum[i] <= upper + sum[j]
// Fenwick Tree, O(nlogn)
class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        // [-2,5,-1], -2, 2
        // prefix: [0,-2,3,2]
        long[] prefix = new long[nums.length + 1];
        for (int i = 1; i < prefix.length; i++) {
            prefix[i] = (long) nums[i - 1] + prefix[i - 1];
        }

        // We need to know in prefix[0..j], how many prefix[i..j] lie in [lower, upper]
        // which means lower <= prefix[j] - prefix[i] <= upper
        // prefix[j] - lower >= prefix[i] && prefix[j] - upper <= prefix[i]
        // thus we need to find how many prefix[i] lie in [prefix[j] - upper, prefix[j]
        // - lower]

        // prefix: [0,-2,3,2] => (-2, 0, 2, -4, -2, 0, 1, 3, 5, 0, 2, 4)
        // set: (-4, -2, 0, 1, 2, 3, 4, 5)
        // Discretization
        Set<Long> set = new TreeSet<>(); // sorted and deduplicate !!!
        for (long i : prefix) {
            set.add(i);
            set.add(i - lower);
            set.add(i - upper);
        }

        // map: <-4,1>, <-2,2>, <0,3>, <1,4>, <2,5>, <3,6>, <4,7>, <5,8>
        Map<Long, Integer> map = new HashMap<>();
        int idx = 1;
        for (Long i : set) {
            map.put(i, idx);
            idx++;
        }

        // -4, -2, 0, 1, 2, 3, 4, 5
        // 1, 2, 3, 4, 5, 6, 7, 8

        // prefix: [0,-2,3,2]
        // 0 => (-2, 2) => index (2, 5) => 0, => [0,0,1,0,0,0,0,0]
        // -2 => (-4, 0) => index (1, 3) => 1, => [0,1,1,0,0,0,0,0]
        // 3 => (1, 5) => index (4, 8) => 0, => [0,1,1,0,0,1,0,0]
        // 2 => (0, 4) => index (3, 7) => 2, => [0,1,1,0,1,1,0,0]
        int res = 0;
        BIT bit = new BIT(map.size());
        for (int i = 0; i < prefix.length; i++) {
            int left = map.get(prefix[i] - upper), right = map.get(prefix[i] - lower);
            res += bit.query(right) - bit.query(left - 1); // [left, right]
            bit.update(map.get(prefix[i]), 1);
        }

        return res;
    }

    private class BIT {
        // don't have to be long since we only store rank/order which won't bigger than
        // nums.length + 1
        private int[] nums;

        public BIT(int n) {
            // rank starts from 1 and right + 1
            nums = new int[n + 2];
        }

        private int lowbit(int x) {
            return x & (-x);
        }

        private int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += nums[i];
                i -= lowbit(i);
            }
            return sum;
        }

        private void update(int i, int diff) {
            while (i < nums.length) {
                nums[i] += diff;
                i += lowbit(i);
            }
        }
    }
}

// Merge sort
class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        long s = 0;
        long[] prefix = new long[nums.length + 1];
        for (int i = 1; i < prefix.length; ++i) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }

        return mergeSort(prefix, lower, upper, 0, prefix.length - 1);
    }

    public int mergeSort(long[] arr, int lower, int upper, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = (left + right) / 2;
        int n1 = mergeSort(arr, lower, upper, left, mid);
        int n2 = mergeSort(arr, lower, upper, mid + 1, right);
        int res = n1 + n2;

        // Count pairs between left side and right side, prefix[i] - prefix[j] ∈ [lower,upper]
        // Actually finding a range, in which numbers are in [lower, upper]
        int cur = left, start = mid + 1, end = mid + 1;
        while (cur <= mid) {
            // for arr[cur], find a range (start, end]
            while (start <= right && arr[start] - arr[cur] < lower) {
                start++;
            }
            while (end <= right && arr[end] - arr[cur] <= upper) {
                end++;
            }
            res += end - start;
            cur++;
        }

        // Merge
        long[] sorted = new long[right - left + 1]; // we only merge [left, right] here
        int i = 0, l = left, r = mid + 1;
        while (l <= mid && r <= right) {
            if (arr[l] < arr[r]) {
                sorted[i] = arr[l];
                l++;
            } else {
                sorted[i] = arr[r];
                r++;
            }
            i++;
        }

        while (l <= mid) {
            sorted[i] = arr[l];
            l++;
            i++;
        }

        while (r <= right) {
            sorted[i] = arr[r];
            r++;
            i++;
        }

        System.arraycopy(sorted, 0, arr, left, sorted.length);

        return res;
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