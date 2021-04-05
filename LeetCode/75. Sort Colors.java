// 75. Sort Colors
class Solution {
    public void sortColors(int[] nums) {
        int start = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, i, start++);
            }
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                swap(nums, i, start++);
            }
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 2) {
                swap(nums, i, start++);
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

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
        // red: 0; white: 1; blue: 2;
        int i = 0, red = 0, blue = nums.length - 1;
        while (i <= blue) {
            if (nums[i] == 0) {
                swap(nums, i, red++);
                i++;
            } else if (nums[i] == 2) {
                swap(nums, i, blue--);
            } else {
                i++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}