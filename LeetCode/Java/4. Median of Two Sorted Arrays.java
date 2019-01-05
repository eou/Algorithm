// 4. Median of Two Sorted Arrays
// 本质是寻找两个数组中第 k 个元素，不同的代码基本都是二分查找思路，时间复杂度log(m+n)，不同之处在于找第 k 个元素的方式
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        if (len % 2 == 0) {
            return (findKth(nums1, 0, nums2, 0, len / 2) + findKth(nums1, 0, nums2, 0, len / 2 + 1)) / 2;
        }
        return findKth(nums1, 0, nums2, 0, len / 2 + 1);
    }

    // 这个 findKth() 在一些情况下要快于下面另一个版本的 findKth() 的实现
    // 一是丢弃元素的时候，当短数组长度小于k/2，长数组会丢弃更多的元素而不是k/2个；二是在两个中点元素相等的时候可以直接返回
    double findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        int len1 = nums1.length - start1;
        int len2 = nums2.length - start2;
        // assume the length of nums1 should always no longer than the length of nums2
        if (len1 > len2) {
            return findKth(nums2, start2, nums1, start1, k);
        }
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }
        if (len2 == 0) {
            return nums1[start1 + k - 1];
        }

        // Base Case. 在这里必须要退出。因为k = 1的时候，不可能再分了。
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        
        // [1,7], [0,2,3,4,5,6,7,9,11], 11/2 = 5
        // pa = 2, pb = 3 => value: nums1: 7, nums2: 3 => start1: 0, start2: 3 => newk: 2
        // pa = 1, pb = 1 => value: nums1: 1, nums2: 4 => start1: 1, start2: 3 => newk: 1
        // len1 == 0 => nums2[start2 + k - 1] = 4
        int pa = Math.min(k / 2, len1);
        int pb = k - pa;
        if (nums1[start1 + pa - 1] < nums2[start2 + pb - 1]) {
            // we could discard the pa smaller numbers before nums1[start1 + pa - 1], so the new start1 should be start1 + pa and the new k is k - pa
            return findKth(nums1, start1 + pa, nums2, start2, k - pa);
        } else if (nums1[start1 + pa - 1] > nums2[start2 + pb - 1]) {
            return findKth(nums1, start1, nums2, start2 + pb, k - pb);
        } else {
            // 这个相等时候直接返回很关键，如果这个情况放在前面两个if-else语句中一起处理也正确，但是递归次数会倍增
            // 因为 pa + pb = k, 每次比较的时候，start1 + pa - 1 说明我们已经排除了前 pa - 1 个元素，start2 + pb - 1 说明我们已经排除了前 pb - 1 个元素，总共排除了前 k - 2 个元素
            // 当 nums1[start1 + pa - 1] == nums2[start2 + pb - 1] 的时候说明整个大数组中我们已经排除了前 k - 2 个数字，这两个就能当做第 k - 1 和 k 个数字
            return nums1[start1 + pa - 1];
        }
    }
}

class Solution {
    // 不同版本的 findKth() 对于二分的实现略有不同，其他一致
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        if (len % 2 == 0) {
            return (findKth(nums1, 0, nums2, 0, len / 2) + findKth(nums1, 0, nums2, 0, len / 2 + 1)) / 2;
        }
        return findKth(nums1, 0, nums2, 0, len / 2 + 1);
    }

    double findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        int len1 = nums1.length - start1;
        int len2 = nums2.length - start2;

        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }
        if (len2 == 0) {
            return nums1[start1 + k - 1];
        }

        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        // -1是因为索引本身是从0开始的。而前k大元素含有k个元素。
        int mid = k / 2 - 1;
        // [1,7], [0,2,3,4,5,6,7,9,11,12,13], 13/2 = 6
        // mid = 2 => keyA = MAX, keyB = 3 => start1: 0, start2: 3, kNew: 3
        // mid = 0 => keyA = 1, keyB = 4 => start1: 1, start2: 3, kNew: 2
        // len1 == 0 => nums2[start2 + k - 1] = 4
        
        // [6], [0,2,3,4,5,7,9,11,12,13], 11/2 = 5
        // mid = 1 => keyA = MAX, keyB = 2 => start1: 0, start2: 2, kNew: 3
        // mid = 0 => keyA = 6, keyB = 3 => start1: 0, start2: 3, kNew: 2
        // mid = 0 => keyA = 6, keyB = 4 => start1: 0, start2: 4, kNew: 1
        // k == 1 => nums1[0]
        // 越界的时候key设为无穷大是为了丢弃对方数组的左边部分
        int keyA = start1 + mid >= nums1.length ? Integer.MAX_VALUE : nums1[start1 + mid];
        int keyB = start2 + mid >= nums2.length ? Integer.MAX_VALUE : nums2[start2 + mid];

        // 因为要丢弃k / 2个元素，注意kNew != k / 2
        int kNew = k - k / 2;
        if (keyA < keyB) {
            return findKth(nums1, start1 + k / 2, nums2, start2, kNew);
        } else {
            return findKth(nums1, start1, nums2, start2 + k / 2, kNew);
        }
        // !!! 这里的 keyA == keyB 时不能直接返回key，因为 keyA 和 keyB 比较的时候并不一定已经排除了前 k - 2 个数字
    }
}

