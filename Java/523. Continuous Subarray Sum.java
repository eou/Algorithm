// 523. Continuous Subarray Sum
class Solution {
    // 用 Prefix sum array 是O(n^2) 复杂度，用 HashMap 是 O(n) 时间复杂度，而空间复杂度是 O(min(n, k))
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        // 当有 sum % k = 0时可以返回true; 设置为 -1 是为了防止第一个元素就是 k 的倍数: 0 - (-1) = 1不会返回true，而从第二个元素开始，sum 如果是 k 的倍数 1 - (-1) > 1就可以返回true
        map.put(0, -1);
        
        int sum = 0;
        for (int i = 0;i < nums.length; i++) {
            sum += nums[i];
            if (k != 0) {
                sum %= k; 
            }
            if(map.containsKey(sum)) {
                if(i - map.get(sum) > 1) {
                    return true;
                }
            } else {
                map.put(sum, i);
            }
        }
        
        return false;
    }
}
