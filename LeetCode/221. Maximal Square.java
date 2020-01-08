// 221. Maximal Square
// brute-force, O(n^3)
class Solution {
    public int maximalSquare(char[][] matrix) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result = Math.max(result, validSquare(matrix, i, j));
            }
        }
        return result;
    }
    
    public int validSquare(char[][] matrix, int x, int y) {
        int size = 1;
        boolean notValid = false;
        while (x + size - 1 < matrix.length && y + size - 1 < matrix[0].length) {
            for (int i = 0; i < size; i++) {
                if (matrix[x + size - 1][y + i] != '1' || matrix[x + i][y + size - 1] != '1') {
                    notValid = true;
                    break;
                }
            }
            if (notValid) {
                break;
            }
            size++;
        }
        return (size - 1) * (size - 1);
    }
}

// prefix sum matrix
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int result = 0;
        int[][] prefix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == 0 || j == 0) {
                    if (i == 0 && j == 0) {
                        prefix[i][j] = matrix[i][j] - '0';
                    } else if (i == 0) {
                        prefix[i][j] = prefix[i][j - 1] + (matrix[i][j] - '0');
                    } else {
                        prefix[i][j] = prefix[i - 1][j] + (matrix[i][j] - '0');
                    }
                    result = Math.max(result, matrix[i][j] - '0');
                } else {
                    prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1] + (matrix[i][j] - '0');
                    int sz = Math.min(i, j);
                    for (int k = sz + 1; k >= 1; --k) {
                        int sum = prefix[i][j];
                        if (i - k >= 0) {
                            sum -= prefix[i - k][j];
                        }
                        if (j - k >= 0) {
                            sum -= prefix[i][j - k];
                        }
                        if (i - k >= 0 && j - k >= 0) {
                            sum += prefix[i - k][j - k];
                        }
                        if (sum == k * k) {
                            result = Math.max(result, sum);
                            // do not need to check smaller square
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}

// dp
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        // size
        int result = 0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = matrix[i][j] - '0';
                } else {
                    dp[i][j] = matrix[i][j] == '1' ? Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1 : 0;
                }
                result = Math.max(result, dp[i][j]);
            }
        }
        
        return result * result;
    }
}

// dp, rolling array, space O(n)
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int result = 0;
        // int[][] dp = new int[matrix.length][matrix[0].length];
        // dp[i] means dp[k][i]
        int[] dp = new int[matrix[0].length];
        int pre = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int sz = dp[j];
                if (i == 0 || j == 0) {
                    dp[j] = matrix[i][j] - '0';
                } else {
                    // dp[i][j] = matrix[i][j] == '1' ? Math.min(Math.min(dp[i - 1][j], dp[i][j -
                    // 1]), dp[i - 1][j - 1]) + 1 : 0;
                    dp[j] = matrix[i][j] == '1' ? Math.min(Math.min(dp[j], dp[j - 1]), pre) + 1 : 0;
                }
                result = Math.max(result, dp[j]);
                pre = sz; // dp[i - 1][j - 1]
            }
        }

        return result * result;
    }
}