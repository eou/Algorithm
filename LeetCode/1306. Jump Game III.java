// 1306. Jump Game III
// BFS
class Solution {
    public boolean canReach(int[] arr, int start) {
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int j = queue.poll();

                if (j - arr[j] >= 0) {
                    if (arr[j - arr[j]] == 0) {
                        return true;
                    }
                    if (!visited.contains(j - arr[j])) {
                        queue.offer(j - arr[j]);
                        visited.add(j - arr[j]);
                    }
                }

                if (j + arr[j] < arr.length) {
                    if (arr[j + arr[j]] == 0) {
                        return true;
                    }
                    if (!visited.contains(j + arr[j])) {
                        queue.offer(j + arr[j]);
                        visited.add(j + arr[j]);
                    }
                }
            }
        }

        return false;
    }
}

// DFS
class Solution {
    private Set<Integer> visited = new HashSet<>();
    public boolean canReach(int[] arr, int start) {
        if (start >= 0 && start < arr.length && !visited.contains(start)) {
            visited.add(start);
            return arr[start] == 0 || canReach(arr, start + arr[start]) || canReach(arr, start - arr[start]);
        }
        return false;
    }
}

class Solution {
    public boolean canReach(int[] arr, int start) {
        if (start >= 0 && start < arr.length) {
            int steps = arr[start];
            arr[start] += arr.length;
            return steps == 0 || canReach(arr, start + steps) || canReach(arr, start - steps);
        }

        return false;
    }
}