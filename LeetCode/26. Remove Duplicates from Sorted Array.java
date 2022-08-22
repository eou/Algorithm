// 26. Remove Duplicates from Sorted Array
// Similar with 27. Remove Element
class Solution {
    public int removeDuplicates(int[] nums) {
        int newPos = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[newPos++] = nums[i];
            }
        }
        
        return newPos;
    }
}

class Solution {
    public int removeDuplicates(int[] nums) {
        int duplicate = 0;
        for(int i = 0; i < nums.length - 1; i++) {
            if(nums[i + 1] == nums[i]) {
                duplicate++;
            } else {
                if(duplicate > 0) {
                    nums[i + 1 - duplicate] = nums[i + 1];
                }
            }
        }     
        return nums.length - duplicate;
    }
}

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0)
            return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
}
