// 278. First Bad Version
public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (isBadVersion(left)) {
            return left;
        } else {
            return right;
        }
    }
}

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