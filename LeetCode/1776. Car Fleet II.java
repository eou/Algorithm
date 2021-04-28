// 1776. Car Fleet II
// If my speed is slower than the speed of the right side car, not only mine but also all cars behind (at the left of) me will NEVER be able to catch/collide with the previous car. 
// Such that we can throw it away.
// A car collision is only related with the car on the right side
// Stack, O(n)
class Solution {
    public double[] getCollisionTimes(int[][] A) {
        int n = A.length;
        
        // monotonic stack, maintain a complete car fleet in stack, throw the fastest car away
        Deque<Integer> stack = new ArrayDeque<>();
        double[] res = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            res[i] = -1.0;

            int pos = A[i][0], speed = A[i][1];
            while (!stack.isEmpty()) {
                int j = stack.peek();
                int p2 = A[j][0], s2 = A[j][1];
                // 1. s2 >= speed, impossible to chase
                // 2. s2 < speed, but it takes too much time to chase
                // thus in this way we don't care next car, just pop them
                // to see if we can collide next next car and etc.
                if (speed <= s2 || (res[j] > 0 && 1.0 * (p2 - pos) / (speed - s2) >= res[j])) {
                    stack.pop();
                } else {
                    break;
                }
            }
            
            // s2 < speed and time is enough, find a car we can collide in stack
            if (!stack.isEmpty()) {
                int j = stack.peek();
                int p2 = A[j][0], s2 = A[j][1];
                res[i] = 1.0 * (p2 - pos) / (speed - s2);
            }

            stack.push(i);
        }

        return res;
    }
}