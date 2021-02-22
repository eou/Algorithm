// 74. Search a 2D Matrix
// 当作一维数组处理即可
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int row = matrix.length, col = matrix[0].length;
        int left = 0, right = row * col - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            int x = mid / col, y = mid % col;
            if (matrix[x][y] < target) {
                left = mid;
            } else if (matrix[x][y] > target) {
                right = mid;
            } else {
                return true;
            }
        }

        if (matrix[left / col][left % col] == target || matrix[right / col][right % col] == target) {
            return true;
        }

        return false;
    }
}

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

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = binarySearch(getColumn(matrix, 0), target);
        return matrix[row][binarySearch(matrix[row], target)] == target;
    }

    private int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid;
            } else {
                left = mid;
            }
        }

        if (nums[left] == target) {
            return left;
        }

        if (nums[right] == target) {
            return right;
        }

        if (nums[right] < target) {
            return right;
        } else {
            return left;
        }
    }

    private int[] getColumn(int[][] matrix, int column) {
        return IntStream.range(0, matrix.length).map(i -> matrix[i][column]).toArray();
    }
}