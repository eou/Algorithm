import java.util.*;

/**
 * 在除对角线方向的元素以外旋转矩阵 k 次, 每次顺时针旋转 90 度, 其中 1 <= k <= 4
 */
class Solution {
    public int[][] RotateDiagonal(int[][] A, int k) {
        for (int i = 0; i < k; i++) {
            A = rotate(A);
        }
        return A;
    }

    public int[][] rotate(int[][] A) {
        // 主对角线交换
        for (int i = 0; i < A.length; i++) {
            for (int j = i; j < A[i].length; j++) {
                if (i != j && i + j != A.length - 1) {
                    int tmp = A[i][j];
                    A[i][j] = A[j][i];
                    A[j][i] = tmp;
                }
            }
        }
        // 左右交换
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length / 2; j++) {
                if (i != j && i + j != A.length - 1) {
                    int tmp = A[i][j];
                    A[i][j] = A[i][A.length - 1 - j];
                    A[i][A.length - 1 - j] = tmp;
                }
            }

        }
        return A;
    }

    public static void test(int[][] A, int k) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + ", ");
            }
            System.out.println();
        }
        Solution s = new Solution();
        int[][] B = s.RotateDiagonal(A, k);
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[i].length; j++) {
                System.out.print(B[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] A = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        test(A, 4);
        int[][] B = new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 },
                { 21, 22, 23, 24, 25 } };
        test(B, 4);
    }
}

/**
 * [1, 2, 3, 4, 5] [6, 7, 8, 9, 10], [11,12,13,14,15], [16,17,18,19,20],
 * [21,22,23,24,25]
 * 
 * [1,16,11,6,5], [22,7,12,9,2], [23,18,13,8,3], [24,17,14,19,4],
 * [21,10,15,20,25]
 */