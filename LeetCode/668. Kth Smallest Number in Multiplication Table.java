// 668. Kth Smallest Number in Multiplication Table
class Solution {
    public int findKthNumber(int m, int n, int k) {
        int left = 1;
        int right = m * n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (count(m, n, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    public int count(int m, int n, int mid) {
        int cnt = 0;
        // from 1 to m
        for (int i = 1; i <= m; i++) {
            cnt += (Math.min(mid / i, n));
        }
        return cnt;
    }
}