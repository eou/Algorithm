// 317. Shortest Distance from All Buildings
class Solution {
    // 暴力BFS，时间复杂度 O(m^2 * n^2)
    public int shortestDistance(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        // distance stores the total distances from each empty land to houses
        int[][] distance = new int[rows][cols];
        // reachable stores the number of houses which each empty land can get to
        int[][] reachable = new int[rows][cols];
        
        int houses = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 1) {
                    houses++;
                }
            }
        }
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 1) {
                    // if you cannot reach another house from one house in the grid, the position of target building does not exist
                    if(!helper(grid, distance, reachable, houses, i, j)) {
                        return -1;
                    }
                }
            }
        }
        
        int minDistance = Integer.MAX_VALUE;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 0 && reachable[i][j] == houses) {
                    minDistance = Math.min(minDistance, distance[i][j]);
                }
            }
        }
        
        return minDistance;
    }
    
    private boolean helper(int[][] grid, int[][] distance, int[][] reachable, int houses, int x, int y) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{x, y});
        boolean[][] visited = new boolean[rows][cols];
        
        int houseCount = 0;
        // simplify directions change
        int[] dx = new int[]{0, 0, 1, -1};
        int[] dy = new int[]{1, -1, 0, 0};
        
        int steps = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            for(int i = 0; i < size; i++) {
                int[] tmp = queue.poll();
                for(int j = 0; j < 4; j++) {
                    int r = tmp[0] + dx[j];
                    int c = tmp[1] + dy[j];
                    
                    if(inGrid(rows, cols, r ,c) && !visited[r][c]) {
                        visited[r][c] = true;
                        if(grid[r][c] == 0) {
                            distance[r][c] += steps;
                            reachable[r][c]++;
                            queue.offer(new int[]{r, c});
                        } else if(grid[r][c] == 1) {
                            houseCount++;
                        }
                    }
                }
            }
        }
        
        // prunning
        return houseCount == houses;
    }
    
    private boolean inGrid(int rows, int cols, int x, int y) {
        return x >=0 && y >=0 && x < rows && y < cols;
    }
}