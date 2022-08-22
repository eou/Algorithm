// 27. Remove Element
class Solution {
    public int removeElement(int[] nums, int val) {
        int newPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[newPos++] = nums[i];
            }
        }
        return newPos;
    }
}

class Solution {
    // In-place swap
    public int removeElement(int[] nums, int val) {
        int target = 0, i = 0, j = 0;
        while (i < nums.length) {
            while (i < nums.length && nums[i] == val) {
                i++;
                target++;
            }
            j = i;
            while (j < nums.length && nums[j] != val) {
                j++;
            }
            // overwrite
            for (int k = i; k < j; k++) {
                nums[k - target] = nums[k];
            }
            i = j;
        }
        
        return nums.length - target;
    }
}