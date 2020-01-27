// 75. Sort Colors
class Solution {
    public void sortColors(int[] nums) {
        int last = moveNumber(nums, 0, nums.length - 1, 0);
        moveNumber(nums, last, nums.length - 1, 1);
    }
    
    public int moveNumber(int[] nums, int start, int end, int target) {
        int left = start;
        // find first position which is not this target
        while (left <= end && nums[left] == target) {
            left++;
        }
        
        int right = left + 1;
        while (right <= end) {
            if (nums[right] == target) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }

        // return first position which is not target
        return left;
    }
    
    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

// Dutch National Flag problem solution
class Solution {
    public void sortColors(int[] nums) {
        // for all idx < i : nums[idx < i] = 0
        // j is an index of element under consideration
        int p0 = 0, curr = 0;
        // for all idx > k : nums[idx > k] = 2
        int p2 = nums.length - 1;

        int tmp;
        while (curr <= p2) {
            if (nums[curr] == 0) {
                // swap p0-th and curr-th elements
                // i++ and j++
                tmp = nums[p0];
                nums[p0++] = nums[curr];
                nums[curr++] = tmp;
            } else if (nums[curr] == 2) {
                // swap k-th and curr-th elements
                // p2--
                tmp = nums[curr];
                nums[curr] = nums[p2];
                nums[p2--] = tmp;
            } else {
                curr++;
            }
        }
    }
}