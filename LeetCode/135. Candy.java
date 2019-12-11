// 135. Candy
class Solution {
    public int candy(int[] ratings) {
        int[] nums = new int[ratings.length];
        Arrays.fill(nums, 1);
        for(int i = 0; i < nums.length - 1; i++) {
            if(ratings[i + 1] > ratings[i]) {
                nums[i + 1] = nums[i] + 1;
            }
        }
        for(int i = nums.length - 1; i > 0; i--) {
            if(ratings[i - 1] > ratings[i]) {
                nums[i - 1] = Math.max(nums[i - 1], nums[i] + 1);
            }
        }

        int sum = 0;
        for(int n : nums) {
            sum += n;
        }
        
        return sum;
    }
}