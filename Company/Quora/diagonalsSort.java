import java.util.*;

/**
 * 把⼀个矩阵按斜线顺序重排？？
 */
class Solution {
    public void diagonalsSort(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        for (int d = 0; d < n; d++) {
            List<Integer> temp = new ArrayList();
            for (int i = 0; i < n - d; i++) {
                temp.add(matrix[i][i + d]);
            }
            Collections.sort(temp);
            for (int i = 0; i < n - d; i++) {
                matrix[i][i + d] = temp.get(i);
            }
            temp.clear();
            if (d != 0) {
                for (int i = 0; i < n - d; i++) {
                    temp.add(matrix[i + d][i]);
                }
                Collections.sort(temp);
                for (int i = 0; i < n - d; i++) {
                    matrix[i + d][i] = temp.get(i);
                }
            }
        }
    }

    public static void print2D(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // int[][] matrix = new int[][] { { 8, 4, 1 }, { 4, 4, 1 }, { 4, 8, 9 } };
        // int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        // int[][] matrix = new int[][] { { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 } };
        // int[][] matrix = new int[][] { { 1, 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 } };
        // int[][] matrix = new int[][] { { 3, 2, 1 }, { 3, 2, 1 }, { 3, 2, 1 } };
        // int[][] matrix = new int[][] { { 9, 8, 7 }, { 6, 5, 4 }, { 3, 2, 1 } };
        int[][] matrix = new int[][] { { 2, 8, 6 }, { 7, 5, 1 }, { 3, 9, 0 } };
        print2D(matrix);
        s.diagonalsSort(matrix);
        print2D(matrix);
    }
}