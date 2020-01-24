import java.util.*;

class Solution {
    private static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public int minHours(List<List<Integer>> grid) {
        int people = 0;
        Queue<Point> queue = new ArrayDeque<>();
        for (int r = 0; r < grid.size(); r++) {
            for (int c = 0; c < grid.get(0).size(); c++) {
                if (grid.get(r).get(c) == 1) {
                    queue.add(new Point(r, c));
                } else {
                    people++;
                }
            }
        }

        if (people == 0) {
            return 0;
        }

        for (int time = 1; !queue.isEmpty(); time++) {
            for (int size = queue.size(); size > 0; size--) {
                Point zombie = queue.poll();
                for (int[] dir : dirs) {
                    int x = zombie.x + dir[0];
                    int y = zombie.y + dir[1];

                    if (isHuman(grid, r, c)) {
                        people--;
                        if (people == 0) {
                            return time;
                        }
                        grid.get(r).set(c, 1);
                        queue.add(new Point(x, y));
                    }
                }
            }
        }
        return -1;
    }

    private boolean isHuman(List<List<Integer>> grid, int r, int c) {
        return r >= 0 && r < grid.size() && c >= 0 && c < grid.get(0).size() && grid.get(r).get(c) != 1;
    }

    public static void main(String[] args) {

    }
}