// 977. Squares of a Sorted Array
// One pass
class Solution {
    public int[] sortedSquares(int[] A) {
        int[] res = new int[A.length];
        if (A == null || A.length == 0) {
            return res;
        }
        List<Integer> list = new ArrayList<>();
        int left = 0, right = A.length - 1;
        while (A[left] < 0 && A[right] > 0) {
            if (A[left] * A[left] > A[right] * A[right]) {
                list.add(A[left] * A[left]);
                left++;
            } else {
                list.add(A[right] * A[right]);
                right--;
            }
        }
        while (left < A.length && A[left] < 0) {
            list.add(A[left] * A[left]);
            left++;
        }
        while (right >= 0 && A[right] > 0) {
            list.add(A[right] * A[right]);
            right--;
        }
        
        // add 0
        while (left < A.length && A[left] == 0) {
            list.add(0);
            left++;
        }
        
        for (int i = list.size() - 1; i >= 0; i--) {
            res[list.size() - 1 - i] = list.get(i);
        }
        return res;
    }
}

class Solution {
    public int[] sortedSquares(int[] A) {
        int[] res = new int[A.length];
        if (A == null || A.length == 0) {
            return res;
        }
        
        int left = 0, right = A.length - 1;
        while (left < A.length && A[left] < 0) {
            left++;
        }
        while (right >= 0 && A[right] >= 0) {
            right--;
        }
        
        int i = 0;
        while (left < A.length && right >= 0) {
            if (A[left] * A[left] > A[right] * A[right]) {
                res[i] = A[right] * A[right];
                right--;
            } else {
                res[i] = A[left] * A[left];
                left++;
            }
            i++;
        }
        
        while (left < A.length) {
            res[i] = A[left] * A[left];
            left++;
            i++;
        }
        
        while (right >= 0) {
            res[i] = A[right] * A[right];
            right--;
            i++;
        }
        return res;
    }
}