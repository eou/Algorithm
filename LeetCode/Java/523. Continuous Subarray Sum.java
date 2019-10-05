// 523. Continuous Subarray Sum
// [23,2,6,4,7] => the prefix sum is [23,25,31,35,42] => the remainders are [5,1,1,5,0] => 5 - 5 
class Solution {
    // 用 Prefix sum array 是O(n^2) 复杂度，用 HashMap 是 O(n) 时间复杂度，而空间复杂度是 O(min(n, k))
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int remainder = sum;
            if (k != 0) {
                remainder = sum % k;   // save remainders
            }
            if (map.containsKey(remainder)) {
                if(i - map.get(remainder) > 1) {
                    return true;
                }
            } else {
                map.put(remainder, i);
            }
        }
        
        return false;
    }
}

class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        // 0, nums[0], nums[0] + nums[1], ...
        int[] prefix = new int[nums.length + 1];
        prefix[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            prefix[i] = nums[i - 1] + prefix[i - 1];
        }
        
        for (int i = 0; i < prefix.length; i++) {
            for (int j = i + 2; j < prefix.length; j++) {
                if (k == 0 && (prefix[j] - prefix[i] == 0)) {
                    return true;
                } else if (k != 0 && (prefix[j] - prefix[i]) % k == 0) {
                    return true;
                }
            }
        }
        
        return false;
    }
}