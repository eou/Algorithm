// 1345. Jump Game IV
class Solution {
    private int[] visited;
    private Map<Integer, List<Integer>> dup;
    public int minJumps(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        
        visited = new int[arr.length];
        dup = new HashMap<>();
        
        for (int i = 0; i < arr.length; i++) {
            List<Integer> list = dup.getOrDefault(arr[i], new ArrayList<>());
            list.add(i);
            dup.put(arr[i], list);
        }

        return bfs(arr);
    }
    
    private int bfs(int[] arr) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            step++;
            for (int i = 0; i < size; i++) {
                int pos = queue.poll();
                if (pos > 0 && visited[pos - 1] == 0) {
                    visited[pos - 1] = step;
                    queue.offer(pos - 1);
                }
                if (pos + 1 < arr.length && visited[pos + 1] == 0) {
                    visited[pos + 1] = step;
                    queue.offer(pos + 1);
                    if (pos + 1 == arr.length - 1) {
                        return step;
                    }
                }
                if (dup.get(arr[pos]).size() != 0) {
                    List<Integer> list = dup.get(arr[pos]);
                    for (int j = list.size() - 1; j >= 0; j--) {
                        int next = list.get(j);
                        if (visited[next] == 0) {
                            visited[next] = step;
                            queue.offer(next);
                            if (next == arr.length - 1) {
                                return step;
                            }
                        }
                    }
                    // !!! Important improvement. Next time we don't need to scan all duplicate number again
                    list.clear();
                }
            }
        }
        
        return step;
    }
}

// bi-directional bfs
class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (arr == null || n <= 1) {
            return 0;
        }

        boolean[] visited = new boolean[n];
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            List<Integer> list = map.getOrDefault(arr[i], new ArrayList<>());
            list.add(i);
            map.put(arr[i], list);
        }

        Set<Integer> head = new HashSet<>(), tail = new HashSet<>();
        head.add(0);
        tail.add(n - 1);
        visited[0] = true;
        visited[n - 1] = true;

        int res = 0;
        while (!head.isEmpty() && !tail.isEmpty()) {
            // keep head set smaller, then do bfs in head set
            if (head.size() > tail.size()) {
                Set<Integer> temp = tail;
                tail = head;
                head = temp;
            }

            Set<Integer> next = new HashSet<>();
            for (int i : head) {
                int x = i + 1, y = i - 1;
                if (tail.contains(x) || tail.contains(y)) {
                    return res + 1;
                }
                if (x < n && !visited[x]) {
                    visited[x] = true;
                    next.add(x);
                }
                if (y >= 0 && !visited[y]) {
                    visited[y] = true;
                    next.add(y);
                }
                for (int z : map.get(arr[i])) {
                    if (z == i) {
                        continue;
                    }
                    if (tail.contains(z)) {
                        return res + 1;
                    }
                    if (!visited[z]) {
                        visited[z] = true;
                        next.add(z);
                    }
                }
            }
            head = next;
            res++;
        }
        return -1;
    }
}