// 88. Merge Sorted Array
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // [2,4,6,8,0,0]
        //        i   k
        // [3,5]
        //    j
        // [2,4,6,8,6,8] => [2,4,6,5,6,8] => [2,4,4,5,6,8]
        //    i   k              k              k
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                --k;
                --i;
            } else {
                nums1[k] = nums2[j];
                --k;
                --j;
            }

        }

        while (j >= 0) {
            nums1[k] = nums2[j];
            --k;
            --j;
        }
    }
}

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int ptr1 = 0, ptr2 = 0;
        while (ptr2 < nums2.length && ptr1 < nums1.length) {
            if (nums1[ptr1] <= nums2[ptr2]) {
                ++ptr1;
            } else {
                moveBack(nums1, ptr1);
                nums1[ptr1] = nums2[ptr2];
                ++ptr2;
            }
        }
        if (ptr2 < nums2.length) {
            for (int i = 1; i <= nums2.length - ptr2 && nums2.length - i >= ptr2; ++i) {
                nums1[nums1.length - i] = nums2[nums2.length - i];
            }
        }
    }

    public void moveBack(int[] nums, int start) {
        for (int i = nums.length - 1; i > start; --i) {
            nums[i] = nums[i - 1];
        }
    }
}

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
    }
}