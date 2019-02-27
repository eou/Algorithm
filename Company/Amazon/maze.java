class maze {
    public static boolean bfs(int[][] maze, int startx, int starty) {
        if (maze == null)
            return false;
        if (maze.length < 1 || maze[0].length < 1)
            return false;

        LinkedList<Point> que = new LinkedList<Point>();

        Point p1 = new Point(startx, starty, maze[startx][starty]);
        que.offer(p1);

        int width = maze[0].length;
        int height = maze.length;
        p1 = que.poll();

        while (p1.val != 9) {
            int x = p1.x;
            int y = p1.y;
            maze[x][y] = -1;

            if ((x + 1) < height) {
                if (maze[x + 1][y] > 0) {
                    Point p2 = new Point(x + 1, y, maze[x + 1][y]);
                    que.offer(p2);
                }
            }

            if ((x - 1) >= 0) {
                if (maze[x - 1][y] > 0) {
                    Point p2 = new Point(x - 1, y, maze[x - 1][y]);
                    que.offer(p2);
                }
            }

            if ((y + 1) < width) {
                if (maze[x][(y + 1)] > 0) {
                    Point p2 = new Point(x, (y + 1), maze[x][(y + 1)]);
                    que.offer(p2);
                }
            }

            if ((y - 1) >= 0) {
                if (maze[x][(y - 1)] > 0) {
                    Point p2 = new Point(x, (y - 1), maze[x][(y - 1)]);
                    que.offer(p2);
                }
            }
                
            if (que.isEmpty()) {
                break;
            } else
                p1 = que.poll();
        }

        return p1.val == 9;
        // if (p1.val == 9)
        //     return true;
        // else
        //     return false;
    }

    public static boolean dfs(int[][] m, int i, int j, int w, int h) {
        if (i > h - 1 || j > w - 1 || i < 0 || j < 0 || m[i][j] == 0 || m[i][j] == 2)
            return false;
        if (m[i][j] == 9) {
            return true;
        }
        m[i][j] = 2;
        boolean left = dfs(m, i, j - 1, w, h);
        boolean right = dfs(m, i, j + 1, w, h);
        boolean up = dfs(m, i - 1, j, w, h);
        boolean down = dfs(m, i + 1, j, w, h);
        m[i][j] = 1;
        return left || right || up || down;
    }

    public static boolean solution(int[][] m) {
        if (m == null || m.length == 0 || m[0][0] == 0)
            return false;
        int w = m[0].length, h = m.length;
        return dfs(m, 0, 0, w, h);
    }
}