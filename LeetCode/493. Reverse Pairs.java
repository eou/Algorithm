// 493. Reverse Pairs
// Similar with 315, 327
// Merge sort
class Solution {
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
 
        return mergeSort(nums, 0, nums.length - 1);
    }

    public int mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = (left + right) / 2;
        int n1 = mergeSort(arr, left, mid);
        int n2 = mergeSort(arr, mid + 1, right);
        int res = n1 + n2;

        // Count pairs
        int cur = left, nxt = mid + 1;
        while (cur <= mid) {
            while (nxt <= right && (long)arr[cur] > 2 * (long)arr[nxt]) {
                nxt++;
            }
            res += nxt - mid - 1; // nxt - mid not nxt - cur
            cur++;
        }

        // Merge
        int[] sorted = new int[right - left + 1]; // we only merge [left, right] here
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

// Fenwick tree
class Solution {
    public int reversePairs(int[] nums) {
        // Discretization
        Set<Long> set = new TreeSet<>(); // sorted and deduplicate !!!
        for (int i : nums) {
            set.add((long) i);
            set.add((long) i * 2);
        }

        Map<Long, Integer> map = new HashMap<>();
        int idx = 1;
        for (Long i : set) {
            map.put(i, idx);
            idx++;
        }

        int res = 0;
        BIT bit = new BIT(map.size());
        for (int i = 0; i < nums.length; i++) {
            int left = map.get((long) nums[i] * 2), right = map.size();
            res += bit.query(right) - bit.query(left);
            bit.update(map.get((long) nums[i]), 1);
        }

        return res;
    }

    private class BIT {
        private int[] nums;

        public BIT(int n) {
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