// 311. Sparse Matrix Multiplication
// A(n, t) * B(t, m) = C(n, m)
class Solution {
    // 暴力解决，如果遇到 0 就跳过
    public int[][] multiply(int[][] A, int[][] B) {
        int rows = A.length;
        int cols = B[0].length;
        int colA = A[0].length;
        
        int[][] C = new int[rows][cols];
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < colA; j++) {
                if(A[i][j] != 0) {
                    for(int k = 0; k < cols; k++) {
                        if(B[j][k] != 0) {
                            C[i][k] += A[i][j] * B[j][k];
                        }
                    }
                }
            }
        }
        
        return C;
    }
}

class Solution {
    // 将非 0 元素抽出来然后计算，时间复杂度相同
    public int[][] multiply(int[][] A, int[][] B) {
        // A(n, t) * B(t, m) = C(n, m)
        int n = A.length;
        int t = A[0].length;
        int m = B[0].length;
        int[][] C = new int[n][m];

        List<int[]> A_Points = getNonZeroPoints(A);
        List<int[]> B_Points = getNonZeroPoints(B);

        for (int[] pA : A_Points) {
            for (int[] pB : B_Points) {
                if (pA[1] == pB[0]) {
                    C[pA[0]][pB[1]] += A[pA[0]][pA[1]] * B[pB[0]][pB[1]];
                }
            }
        }

        return C;
    }

    private List<int[]> getNonZeroPoints(int[][] matrix) {
        List<int[]> nonZeroPoints = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    nonZeroPoints.add(new int[] { i, j });
                }
            }
        }
        return nonZeroPoints;
    }
}
