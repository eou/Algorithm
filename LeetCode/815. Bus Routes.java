// 815. Bus Routes
class Solution {
    // BFS，时间复杂度为 O(N^2), N 为公交车个数
    public int numBusesToDestination(int[][] routes, int S, int T) {
        if(routes == null || routes.length == 0 || S == T) {
            return 0;
        }

        // stop => buses
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i = 0; i < routes.length; i++) {
            for(int j = 0; j < routes[i].length; j++) {
                map.putIfAbsent(routes[i][j], new ArrayList<>());
                map.get(routes[i][j]).add(i);
            }
        }

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(S);
        int num = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            num++;
            for(int i = 0; i < size; i++) {
                int start = queue.poll();
                List<Integer> buses = map.get(start);
                for(int bus : buses) {
                    // if(!visited.add(bus)) {
                    //     continue;
                    // }
                    if(visited.contains(bus)) {
                        continue;
                    }
                    visited.add(bus);
                    for(int j = 0; j < routes[bus].length; j++) {
                        if(routes[bus][j] == T) {
                            return num;
                        }
                        queue.offer(routes[bus][j]);
                    }
                }
            }
        }

        return -1;
    }
}