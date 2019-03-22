// 210. Course Schedule II
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