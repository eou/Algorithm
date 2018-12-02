// 329. Longest Increasing Path in a Matrix
class Solution {
    // dfs with memoization，时间复杂度为 O(mn)，如果不用 memo 会 TLE
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        
        int[][] memo = new int[matrix.length][matrix[0].length];
        int longest = 1;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                longest = Math.max(longest, helper(matrix, i, j, memo));
            }
        }
        
        return longest;
    }
    
    public int helper(int[][] matrix, int i, int j, int[][] memo) {
        if(memo[i][j] != 0) {
            return memo[i][j];
        }
        
        int longest = 1;
        for(int k = 0; k < 4; k++) {
            int x = dx[k] + i;
            int y = dy[k] + j;
            if(x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length) {
                if(matrix[x][y] > matrix[i][j]) {
                    longest = Math.max(longest, 1 + helper(matrix, x, y, memo));
                } else {
                    continue;
                }
            }
        }
        
        memo[i][j] = longest;
        return longest;
    }
}

class Solution {
    // topological sort
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int len = 0;
        int visitedNum = 0;
        while (visitedNum < matrix.length * matrix[0].length) {
            Set<int[]> set = new HashSet<>();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == Integer.MIN_VALUE) {
                        continue;
                    }
                    if (isPeak(matrix, i, j)) {
                        set.add(new int[] { i, j });
                    }
                }
            }

            for (int[] point : set) {
                matrix[point[0]][point[1]] = Integer.MIN_VALUE;
                visitedNum++;
            }
            len++;
        }

        return len;
    }

    private boolean isPeak(int[][] matrix, int i, int j) {
        boolean up = (i == 0 || matrix[i][j] >= matrix[i - 1][j]);
        boolean bottom = (i == matrix.length - 1 || matrix[i][j] >= matrix[i + 1][j]);
        boolean left = (j == 0 || matrix[i][j] >= matrix[i][j - 1]);
        boolean right = (j == matrix[0].length - 1 || matrix[i][j] >= matrix[i][j + 1]);

        if (up && bottom && left && right) {
            return true;
        } else {
            return false;
        }
    }
}