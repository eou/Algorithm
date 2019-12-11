// 493. Reverse Pairs
// Fenwick tree, similar with 315
class Solution {
    class FenwickTree {
        private int[] sums;

        public FenwickTree(int n) {
            sums = new int[n];
        }

        private int lowbit(int i) {
            return i & (-i);
        }

        public void update(int i, int delta) {
            while (i < sums.length) {
                sums[i] += delta;
                i += lowbit(i);
            }
        }

        public int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += sums[i];
                i -= lowbit(i);
            }
            return sum;
        }
    }

    public int reversePairs(int[] nums) {
        int result = 0;
        long[] nums2 = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            nums2[i] = (long) nums[i];
        }
        Arrays.sort(nums2);
        Map<Long, Integer> map = new HashMap<>();
        int pos = 1;
        for (int i = 0; i < nums.length; i++) {
            map.put(nums2[i], map.getOrDefault(nums2[i], pos++));
        }

        FenwickTree tree = new FenwickTree(nums.length + 1); // start from 1
        for (int i = nums.length - 1; i >= 0; i--) {
            int bound = lower_bound(nums2, (long) nums[i]);
            if (bound != -1) {
                result += tree.query(map.get(nums2[bound]));
            }
            tree.update(map.get((long) nums[i]), 1);
        }
        return result;
    }

    public int lower_bound(long[] nums, long num) {
        int left = 0, right = nums.length - 1;
        while (left < right - 1) {
            int mid = left + (right - left) / 2;
            if (nums[mid] * 2 < num) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        if (nums[right] * 2 < num) {
            return right;
        } else if (nums[left] * 2 < num) {
            return left;
        } else {
            return -1;  // no lower_bound
        }

    }
}