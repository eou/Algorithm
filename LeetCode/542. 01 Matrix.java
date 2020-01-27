// 542. 01 Matrix
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int[][] dist = new int[matrix.length][matrix[0].length];
        for (int[] d : dist) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        
        Deque<int[]> queue = new ArrayDeque<>();
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    // start from all 0
                    dist[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] dir : dirs) {
                int newx = cur[0] + dir[0];
                int newy = cur[1] + dir[1];
                if (newx >= 0 && newx < matrix.length && newy >= 0 && newy < matrix[0].length) {
                    if (dist[newx][newy] > dist[cur[0]][cur[1]] + 1) {
                        dist[newx][newy] = dist[cur[0]][cur[1]] + 1;
                        queue.offer(new int[]{newx, newy});
                    }
                }
            }
        }
        
        return dist;
    }
}

// dp
// compare with left and top neighbor first, then compare bottom and right neighbors
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int[] r : res) {
            Arrays.fill(r, Integer.MAX_VALUE - 1);
        }

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if (matrix[i][j] == 0) {
                    res[i][j] = 0;
                } else {
                    if (i > 0) {
                        // Integer.MAX_VALUE + 1 < 0 !!!
                        res[i][j] = Math.min(res[i][j], res[i - 1][j] + 1);
                    }
                    if (j > 0) {
                        res[i][j] = Math.min(res[i][j], res[i][j - 1] + 1);
                    }
                }
            }
        }

        for (int i = matrix.length - 1; i >= 0; --i) {
            for (int j = matrix[0].length - 1; j >= 0; --j) {
                if (res[i][j] != 0 && res[i][j] != 1) {
                    if (i < matrix.length - 1) {
                        res[i][j] = Math.min(res[i][j], res[i + 1][j] + 1);
                    }
                    if (j < matrix[0].length - 1) {
                        res[i][j] = Math.min(res[i][j], res[i][j + 1] + 1);
                    }
                }
            }
        }

        return res;
    }
}