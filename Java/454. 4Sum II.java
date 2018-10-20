// 454. 4Sum II
class Solution {
    // 用O(n^2)空间复杂度换来O(n^2)的时间复杂度
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < B.length; j++) {
                int sum = A[i] + B[j];
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        
        for(int i = 0; i < C.length; i++) {
            for(int j = 0; j < D.length; j++) {
                int sum = 0 - C[i] - D[j];
                result += map.getOrDefault(sum, 0);
            }
        }
        
        return result;
    }
}