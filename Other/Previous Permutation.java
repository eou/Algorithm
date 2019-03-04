// 51. Previous Permutation
// https://www.lintcode.com/problem/previous-permutation/description
public class Solution {
    public List<Integer> previousPermuation(List<Integer> nums) {
        // [1,4,2,3,6] => [1,3,2,4,6] => [1,3,6,4,2]
        int i  = nums.size() - 1;
        for (; i > 0 && nums.get(i) >= nums.get(i - 1); --i);
        --i;
        
        if (i >= 0) {
            int smaller = i;
            for (int j = i; j < nums.size(); ++j) {
                if (nums.get(j) < nums.get(i)) {
                    smaller = j;
                }
            }
            swap(nums, i, smaller);
        }
        
        reverse(nums, i + 1);
        
        return nums;
    }
    
    public void swap (List<Integer> nums, int i, int j) {
        int tmp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, tmp);
    }
    
    public void reverse (List<Integer> nums, int start) {
        for (int i = start, j = nums.size() - 1; i < j; ++i, --j) {
            swap(nums, i, j);
        }
    }
}