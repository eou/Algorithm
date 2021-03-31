// 1509. Minimum Difference Between Largest and Smallest Value in Three Moves
// !!! 2021 Google SWE New Grad Online Assessment
// Only consider 3 biggest numbers and 3 smallest numbers
// O(nlogn) time complexity, O(1) space
class Solution {
    public int minDifference(int[] nums) {
        if (nums == null || nums.length < 4) {
            return 0;
        }
        
        Arrays.sort(nums);
        
        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= 3; i++) {
            res = Math.min(res, nums[nums.length - 1 - (3 - i)] - nums[i]);
        }
        return res;
    }
}

// O(n) time complexity, O(1) space
class Solution {
    public int minDifference(int[] nums) {
        if (nums.length <= 4) return 0;
        
        int[] maxArray = new int[4];
        int[] minArray = new int[4];
        
        Arrays.fill(maxArray, Integer.MIN_VALUE);
        Arrays.fill(minArray, Integer.MAX_VALUE);
        
        // O(4*n)
        for (int num : nums) {
            int currNum = num;
            // find the correct position for current number in maxArray
            for (int i = 0; i < 4; i++) {
                if (currNum >= maxArray[i]) {
                    currNum = swap(maxArray, i, currNum);    
                }
            }
            
            currNum = num;
            // find the correct position for current number in minArray
            for (int i = 0; i < 4; i++) {
                if (currNum <= minArray[i]) {
                    currNum = swap(minArray, i, currNum);
                }
            }
        }
        
        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= 3; i++) {
            res = Math.min(res, maxArray[i] - minArray[3 - i]);
        }
        
        return res;
    }
    
    private static int swap (int[] arr, int index, int val) {
        int temp = arr[index];
        arr[index] = val;
        return temp;
    }
}

/**
 * Testcases:
 * [1,5,0,10,100], 1
 * [1,2,3,4] 1
 * [1,1] 0
 * [1] 0
 * [100,99,98,1,2,3,4] 3
 * [100,98,97,96,1,2,3] 4
 * [100,98,97,96,1,2,3,4] 95
 * [100,100,100,100,1,1,1] 0
 * [100,100,100,100,1,1,1,1] 99
 * [100,100,100,100,100,1,1,1] 0
 */