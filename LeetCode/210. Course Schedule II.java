// 210. Course Schedule II
// Topological sort
// BFS
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            res[i] = i;
        }
        if (numCourses == 0 || prerequisites == null || prerequisites.length == 0 || prerequisites[0].length == 0) {
            return res;
        }

        // 1. indegree
        int[] indegree = new int[numCourses];
        for (int[] pre : prerequisites) {
            indegree[pre[0]]++;
        }

        // 2. first course without any pres
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int num = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            res[num++] = course;
            for (int[] pre : prerequisites) {
                if (pre[1] == course) {
                    indegree[pre[0]]--;
                    if (indegree[pre[0]] == 0) {
                        queue.offer(pre[0]);
                    }
                }
            }
        }

        if (num == numCourses) {
            return res;
        }

        return new int[0];
    }
}

// Don't have to build graph
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses == 0 || prerequisites == null) {
            return new int[0];
        }

        // Build graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            graph.add(new ArrayList<>());
        }

        int[] indegrees = new int[numCourses];
        for (int i = 0; i < prerequisites.length; ++i) {
            // pre => cur
            int cur = prerequisites[i][0];
            int pre = prerequisites[i][1];
            ++indegrees[cur];
            graph.get(pre).add(cur);
        }

        int[] order = new int[numCourses];
        // BFS
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; ++i) {
            // add all start nodes which indegrees are 0
            if (indegrees[i] == 0) {
                queue.offer(i);
            }
        }

        int index = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            order[index++] = cur;
            for (int i = 0; i < graph.get(cur).size(); ++i) {
                int next = graph.get(cur).get(i);
                --indegrees[next];
                if (indegrees[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return index == numCourses ? order : new int[0];
    }
}