// 698. Partition to K Equal Sum Subsets
class Solution {
    // 无优化的时间复杂度为 O(k^n)，优化后降到 O(k^(n - k) * k!)
    // Fill the k length subsets array need O(k!)，for each kind of subset array, there are O(k^(n - k)) possibilities
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }

        int target = sum / k;
        Arrays.sort(nums);
        if(nums[0] > target || nums[nums.length - 1] > target) {
            return false;
        }

        int i = nums.length - 1;
        for(; i >= 0; i--) {
            if(nums[i] != target) {
                break;
            } else {
                k--;
            }
        }

        return dfs(new int[k], target, nums, i);
    }

    private boolean dfs(int[] subsets, int target, int[] nums, int start) {
        if(start < 0) {
            return true;
        }

        for(int i = 0; i < subsets.length; i++) {
            if(subsets[i] + nums[start] <= target) {
                subsets[i] += nums[start];
                if(dfs(subsets, target, nums, start - 1)) {
                    return true;
                }
                subsets[i] -= nums[start];
            }
            // optimization, avoid duplicate work
            if(subsets[i] == 0) {
                break;
            }
        }
        
        return false;
    }
}