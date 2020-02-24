// 240. Search a 2D Matrix II
// 从左下/右上对角线出发比较，O(n + m)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        // 左下出发比较
        int x = matrix.length - 1, y = 0;
        while (x >= 0 && y < matrix[0].length) {
            if (matrix[x][y] < target) {
                y++;
            } else if (matrix[x][y] > target) {
                x--;
            } else {
                return true;
            }
        }
        return false;
    }
}

// divide and conquer, O(mlogn)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int n = matrix.length, m = matrix[0].length;
        return helper(matrix, 0, n - 1, 0, m - 1, target);
    }

    public boolean helper(int[][] matrix, int rowStart, int rowEnd, int colStart, int colEnd, int target) {
        if (rowStart > rowEnd || colStart > colEnd) {
            return false;
        }

        // mid
        int rm = (rowStart + rowEnd) / 2, cm = (colStart + colEnd) / 2;
        if (matrix[rm][cm] == target) {
            return true;
        } else if (matrix[rm][cm] > target) {
            return helper(matrix, rowStart, rm - 1, colStart, cm - 1, target)
                    || helper(matrix, rm, rowEnd, colStart, cm - 1, target)
                    || helper(matrix, rowStart, rm - 1, cm, colEnd, target);
        } else {
            return helper(matrix, rm + 1, rowEnd, cm + 1, colEnd, target)
                    || helper(matrix, rm + 1, rowEnd, colStart, cm, target)
                    || helper(matrix, rowStart, rm, cm + 1, colEnd, target);
        }
    }
}

// 如用二分法，则是O(log(n!))，参看LeetCode Solution