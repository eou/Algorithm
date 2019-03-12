// 210. Course Schedule II
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        // count the prerequisites of each courses
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

        int[] order = new int[numCourses];
        int orderIndex = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            --numCourses;
            order[orderIndex++] = course;
            for (int[] courses : prerequisites) {
                if (courses[1] == course) {
                    --indegrees[courses[0]];
                    if (indegrees[courses[0]] == 0) {
                        queue.offer(courses[0]); // next start
                    }
                }
            }
        }

        return numCourses == 0 ? order : new int[0];
    }
}