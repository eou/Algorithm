// 454. 4Sum II
// TLE
class Solution {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int result = 0;
        Map<Integer, Integer> mapAB = new HashMap<>();  // sum => pairs number
        Map<Integer, Integer> mapCD = new HashMap<>();  // sum => pairs number
        storePairs(mapAB, A, B);
        storePairs(mapCD, C, D);
        for (Integer i : mapAB.keySet()) {
            for (Integer j : mapCD.keySet()) {
                if (i + j == 0) {
                    result += (mapAB.get(i) * mapCD.get(j));
                }
            }
        }
        
        return result;
    }
    
    public void storePairs(Map<Integer, Integer> map, int[] A, int[] B) {
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < B.length; ++j) {
                map.put(A[i] + B[j], map.getOrDefault(A[i] + B[j], 0) + 1);
            }
        }
    }
}

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