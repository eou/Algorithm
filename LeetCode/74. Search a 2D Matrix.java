// 74. Search a 2D Matrix
// 当作一维数组处理即可
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int start = 0, end = matrix[0].length * matrix.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int x = mid / matrix[0].length, y = mid % matrix[0].length;
            if (matrix[x][y] == target) {
                return true;
            } else if (matrix[x][y] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return false;
    }
}