// 304. Range Sum Query 2D - Immutable
// 与 303 类似，换成了二维前缀和数组，可以摊成一维数组处理，也可以直接处理
class NumMatrix {
    private int[][] prefix;
    public NumMatrix(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        prefix = new int[matrix.length + 1][matrix[0].length + 1];
        for(int i = 0; i < matrix[0].length + 1; i++) {
            prefix[0][i] = 0;
        }
        for(int i = 0; i < matrix.length + 1; i++) {
            prefix[i][0] = 0;
        }
        
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                prefix[i + 1][j + 1] = matrix[i][j] + prefix[i][j + 1] + prefix[i + 1][j] - prefix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return (prefix[row2 + 1][col2 + 1] - prefix[row2 + 1][col1] - prefix[row1][col2 + 1] + prefix[row1][col1]);
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */