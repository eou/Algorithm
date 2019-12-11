// 73. Set Matrix Zeroes
class Solution {
    // 空间复杂度 O(m + n)，时间复杂度O(m * n)
    public void setZeroes(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        boolean rows[] = new boolean[matrix.length];
        boolean cols[] = new boolean[matrix[0].length];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0) {
                    rows[i] = true;
                    break;
                }
            }
        }
        for(int i = 0; i < matrix[0].length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(matrix[j][i] == 0) {
                    cols[i] = true;
                    break;
                }
            }
        }
        
        for(int i = 0; i < matrix.length; i++) {
            if(rows[i] == true) {
                setRow(i, matrix);
            }
        }
        
        for(int i = 0; i < matrix[0].length; i++) {
            if(cols[i] == true) {
                setCol(i, matrix);
            }
        }
    }
    
    private void setRow(int row, int[][] matrix) {
        for(int i = 0; i < matrix[0].length; i++) {
            matrix[row][i] = 0;
        }
    }
    
    private void setCol(int col, int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            matrix[i][col] = 0;
        }
    }
}

class Solution {
    // 空间复杂度 O(1)，时间复杂度O(m * n)，但并不准确
    private static int sentinel = -1000000; // Java 在 int 类型中不能设置除数字之外的值，Python就可以，-1000000 在这里只是一个AC巧合值
    public void setZeroes(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0) {
                    set(i, j, matrix);
                }
            }
        }
        
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == sentinel) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    private void set(int row, int col, int[][] matrix) {
        for(int i = 0; i < matrix[0].length; i++) {
            if(matrix[row][i] != 0) {
                matrix[row][i] = sentinel;
            }
        }
        for(int i = 0; i < matrix.length; i++) {
            if(matrix[i][col] != 0) {
                matrix[i][col] = sentinel;
            }
        }
    }
}

class Solution {
    // 空间复杂度 O(1)，时间复杂度O(m * n)
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        // 将每行每列的第一个元素作为哨兵值
        int rows = matrix.length, cols = matrix[0].length;
        // 注意第一行第一列为特殊情况，交叉点 matrix[0][0] 的值不能同时用作判断第一行和第一列是否变为0，需要辅助变量col0来分担一个功能
        boolean col0 = false; 
        for (int i = 0; i < rows; ++i) {
            if (matrix[i][0] == 0) {
                col0 = true;
            }
            for (int j = 1; j < cols; ++j) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = rows - 1; i >= 0; --i) {
            for (int j = cols - 1; j >= 1; --j) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (col0) {
                matrix[i][0] = 0;
            }
        }
    }
}