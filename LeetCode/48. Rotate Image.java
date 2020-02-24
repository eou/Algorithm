// 48. Rotate Image
class Solution {
    public void rotate(int[][] matrix) {
        // coordinate transform:
        // assume center point's position: center(cx, cy)
        // for any point (x, y) to center, horizontal distance is |x - cx|, vertical distance is |y - cy|
        // after rotation, horizontal distance is |y - cy|, vertical distance is |x - cx|
        // thus we can use 2 transforms to achieve this affect
        // 1. swap the point which is symmetric with the second diagonol
        // 2. swap the point which is symmetric with the center horizotal line
        
        // 1. swap by second diagnal 
        // i+j-n+1 = -(i0+j0-n+1)
        // i-j = i0-j0
        // i0 = n-1-j, j0 = n-1-i
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length - i; j++) {
                int ii = matrix.length - 1 - j;
                int jj = matrix.length - 1 - i;
                swap(matrix, i, j, ii, jj);
            }
        }
        
        // 2. swap by center horizontal line
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int ii = matrix.length - 1 - i;
                int jj = j;
                swap(matrix, i, j, ii, jj);
            }
        }
    }
    
    public void swap(int[][] matrix, int x1, int y1, int x2, int y2) {
        int tmp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = tmp;
    }
}