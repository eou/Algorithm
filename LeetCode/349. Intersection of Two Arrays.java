// 349. Intersection of Two Arrays
// Two pointer, O(nlogn)
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        int[] res = new int[nums1.length];
        int ptr = 0, ptr1 = 0, ptr2 = 0;
        while (ptr1 < nums1.length && ptr2 < nums2.length) {
            if (nums1[ptr1] == nums2[ptr2]) {
                res[ptr] = nums1[ptr1];
                ptr1++;
                ptr2++;
                ptr++;
                while (ptr1 < nums1.length && nums1[ptr1] == nums1[ptr1 - 1]) {
                    ptr1++;
                }
                while (ptr2 < nums2.length && nums2[ptr2] == nums2[ptr2 - 1]) {
                    ptr2++;
                }
            } else if (nums1[ptr1] < nums2[ptr2]) {
                ptr1++;
            } else {
                ptr2++;
            }
        }
        
        return Arrays.copyOfRange(res, 0, ptr);
    }
}

// Set, O(n)
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> intersect = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            if (set.contains(nums2[i])) {
                intersect.add(nums2[i]);
            }
        }
        int[] result = new int[intersect.size()];
        int i = 0;
        for (Integer num : intersect) {
            result[i++] = num;
        }
        return result;
    }
}