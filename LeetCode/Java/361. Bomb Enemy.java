// 361. Bomb Enemy
// Space: O(n^2)
class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        // scan the grid for each column and each row
        // e.g. for each column, enemy cnt start from 0, increase when meet an enemy, back to 0 when meet a wall, then add enemy numbers to any empty cell in this column of this area (seperate by walls)
        int[][] num = new int[grid.length][grid[0].length];
        
        // scan rows
        for (int i = 0; i < grid.length; i++) {
            int start = 0;
            int end = 0;
            int enemy = 0;
            while (end < grid[i].length) {
                if (grid[i][end] == 'W') {
                    // update previous empty cell
                    for (int j = start; j < end; j++) {
                        if (grid[i][j] == '0') {
                            num[i][j] += enemy;
                        }
                    }
                    enemy = 0;
                    start = end;
                } else if (grid[i][end] == 'E') {
                    enemy++;
                }
                end++;
            }
            // reach end of this row, update last time
            for (int j = start; j < end; j++) {
                if (grid[i][j] == '0') {
                    num[i][j] += enemy;
                }
            }
        }
        
        for (int i = 0; i < grid[0].length; i++) {
            int start = 0;
            int end = 0;
            int enemy = 0;
            while (end < grid.length) {
                if (grid[end][i] == 'W') {
                    // update previous empty cell
                    for (int j = start; j < end; j++) {
                        if (grid[j][i] == '0') {
                            num[j][i] += enemy;
                        }
                    }
                    enemy = 0;
                    start = end;
                } else if (grid[end][i] == 'E') {
                    enemy++;
                }
                end++;
            }
            // reach end of this col, update last time
            for (int j = start; j < end; j++) {
                if (grid[j][i] == '0') {
                    num[j][i] += enemy;
                }
            }
        }
        
        int res = 0;
        // also can update during counting
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                res = Math.max(res, num[i][j]);
            }
        }
        
        return res;
    }
}

// Space O(n), 逐行遍历只需用一个变量保存行信息
class Solution {
    public int maxKilledEnemies(char[][] grid) {
        int m = grid.length;
        int n = m != 0 ? grid[0].length : 0;
        int result = 0;
        int rowhits = 0;
        int[] colhits = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // start or encounter walls for each row
                if (j == 0 || grid[i][j - 1] == 'W') {
                    rowhits = 0;
                    // count enemies start from this wall to next wall
                    for (int k = j; k < n && grid[i][k] != 'W'; k++)
                        rowhits += grid[i][k] == 'E' ? 1 : 0;
                }
                // start or encounter walls for each column
                if (i == 0 || grid[i - 1][j] == 'W') {
                    colhits[j] = 0;
                    for (int k = i; k < m && grid[k][j] != 'W'; k++)
                        colhits[j] += grid[k][j] == 'E' ? 1 : 0;
                }
                if (grid[i][j] == '0') {
                    result = Math.max(result, rowhits + colhits[j]);
                }
            }
        }
        return result;
    }
}