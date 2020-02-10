// 969. Pancake Sorting
// flip the largest number to the tail
class Solution {
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < A.length - 1; i++) {
            // each round at most 10 actions
            // 1. find ith max number
            int curMaxIdx = 0;
            for (int j = 0; j < A.length - i; j++) {
                if (A[j] > A[curMaxIdx]) {
                    curMaxIdx = j;
                }
            }
            reverse(A, 0, curMaxIdx);
            reverse(A, 0, A.length - i - 1);
            res.add(curMaxIdx + 1);
            res.add(A.length - i);
        }
        
        return res;
    }
    
    public void reverse(int[] A, int left, int right) {
        while (left < right) {
            int tmp = A[left];
            A[left] = A[right];
            A[right] = tmp;
            left++;
            right--;
        }
    }
}