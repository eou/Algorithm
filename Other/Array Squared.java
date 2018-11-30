/**
 * Array Squared.java
 * 将一个有序数组里每个数平方后返回一个结果有序数组
 * 注意先找到绝对值最小的数字，然后左右扫描
 */
import java.io.*;
import java.util.*;
class Solution {
    public int[] arraySquare(int[] nums) {
        int[] result = new int[nums.length];
        if(nums.length < 1) {
            return result;
        }
        
        // find the number closest to 0
        int minIndex = 0;
        for(int i = 0; i < nums.length; i++) {
            if(Math.abs(nums[minIndex]) > Math.abs(nums[i])) {
                minIndex = i;
            }
        }
        result[0] = nums[minIndex] * nums[minIndex];
        int left = minIndex - 1;
        int right = minIndex + 1;
        int ptr = 1;
        while(left >= 0 && right < nums.length) {
            if(Math.abs(nums[left]) < Math.abs(nums[right])) {
                result[ptr] = nums[left] * nums[left];
                left--;
            } else {
                result[ptr] = nums[right] * nums[right];
                right++;
            }
            ptr++;
        }
        while(left >= 0) {
            result[ptr] = nums[left] * nums[left];
            ptr++;
            left--;
        }
        while(right < nums.length) {
            result[ptr] = nums[right] * nums[right];
            ptr++;
            right++;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = {-10, -3, -1, 0, 1, 5, 7, 11};
        for(int i : new Solution().arraySquare(input)) {
            System.out.println(i);
        }
    }
}