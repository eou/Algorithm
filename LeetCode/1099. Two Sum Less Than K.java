// 1099. Two Sum Less Than K
class Solution {
    public int twoSumLessThanK(int[] A, int K) {
        Arrays.sort(A);
        
        int left = 0, right = A.length - 1;
        int res = -1;
        
        while (left < right) {
            int sum = A[left] + A[right];
            if (sum >= K) {
                right--;
            } else {
                if (sum > res) {
                    res = sum;
                }
                left++;
            }
        }
        
        return res;
    }
}