// 278. First Bad Version
class Solution {
    public int firstBadVersion(int n) {
        int start = 1, end = n;

        while (start < end) {
            int mid = start + (end - start) / 2;
            if (isBadVersion(mid)) {
                // 相当于找后半部分的首元素
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}