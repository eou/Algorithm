// 573. Build Post Office II
// BFS
public class Solution {
    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    ans = Math.min(ans, bfs(grid, i, j));
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private int bfs(int[][] grid, int sx, int sy) {
        Deque<int[]> queue = new ArrayDeque<>();
        // Array is better than set!
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        int dist = 0;
        int sum = 0;

        queue.offer(new int[]{sx, sy});
        visited[sx][sy] = true;
        while (!queue.isEmpty()) {
            dist++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                for (int k = 0; k < 4; k++) {
                    int nx = pos[0] + dx[k];
                    int ny = pos[1] + dy[k];
                    if (0 <= nx && nx < grid.length && 0 <= ny && ny < grid[0].length && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        if (grid[nx][ny] == 1) {
                            sum += dist;
                        }
                        if (grid[nx][ny] == 0) {
                            queue.offer(new int[]{nx, ny});
                        }
                    }
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // can't reach all houses
                if (grid[i][j] == 1 && !visited[i][j]) {
                    return Integer.MAX_VALUE;
                }
            }
        }
        return sum;
    }
}