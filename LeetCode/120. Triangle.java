// 120. Triangle
// row numbers == column numbers
// DFS, TLE
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        return dfs(triangle, 0, Integer.MAX_VALUE, 0, 0);
    }

    public int dfs(List<List<Integer>> triangle, int sum, int min, int col, int row) {
        sum += triangle.get(row).get(col);

        if (row == triangle.size() - 1) {
            return Math.min(min, sum);
        }

        return Math.min(dfs(triangle, sum, min, col, row + 1), dfs(triangle, sum, min, col + 1, row + 1));
    }
}

// DP, space O(n^2)
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int[][] dp = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];
        // coordinate transform
        // triangle.get(i).get(j) can come from triangle.get(i).get(j) or triangle.get(i).get(j - 1)
        for (int i = 0; i < triangle.size(); i++) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                int left, right;
                if (i == 0) {
                    left = 0;
                    right = 0;
                } else {
                    if (j == 0) {
                        left = Integer.MAX_VALUE;
                    } else {
                        left = dp[i - 1][j - 1];
                    }
                    if (j == triangle.get(i).size() - 1) {
                        right = Integer.MAX_VALUE;
                    } else {
                        right = dp[i - 1][j];
                    }
                }
                dp[i][j] = triangle.get(i).get(j) + Math.min(left, right);
            }
        }

        int res = dp[triangle.size() - 1][0];
        for (int i = 0; i < triangle.get(triangle.size() - 1).size(); i++) {
            res = Math.min(res, dp[triangle.size() - 1][i]);
        }
        return res;
    }
}

// DP, space O(n)
class Solution {
    // 滚动数组
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] prev = new int[triangle.get(triangle.size() - 1).size()];
        int[] curr = new int[triangle.get(triangle.size() - 1).size()];
        for (int i = 0; i < triangle.size(); i++) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                int left, right;
                if (i == 0) {
                    left = 0;
                    right = 0;
                } else {
                    if (j == 0) {
                        left = Integer.MAX_VALUE;
                    } else {
                        left = prev[j - 1];
                    }
                    if (j == triangle.get(i).size() - 1) {
                        right = Integer.MAX_VALUE;
                    } else {
                        right = prev[j];
                    }
                }
                curr[j] = triangle.get(i).get(j) + Math.min(left, right);
            }
            System.arraycopy(curr, 0, prev, 0, curr.length);
            // prev = curr; // incorrect
        }

        int res = curr[0];
        for (int i = 0; i < triangle.get(triangle.size() - 1).size(); i++) {
            res = Math.min(res, curr[i]);
        }
        return res;
    }
}

// from bottom to top
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size() + 1];
        for (int i = triangle.size() - 1; i >= 0; i--)
            for (int j = 0; j < triangle.get(i).size(); j++)
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
        return dp[0];
    }
}