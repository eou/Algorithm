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