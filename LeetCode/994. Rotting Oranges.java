// 994. Rotting Oranges
// bfs, start from fresh oranges
class Solution {
    public int orangesRotting(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    int time = bfs(grid, i, j);
                    if (time == 0) {
                        // impossible
                        return -1;
                    }
                    res = Math.max(res, time);
                }
            }
        }
        return res;
    }

    public int bfs(int[][] grid, int x, int y) {
        int[] dirx = new int[] { -1, 0, 0, 1 };
        int[] diry = new int[] { 0, -1, 1, 0 };
        int res = 0;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { x, y });
        Set<Integer> set = new HashSet<>();
        while (!queue.isEmpty()) {
            res++;
            int size = queue.size();
            while (size > 0) {
                size--;
                int[] cur = queue.poll();
                // 4 directions
                for (int i = 0; i < 4; i++) {
                    int newx = cur[0] + dirx[i];
                    int newy = cur[1] + diry[i];
                    if (newx >= 0 && newx < grid.length && newy >= 0 && newy < grid[0].length
                            && !set.contains(newx * grid[0].length + newy)) {
                        if (grid[newx][newy] == 2) {
                            // nearest rotten orange
                            return res;
                        } else if (grid[newx][newy] == 1) {
                            queue.offer(new int[] { newx, newy });
                            set.add(newx * grid[0].length + newy);
                        }
                    }
                }
            }
        }

        return 0;
    }
}

// bfs, start from rotten oranges
class Solution {
    public int orangesRotting(int[][] grid) {
        int freshOrange = 0;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    freshOrange++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[] { i, j });
                }
            }
        }

        // bfs, start from all rotten orange
        int time = 0;
        int res = 0;
        int[] dirx = new int[] { -1, 0, 0, 1 };
        int[] diry = new int[] { 0, 1, -1, 0 };
        while (!queue.isEmpty()) {
            int size = queue.size();
            time++;
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                for (int j = 0; j < 4; j++) {
                    int newx = cur[0] + dirx[j];
                    int newy = cur[1] + diry[j];
                    if (newx >= 0 && newx < grid.length && newy >= 0 && newy < grid[0].length) {
                        if (grid[newx][newy] == 1) {
                            freshOrange--;
                            grid[newx][newy] = 2;
                            res = time;
                            queue.offer(new int[] { newx, newy });
                        }
                    }
                }
            }
        }

        return freshOrange == 0 ? res : -1;
    }
}

// bfs, use O(n^2) space, a little faster
class Solution {
    public int orangesRotting(int[][] grid) {
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        int[][] dis = new int[grid.length][grid[0].length];

        int[] dx = new int[] { -1, 0, 0, 1 };
        int[] dy = new int[] { 0, -1, 1, 0 };

        // init
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] { i, j });
                    dis[i][j] = 0;
                } else {
                    dis[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newx = cur[0] + dx[i];
                int newy = cur[1] + dy[i];
                if (newx >= 0 && newx < grid.length && newy >= 0 && newy < grid[0].length) {
                    if (grid[newx][newy] == 1 && dis[newx][newy] > dis[cur[0]][cur[1]] + 1) {
                        dis[newx][newy] = dis[cur[0]][cur[1]] + 1;
                        queue.offer(new int[] { newx, newy }); // infected
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    res = Math.max(res, dis[i][j]);
                }
            }
        }
        if (res == grid.length * grid[0].length || res == Integer.MAX_VALUE) {
            res = -1;
        }
        return res;
    }
}

// dfs
class Solution {
    public boolean possible = true;

    public int orangesRotting(int[][] grid) {
        int res = dfs(grid);
        return possible ? res : -1;
    }

    public int dfs(int[][] grid) {
        boolean exit = true;
        boolean next = false;
        int[] dx = new int[] { -1, 0, 0, 1 };
        int[] dy = new int[] { 0, 1, -1, 0 };
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    boolean neighborRotten = false;
                    for (int k = 0; k < 4; k++) {
                        int x = i + dx[k];
                        int y = j + dy[k];
                        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                            if (grid[x][y] == 2) {
                                neighborRotten = true;
                            }
                        }
                    }
                    if (neighborRotten) {
                        grid[i][j] = -1; // mark
                        exit = false;
                    }
                }
            }
        }
        // only infect fresh oranges whose neighbors are rotten oranges
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == -1) {
                    grid[i][j] = 2;
                }
                if (grid[i][j] == 1) {
                    next = true;
                }
            }
        }

        if (exit) {
            if (next) {
                possible = false;
            }
            return 0;
        }

        return 1 + dfs(grid);
    }
}

// bfs without queue
class Solution {
    public int orangesRotting(int[][] grid) {
        int fresh = 0, time = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 1) {
                    ++fresh;
                }
            }
        }
            
        for (int old_fresh = fresh; fresh > 0; ++time, old_fresh = fresh) {
            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[i].length; ++j) {
                    // start from 2 (rotten orange), time = 0
                    // rotten orange from 1st level is 3 (0 + 3), will infect neighbor in time 1 
                    // nth level rotten orange will infect neighbor next time
                    if (grid[i][j] == time + 2) {
                        fresh -= rot(grid, i + 1, j, time) + rot(grid, i - 1, j, time) + rot(grid, i, j + 1, time) + rot(grid, i, j - 1, time);
                    }     
                }
            }
            
            // no infect this loop, but still have fresh oranges, impossible
            if (fresh == old_fresh) {
                return -1;
            }
        }
        
        return time;
    }
    
    // check if it is fresh
    private int rot(int[][] g, int i, int j, int time) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[i].length || g[i][j] != 1) {
            return 0;
        }
        
        // find a fresh orange
        g[i][j] = time + 3;
        return 1;
    }
}