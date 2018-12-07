/**
 * Move Zeroes To The Beginning.java
 */
import java.util.*;
import java.io.*;

public class Solution {
    public void moveZeroesToTheBeginning(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while(left < right) {
            while(nums[left] == 0 && left < right) {
                left++;
            }
            while(nums[right] != 0 && left < right) {
                right--;
            }

            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }
    
    public void moveZeroesToTheBeginning(int[] nums) {
        int nonZeroNum = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                nonZeroNum++;
            }
            if(nums[i] == 0) {
                if(nonZeroNum != 0) {
                    int tmp = nums[i];
                    nums[i] = nums[i - nonZeroNum];
                    nums[i - nonZeroNum] = tmp;
                }
            }
        }
    }

    public void moveZeroesToTheBeginning(int[] nums) {
        // 不改变原来数字的顺序
        int zeroNum = 0;
        for(int i = nums.length - 1; i >= 0; i--) {
            if(nums[i] == 0) {
                zeroNum++;
            } else {
                if(zeroNum != 0) {
                    nums[i + zeroNum] = nums[i];
                }
            }
        }
        for(int i = zeroNum - 1; i >= 0; i--) {
            nums[i] = 0;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 3, 5, 5, 0, 0, -1, 0, 1, 1};
        Solution s = new Solution();
        s.moveZeroesToTheBeginning(nums);
        for(int n : nums) {
            System.out.println(n);
        }
    }
}