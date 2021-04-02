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
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        if (mat1 == null || mat2 == null || mat1.length == 0 || mat2.length == 0 || mat1[0].length == 0 || mat2[0].length == 0) {
            return new int[0][0];
        }
        
        List<int[]> m1 = extractNonZeroNum(mat1);
        List<int[]> m2 = extractNonZeroNum(mat2);
        
        int[][] res = new int[mat1.length][mat2[0].length];
        for (int[] a : m1) {
            for (int[] b : m2) {
                if (a[1] == b[0]) {
                    res[a[0]][b[1]] += (mat1[a[0]][a[1]] * mat2[b[0]][b[1]]);
                }
            }
        }
        return res;
    }
    
    private List<int[]> extractNonZeroNum(int[][] mat) {
        List<int[]> m = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] != 0) {
                    m.add(new int[]{i, j});
                }
            }
        }
        return m;
    }
}
