class Solution {
    // DFS + BFS, 时间复杂度为 O(A)
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};
    public int shortestBridge(int[][] A) {
        if(A == null || A.length == 0) {
            return -1;
        }

        boolean[][] visited = new boolean[A.length][A[0].length];
        Deque<int[]> queue = new ArrayDeque<>();

        // dfs to find one island
        boolean foundOneIsland = false;
        for(int i = 0; i < A.length; i++) {
            if(foundOneIsland) {
                break;
            } else {
                for(int j = 0; j < A[0].length; j++) {
                    if(A[i][j] == 1) {
                        dfs(A, visited, i, j, queue);
                        foundOneIsland = true;
                        break;
                    }
                }
            }
        }

        // bfs to expand this island
        int step = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                for(int j = 0; j < 4; j++) {
                    int x = pos[0] + dx[j];
                    int y = pos[1] + dy[j];
                    if(x >= 0 && y >= 0 && x < A.length && y < A[0].length && !visited[x][y]) {
                        if(A[x][y] == 1) {
                            return step;
                        } else {
                            queue.offer(new int[]{x, y});
                            visited[x][y] = true;
                        }
                    }
                }
            }
            step++;
        }

        return -1;
    }

    private void dfs(int[][] A, boolean[][] visited, int x, int y, Deque<int[]> queue) {
        if(x < 0 || y < 0 || x >= A.length || y >= A[0].length || visited[x][y] || A[x][y] == 0) {
            return;
        }

        visited[x][y] = true;
        queue.offer(new int[]{x, y});
        for(int i = 0; i < 4; i++) {
            dfs(A, visited, x + dx[i], y + dy[i], queue);
        }
    }
}