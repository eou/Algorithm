// 207. Course Schedule
// Topological sorting，此题只需判断有向图是否有环
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0].length == 0) {
            return true;
        }

        // 1. calculate indegree of vertex
        Map<Integer, Integer> map = new HashMap<>();
        // [1, 0] means 0 -> 1
        for (int[] pre : prerequisites) {
            int indegree = map.getOrDefault(pre[0], 0) + 1;
            map.put(pre[0], indegree);
        }

        // 2. Find the head vertex
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (map.getOrDefault(i, 0) == 0) {
                queue.offer(i);
            }
        }

        // BFS
        int num = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            num++;
            for (int[] pre : prerequisites) {
                if (pre[1] == course) {
                    // neighbor
                    map.put(pre[0], map.get(pre[0]) - 1);
                    if (map.get(pre[0]) == 0) {
                        queue.offer(pre[0]);
                    }
                }
            }
        }

        return num == numCourses;
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
            // find next course which can take
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

class Solution {
    // 邻接表 adjacency list 存图，时间复杂度为 O(V + E)，空间复杂度为 O(E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // edge cases
        if (numCourses <= 0 || prerequisites == null) {
            return false;
        }
        // have no dependent relations
        if (prerequisites.length == 0) {
            return true;
        }

        List<List<Integer>> graph = new ArrayList<>(); // adjecent list
        for (int i = 0; i < numCourses; ++i) {
            graph.add(new ArrayList<>());
        }

        int[] indegrees = new int[numCourses]; // count each nodes' indegrees
        for (int i = 0; i < prerequisites.length; ++i) {
            // pre => cur
            int pre = prerequisites[i][1];
            int cur = prerequisites[i][0];
            ++indegrees[cur];
            graph.get(pre).add(cur); // graph.get(pre): point to next node
        }

        Deque<Integer> queue = new ArrayDeque<>();
        // add all start nodes which have no indegrees
        for (int i = 0; i < numCourses; ++i) {
            if (indegrees[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            --numCourses;
            for (int i = 0; i < graph.get(cur).size(); ++i) {
                int next = graph.get(cur).get(i);
                --indegrees[next];
                if (indegrees[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return numCourses == 0;
    }
}