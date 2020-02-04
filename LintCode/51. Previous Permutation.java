// 51. Previous Permutation
class Solution {
    public List<Integer> previousPermuation(List<Integer> nums) {
        // from LeetCode 31. Next Permutation, reverse its process
        // find last non-decreasing subarray, reverse it, 7,3,1,2,4,5 => 7,3,5,4,2,1
        // get the number before this subarray, 7,3,5,4,2,1 => 3
        // swap this number with a number smaller than it in the reversed subarray, 7,2,5,4,3,1
        int target = nums.size() - 2;
        for (; target >= 0 && nums.get(target) <= nums.get(target + 1); target--) // target >= 0 should be the first
                                                                                  // check condition !!!
            ;
        
        // reverse
        for (int i = target + 1, j = nums.size() - 1; i < j; i++, j--) {
            swap(nums, i, j);
        }
        
        if (target >= 0) {
            int smaller = nums.size() - 1;
            // choose the largest number in the reversed array which is smaller than target number
            for(; nums.get(smaller) < nums.get(target) && smaller > target; smaller--)
                ;
            // smaller + 1 won't exceed the border of the array since if the smallest number of reversed array is larger than target number
            // the target number should belong to the reversed array before the subarray is reversed because this subarray is non-decreasing at the beginning
            swap(nums, target, smaller + 1);
        }
        
        return nums;
    }
    
    public void swap(List<Integer> nums, int i, int j) {
        int tmp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, tmp);
    }
}