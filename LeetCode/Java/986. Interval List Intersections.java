// 986. Interval List Intersections
class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> list = new ArrayList<>();
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return new int[][]{};
        }
        
        int i = 0;  // point to A
        int j = 0;  // point to B
        while (i < A.length && j < B.length) {
            if (A[i][0] < B[j][0]) {
                // A.start is in front of B.start
                if (A[i][1] < B[j][0]) {
                    // A.end < B.start
                    i++;
                } else {
                    // overlap some interval
                    // A[i][0] ======= A[i][1]
                    //   B[j][0] ============ ?
                    if (A[i][1] < B[j][1]) {
                        list.add(new int[]{B[j][0], A[i][1]});
                        i++;
                    } else {
                        list.add(new int[]{B[j][0], B[j][1]});  
                        j++;
                    }
                }
            } else {
                if (B[j][1] < A[i][0]) {
                    j++;
                } else {
                    if (B[j][1] < A[i][1]) {
                        list.add(new int[]{A[i][0], B[j][1]});
                        j++;
                    } else {
                        list.add(new int[]{A[i][0], A[i][1]});  
                        i++;
                    }
                }
            }
        }
        
        int[][] res = new int[list.size()][2];
        for (int k = 0; k < list.size(); k++) {
            res[k] = list.get(k);
        }
        return res;
    }
}

class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> list = new ArrayList<>();
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return new int[][]{};
        }
        
        for (int i = 0, j = 0; i < A.length && j < B.length; ) {
            int[] intA = A[i];
            int[] intB = B[j];
            if (intA[1] < intB[0]) {
                i++;
            } else if (intB[1] < intA[0]) {
                j++;
            } else {
                // has intersection
                int start = Math.max(intA[0], intB[0]);
                int end = Math.min(intA[1], intB[1]);
                list.add(new int[]{start, end});
                if (intA[1] < intB[1]) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}