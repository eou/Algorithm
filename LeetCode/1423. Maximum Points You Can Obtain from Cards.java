// 1423. Maximum Points You Can Obtain from Cards
// sliding window
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        if (k == 0 || n == 0) {
            return 0;
        }

        int res = 0, sum = 0;
        for (int i = n - k; i < n; i++) {
            sum += cardPoints[i];
        }

        res = sum;
        if (k >= n) {
            return res;
        }

        int left = n - k, right = 0;
        while (right < k) {
            sum -= cardPoints[left];
            sum += cardPoints[right];
            left++;
            right++;
            res = Math.max(res, sum);
        }
        return res;
    }
}

// Problem Translation: Find the smallest subarray sum of length len(cardPoints) - k
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length - k, total = 0, minSum = Integer.MAX_VALUE, sum = 0;

        int l = 0, r = 0;
        while (r < cardPoints.length) {
            total += cardPoints[r];
            sum += cardPoints[r];
            // sliding window
            if (r - l + 1 == n) {
                minSum = Math.min(minSum, sum);
                sum -= cardPoints[l];
                l++; // control size
            }
            r++;
        }

        return total - (minSum == Integer.MAX_VALUE ? 0 : minSum);
    }
}