// 350. Intersection of Two Arrays II
// O(nlogn)
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[] {};
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
            } else if (nums1[ptr1] < nums2[ptr2]) {
                ptr1++;
            } else {
                ptr2++;
            }
        }

        return Arrays.copyOfRange(res, 0, ptr);
    }
}

// O(n + m)
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int n : nums1) {
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }
}