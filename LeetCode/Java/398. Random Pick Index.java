// 398. Random Pick Index
// Reservoir Sampling
// https://zh.wikipedia.org/wiki/%E6%B0%B4%E5%A1%98%E6%8A%BD%E6%A8%A3
class Solution {
    // 空间复杂度为 O(n), 时间复杂度为 O(n)
    public int[] nums;
    
    public Solution(int[] nums) {
        this.nums = nums;
    }
    
    public int pick(int target) {
        Random rand = new Random();
        int result = -1;
        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != target) {
                if(count != 0) {
                    break;
                } else {
                    continue;
                }
            } else {
                count++;
                // [0, count) => the probability of get 0 is 1/count
                // for i-th element, the probability of choosing it is 1/i
                if(rand.nextInt(count) == 0) {
                    result = i;
                }
            }
        }
        return result;
    }
}