// 489. Robot Room Cleaner
// DFS
class Solution {
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};
    public void cleanRoom(Robot robot) {
        // 0: up, 90: right, 180: down, 270: left
        dfs(robot, new HashSet<>(), 0, 0, 0);
    }
 
    public void dfs(Robot robot, Set<String> visited, int x, int y, int curDir) {
        // use 'x,y' mark visited nodes, where x,y are integers tracking the coordinates
        String key = x + "," + y;
        if (visited.contains(key)) {
            return;
        }

        visited.add(key);
        robot.clean();
 
        for (int i = 0; i < 4; i++) {
            // can go directly. Find the (x, y) for the next cell based on current direction
            if(robot.move()) {
                dfs(robot, visited, x + dx[curDir], y + dy[curDir], curDir);
                reset(robot);
            }
 
            // cannot move, turn to next direction to move
            robot.turnRight();
            curDir += 1;
            curDir %= 4;
        }
    }
 
    // go back to the starting position with starting direction
    private void reset(Robot robot) {
        robot.turnLeft();
        robot.turnLeft();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }
}