public class Solution {
    /**
     * @param grid: a 2D integer grid
     * @return: an integer
     */
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, 1, -1};
    public int zombie(int[][] grid) {
        Deque<int[]> queue = new ArrayDeque<>();
        int human = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 0) {
                    human++;
                }
            }
        }

        int res = 0;
        Set<int[]> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            res++;
            for (int i = 0; i < size; i++) {
                int[] zb = queue.poll();
                visited.add(zb);
                for (int j = 0; j < 4; j++) {
                    int x = zb[0] + dx[j];
                    int y = zb[1] + dy[j];
                    if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                        int[] infected = new int[]{x, y};
                        if (grid[x][y] == 0 && !visited.contains(infected)) {
                            human--;
                            grid[x][y] = 1;
                            queue.offer(infected);
                        }
                    }
                }
            }
        }

        return human == 0 ? res - 1 : -1;
    }
}