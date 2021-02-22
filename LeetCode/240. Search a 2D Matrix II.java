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

// 沿着对角线对每行每列二分法，则是 O(log(n!)) 优于 O(nlogn)，分析参看 LeetCode Solution
// --------->
// |-------->
// ||------->
// |||------>
// ||||
// ∨∨∨∨
class Solution {
    private boolean binarySearch(int[][] matrix, int target, int start, boolean vertical) {
        int lo = start;
        int hi = vertical ? matrix[0].length - 1 : matrix.length - 1;

        while (hi >= lo) {
            int mid = (lo + hi) / 2;
            if (vertical) { // searching a column
                if (matrix[start][mid] < target) {
                    lo = mid + 1;
                } else if (matrix[start][mid] > target) {
                    hi = mid - 1;
                } else {
                    return true;
                }
            } else { // searching a row
                if (matrix[mid][start] < target) {
                    lo = mid + 1;
                } else if (matrix[mid][start] > target) {
                    hi = mid - 1;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        // iterate over matrix diagonals
        int shorterDim = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < shorterDim; i++) {
            boolean verticalFound = binarySearch(matrix, target, i, true);
            boolean horizontalFound = binarySearch(matrix, target, i, false);
            if (verticalFound || horizontalFound) {
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