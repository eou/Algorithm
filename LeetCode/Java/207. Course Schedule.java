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
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Deque<Integer> queue = new ArrayDeque<>();

        for (int[] courses : prerequisites) {
            inDegree[courses[1]]++;
        }

        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            numCourses--;
            int course = queue.poll();
            for (int[] courses : prerequisites) {
                if (courses[0] == course) {
                    inDegree[courses[1]]--;
                    if (inDegree[courses[1]] == 0) {
                        queue.add(courses[1]);
                    }
                }
            }
        }

        return numCourses == 0;
    }
}