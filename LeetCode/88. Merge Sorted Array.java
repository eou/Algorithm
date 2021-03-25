// 88. Merge Sorted Array
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int ptr = m + n - 1, ptr1 = m - 1, ptr2 = n - 1;
        while (ptr1 >= 0 && ptr2 >= 0) {
            if (nums1[ptr1] > nums2[ptr2]) {
                nums1[ptr] = nums1[ptr1];
                ptr1--;
            } else {
                nums1[ptr] = nums2[ptr2];
                ptr2--;
            }
            ptr--;
        }

        while (ptr1 >= 0) {
            nums1[ptr] = nums1[ptr1];
            ptr1--;
            ptr--;
        }

        while (ptr2 >= 0) {
            nums1[ptr] = nums2[ptr2];
            ptr2--;
            ptr--;
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