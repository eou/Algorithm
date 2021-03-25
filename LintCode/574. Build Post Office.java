// 574. Build Post Office
// BFS, O(n^4), TLE
public class Solution {
    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int[][] dis = new int[grid.length][grid[0].length];

        // 1. Find every house's position
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    // 2. Start from each house, calculate the distance between house and empty place
                    bfs(dis, grid, new int[]{i, j});
                }
            }
        }

        // 3. Find the smallest distance
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // we can confirm that all distances are larger than 0
                if (grid[i][j] == 0) {
                    res = Math.min(res, dis[i][j]);
                }
            }
        }

        return res;
    }

    private int[] dx = {1, -1, 0, 0};
    private int[] dy = {0, 0, -1, 1};
    private void bfs(int[][] dis, int[][] grid, int[] house) {
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(house);

        Set<Integer> visited = new HashSet<>();
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                dis[cur[0]][cur[1]] += step;

                for (int j = 0; j < 4; j++) {
                    int x = cur[0] + dx[j];
                    int y = cur[1] + dy[j];
                    int[] next = new int[]{x, y};

                    if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                        if (!visited.contains(mapping(grid, next))) {
                            queue.offer(next);
                            // This node has visited here! 
                            // Cannot wait until poll since many duplicate nodes might be added into queue already
                            visited.add(mapping(grid, next));
                        }
                    }
                }
            }

            step++;
        }
    }

    private int mapping(int[][] grid, int[] pos) {
        return pos[0] * grid[0].length + pos[1];
    }
}

// Key point: distance = x distance + y distance, O(n^2)
public class Solution {
    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int[] xHouse = new int[grid.length];
        int[] yHouse = new int[grid[0].length];

        // 1. Find every house's position
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    xHouse[i]++;
                    yHouse[j]++;
                }
            }
        }

        // 2. Calculate the distance between house and empty place
        int[] xDis = new int[grid.length];
        int[] yDis = new int[grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                xDis[i] += xHouse[j] * Math.abs(j - i);
            }
        }

        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                yDis[i] += yHouse[j] * Math.abs(j - i);
            }
        }

        int[][] dis = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    dis[i][j] += xDis[i] + yDis[j];
                }
            }
        }

        // 3. Find the smallest distance
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // we can confirm that all distances are larger than 0
                if (grid[i][j] == 0) {
                    res = Math.min(res, dis[i][j]);
                }
            }
        }

        return res;
    }
}