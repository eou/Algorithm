// 852. Peak Index in a Mountain Array
// 二分查找入门题
class Solution {
    public int peakIndexInMountainArray(int[] A) {
        int i = 1;
        for(; i < A.length - 1; i++) {
            // 其实可以简化为 A[i] > A[i + 1] 即可
            if(A[i] > A[i - 1] && A[i] > A[i + 1]) {
                break;
            }
        }
        return i;
    }
}

class Solution {
    public int peakIndexInMountainArray(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else if (nums[mid] > nums[mid - 1] && nums[mid] < nums[mid + 1]) {
                left = mid;
            } else if (nums[mid] < nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                right = mid;
            }
        }

        if (nums[left] > nums[right]) {
            return left;
        } else {
            return right;
        }
    }
}

class Solution {
    public int peakIndexInMountainArray(int[] A) {
        int left = 0, right = A.length - 1;
        while(left < right) {
            int mid = left + (right - left) / 2;
            if(A[mid] < A[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
}