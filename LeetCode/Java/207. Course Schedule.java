// 207. Course Schedule
// Topological sorting，此题只需判断有向图是否有环
class Solution {
    // 邻接表 adjacency list，时间复杂度为 O(V + E)，空间复杂度为 O(E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0 || prerequisites == null) {
            return false;
        }
        if (prerequisites.length == 0) {
            return true;
        }

        int[] inDegree = new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            inDegree[prerequisites[i][0]]++;
            graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        // BFS
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0)
                queue.offer(i);
        }

        while (!queue.isEmpty()) {
            int course = queue.poll();
            numCourses--;
            for (int i : graph.get(course)) {
                inDegree[i]--;
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
        }
        return numCourses == 0;
    }
}

class Solution {
    // 可以不用存图，点之间的关系可以直接遍历寻找
    // 从图的尾节点逆向遍历和从起点遍历是一个效果
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        // count the prerequisites of each courses, [0, 1] means 1 => 0
        for (int[] courses : prerequisites) {
            ++indegrees[courses[0]];
        }

        // BFS
        Deque<Integer> queue = new ArrayDeque<>();
        // if some courses do not need prerequisites, they can be the start nodes of the graph
        for (int i = 0; i < indegrees.length; ++i) {
            if (indegrees[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int course = queue.poll();
            --numCourses;
            for (int[] courses : prerequisites) {
                if (courses[1] == course) {
                    --indegrees[courses[0]];
                    if (indegrees[courses[0]] == 0) {
                        queue.offer(courses[0]); // next start
                    }
                }
            }
        }

        return numCourses == 0;
    }
}