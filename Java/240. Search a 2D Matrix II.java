// 240. Search a 2D Matrix II
// 如用二分法，则是O(log(n!))，参看LeetCode Solution
// 如用分治法，则是O(mlogn)
// 从左下/右上对角线出发比较，O(n+m)
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
            } 
            else if (matrix[x][y] > target) {
                x--;
            } 
            else {
                return true;
            }
        }
        return false;
    }
}