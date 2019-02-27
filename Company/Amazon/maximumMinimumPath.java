class maximumMinimumPath {
    int min, max, row, col;

    // O(2^(m+n))
    public int solution1(int[][] matrix) {
        row = matrix.length;
        col = matrix[0].length;
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        dfs(matrix, min, 0, 0);
        return max;
    }

    public void dfs(int[][] matrix, int min, int i, int j) {
        if (i >= row || j >= col) {
            return;
        }

        if (i == row - 1 && j == col - 1) {
            min = Math.min(min, matrix[i][j]);
            max = Math.max(max, min);
            return;
        }

        min = Math.min(min, matrix[i][j]);
        dfs(matrix, min, i, j + 1);
        dfs(matrix, min, i + 1, j);
    }

    // dp, O(mn)
    public int solution2(int[][] matrix) {
        int[] result = new int[matrix[0].length];
        result[0] = matrix[0][0];
        for (int i = 1; i < matrix[0].length; i++) {
            result[i] = Math.min(result[i - 1], matrix[0][i]);
        }

        for (int i = 1; i < matrix.length; i++) {
            result[0] = Math.min(matrix[i][0], result[0]);
            for (int j = 1; j < matrix[0].length; j++) {
                result[j] = (result[j] < matrix[i][j] && result[j - 1] < matrix[i][j] ? Math.max(result[j - 1], result[j]) : matrix[i][j]);
            }
        }

        return result[result.length - 1];
    }
    
    // dp, O(mn)
    public int solution3(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m]; // the target value from dp[0][0] to dp[i][j]
        dp[0][0] = matrix[0][0];    // must including matrix[0][0]
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.min(dp[i - 1][0], matrix[i][0]);
        }
        for (int i = 1; i < m; i++) {
            dp[0][i] = Math.min(dp[0][i - 1], matrix[0][i]);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.min(Math.max(dp[i - 1][j], dp[i][j - 1]), matrix[i][j]);
            }
        }
        return dp[n - 1][m - 1];
    }

    public static void main(String[] args) {
        maximumMinimumPath s = new maximumMinimumPath();

        int[][] matrix1 = {{5, 4, 5}, {1, 3, 6}};
        int[][] matrix2 = {{8, 4, 7}, {6, 5, 9}};
        System.out.println(s.solution1(matrix1));
        System.out.println(s.solution1(matrix2));
        System.out.println(s.solution2(matrix1));
        System.out.println(s.solution2(matrix2));
        System.out.println(s.solution3(matrix1));
        System.out.println(s.solution3(matrix2));
    }
}