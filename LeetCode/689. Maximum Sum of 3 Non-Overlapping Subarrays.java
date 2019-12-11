// 689. Maximum Sum of 3 Non-Overlapping Subarrays
// 思路是渐进的：首先是brute force 时间复杂度为 O(n^3)
// 其次改进brute force，确定中间子数组后遍历左右两个数组，时间复杂度 O(n^2)
// 最后是多用两个数组保存数组每个位置之前和之后，k 个元素子数组之和最大的下标
class Solution {
    // 时间复杂度 O(n^2)
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int results[] = new int[3];
        int sum[] = new int[n + 1];

        sum[0] = 0;
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        // [0, 1, ..., k - 1, k, ..., n - 2k, ..., n - 1]
        // length: k, ..., 2k
        int max = Integer.MIN_VALUE;
        // i is the start index of second interval
        for (int i = k; i < n - 2 * k + 1; i++) {
            int left = 0;
            int right = 0;
            int total = 0;

            // l is the start index of first interval
            for (int l = 0; l < i - k + 1; l++) {
                if (sum[l + k] - sum[l] > total) {
                    left = l;
                    total = sum[l + k] - sum[l];
                }
            }

            total = 0;
            // r is the start index of third interval
            for (int r = i + k; r < n - k + 1; r++) {
                if (sum[r + k] - sum[r] > total) {
                    right = r;
                    total = sum[r + k] - sum[r];
                }
            }

            total = (sum[left + k] - sum[left]) + (sum[i + k] - sum[i]) + (sum[right + k] - sum[right]);
            if (total > max) {
                max = total;
                results[0] = left;
                results[1] = i;
                results[2] = right;
            }
        }

        return results;
    }
}

class Solution {
    // 时间复杂度 O(n)，空间复杂度 O(n)
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int results[] = new int[3];
        int sum[] = new int[n + 1];
        int leftMax[] = new int[n];
        int rightMax[] = new int[n];
        
        // maintain prefix sum array
        sum[0] = 0;
        for(int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        
        // store the starting index for the left-interval whose elements' sum is the maximum
        int total = sum[k] - sum[0];
        for(int i = k; i < n; i++) {
            if(sum[i + 1] - sum[i + 1 - k] > total) {
                leftMax[i] = i + 1 - k;
                total = sum[i + 1] - sum[i + 1 - k];
            } else {
                leftMax[i] = leftMax[i - 1];
            }
        }
        
        rightMax[n - k] = n - k;
        total = sum[n] - sum[n - k];
        for(int i = n - k - 1; i >= 0; i--) {
            // >= means always select left most one
            if(sum[i + k] - sum[i] >= total) {
                rightMax[i] = i;
                total = sum[i + k] - sum[i];
            } else {
                rightMax[i] = rightMax[i + 1];
            }
        }
        
        int max = Integer.MIN_VALUE;
        for(int i = k; i <= n - 2 * k; i++) {
            int left = leftMax[i - 1], right = rightMax[i + k];
            total = (sum[i + k] - sum[i]) + (sum[left + k] - sum[left]) + (sum[right + k] - sum[right]);
            if(total > max) {
                max = total;
                results[0] = left;
                results[1] = i;
                results[2] = right;
            }
        }
        
        return results;
    }
}
