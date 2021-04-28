// 1499. Max Value of Equation
// yi + yj + |xi - xj| => yi - xi + yj + xj
// for point j, find maximum yi - xi for previous point i with the restriction xj - xi <= k
// O(nlogn)
class Solution {
    public int findMaxValueOfEquation(int[][] points, int k) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(
                (a, b) -> (a.getKey() == b.getKey() ? a.getValue() - b.getValue() : b.getKey() - a.getKey()));

        int res = Integer.MIN_VALUE;
        for (int[] point : points) {
            while (!pq.isEmpty() && point[0] - pq.peek().getValue() > k) {
                pq.poll();
            }

            if (!pq.isEmpty()) {
                res = Math.max(res, pq.peek().getKey() + point[0] + point[1]);
            }

            pq.offer(new Pair<>(point[1] - point[0], point[0]));
        }

        return res;
    }
}

// Increasing queue, O(n)
class Solution {
    public int findMaxValueOfEquation(int[][] points, int k) {
        // (y(i) - x(i)) + (y(j) + x(j)) => Pair<y(i) - x(i), x(i)>
        Deque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        int res = Integer.MIN_VALUE;
        for (int[] point : points) {
            // x_i - x_j <= k
            while (!queue.isEmpty() && point[0] - queue.peekFirst().getValue() > k) {
                queue.poll();
            }

            if (!queue.isEmpty()) {
                res = Math.max(res, queue.peekFirst().getKey() + point[0] + point[1]);
            }
            
            // keep monotonic, remove tails, 
            while (!queue.isEmpty() && point[1] - point[0] > queue.peekLast().getKey()) {
                queue.pollLast();
            }

            queue.offer(new Pair<>(point[1] - point[0], point[0]));
        }

        return res;
    }
}
