// 85. Maximal Rectangle
// ***二维矩阵每一层向上都可以看做一个直方图
class Solution {
    // 与 84. Largest Rectangle in Histogram 递增栈版本的思路完全一样，这里时间复杂度为 O(n^2)
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        for(int i = 0; i < matrix.length; i++) {
            maxArea = Math.max(maxArea, helper(matrix, i));
        }
        return maxArea;
    }
    
    // calculate largest rectangle in histogram based on each row
    private int helper(char[][] matrix, int row) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int maxArea = 0;
        for(int i = 0; i < matrix[0].length; i++) {
            if(stack.peek() == -1 || height(matrix, row, i) > height(matrix, row, stack.peek())) {
                stack.push(i);
            } else {
                maxArea = Math.max(maxArea, height(matrix, row, stack.pop()) * (i - stack.peek() - 1));
                i--;
            }
        }
        while(stack.peek() != -1) {
            maxArea = Math.max(maxArea, height(matrix, row, stack.pop()) * (matrix[0].length - stack.peek() - 1));
        }
        return maxArea;
    }
    
    // 计算高度这一步可以简化重复计算
    private int height(char[][] matrix, int row, int col) {
        int height = 0;
        while(row >= 0) {
            if(matrix[row][col] == '1') {
                height++;
            } else {
                break;
            }
            row--;
        }
        return height;
    }
}

class Solution {
    // 另一种写法
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] height = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            maxArea = Math.max(maxArea, helper(matrix, i, height));
        }
        return maxArea;
    }

    private int helper(char[][] matrix, int row, int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int maxArea = 0;
        // 改进 height 的计算，其实是二维 DP 数组可以用一维解决，height(row, col) = matrix[row][col] == 1 ? height(row - 1, col) + 1 : 0
        for (int i = 0; i < matrix[0].length; i++) {
            height[i] = matrix[row][i] == '1' ? height[i] + 1 : 0;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            if (stack.peek() == -1 || height[i] > height[stack.peek()]) {
                stack.push(i);
            } else {
                maxArea = Math.max(maxArea, height[stack.pop()] * (i - stack.peek() - 1));
                i--;
            }
        }
        while (stack.peek() != -1) {
            maxArea = Math.max(maxArea, height[stack.pop()] * (matrix[0].length - stack.peek() - 1));
        }
        return maxArea;
    }
}

class Solution {
    // 二维 DP 版本，时间复杂度为 O(n^2)
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        // 三个数组都是 DP 状态数组，有一维数组解决二维的问题，每一行的高、左、右都是跟上一行相关
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] height = new int[cols];
        int[] left = new int[cols];
        int[] right = new int[cols];
        Arrays.fill(right, cols);
        for(int i = 0; i < rows; i++) {
            int cur_left = 0;
            int cur_right = cols;
            for(int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    height[j]++;
                } else {
                    height[j] = 0;
                }
            }
            for(int j = 0; j < cols; j++) {
                if(matrix[i][j] == '1') {
                    // left(row, col) = matrix[row][col] == 1 ? max(left(row - 1, col), cur_left)
                    left[j] = Math.max(left[j], cur_left); // left[j] 默认为 0
                } else {
                    left[j] = 0;
                    cur_left = j + 1;
                }
            }
            for(int j = cols - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], cur_right); // right[j] 默认为 cols
                } else {
                    right[j] = cols;
                    cur_right = j;
                }
            }
            for(int j = 0; j < cols; j++) {
                maxArea = Math.max(maxArea, (right[j] - left[j]) * height[j]);
            }
        }

        return maxArea;
    }
}
