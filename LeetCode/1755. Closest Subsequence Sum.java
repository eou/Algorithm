// 1755. Closest Subsequence Sum
class Solution {
    public int minAbsDifference(int[] nums, int goal) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int[] arr = nums;
        int n = nums.length;

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        // Generate all possible subset sums from first half the array
        dfs(0, n / 2, 0, arr, left);
        // Generate all possible subset sums from the second half of the array
        dfs(n / 2, n, 0, arr, right);

        Collections.sort(left);

        int res = Integer.MAX_VALUE;
        for (int sum : right) {
            int diff = goal - sum; // How far off are we from the desired goal?

            // All subset sums from first half are too big => Choose the smallest
            if (left.get(0) > diff) {
                res = (int) (Math.min(res, Math.abs((left.get(0) + sum) - goal)));
                continue;
            }

            // All subset sums from first half are too small => Choose the largest
            if (left.get(left.size() - 1) < diff) {
                res = (int) (Math.min(res, Math.abs((left.get(left.size() - 1) + sum) - goal)));
                continue;
            }

            int pos = binarySearch(left, diff);
            if (left.get(pos) == diff) {
                return 0;
            } else {
                res = Math.min(res, Math.abs(sum + left.get(pos) - goal));
            }
        }

        return res;
    }

    // Subset
    private void dfs(int start, int end, int sum, int[] arr, List<Integer> subsetSum) {
        if (start == end) {
            subsetSum.add(sum);
            return;
        }

        // selected
        dfs(start + 1, end, sum + arr[start], arr, subsetSum);
        // not selected
        dfs(start + 1, end, sum, arr, subsetSum);

    }

    private int binarySearch(List<Integer> nums, int target) {
        int left = 0, right = nums.size() - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < target) {
                left = mid;
            } else if (nums.get(mid) > target) {
                right = mid;
            } else {
                return mid;
            }
        }

        int leftDiff = Math.abs(nums.get(left) - target);
        int rightDiff = Math.abs(nums.get(right) - target);
        if (leftDiff < rightDiff) {
            return left;
        } else {
            return right;
        }
    }
}