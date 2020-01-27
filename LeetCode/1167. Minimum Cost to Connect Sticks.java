// 1167. Minimum Cost to Connect Sticks
// https://leetcode.com/discuss/interview-question/344677
// min heap
class Solution {
    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int stick : sticks) {
            pq.offer(stick);
        }
        
        int res = 0;
        while (pq.size() > 1) {
            int x = pq.poll();
            int y = pq.poll();
            res += (x + y);
            pq.offer(x + y);
        }
        return res;
    }
}

// use one array representing two set
class Solution {
    int left = 0, right = 0, numSticks, numResults = 0, num;
    // Gets the minimum from InputSet and ResultSet
    private boolean getMin(int[] sticks) {
        // check if there are numbers available from InputSet and ResultSet
        boolean f = right < numSticks, s = left < numResults;
        // If number is available from both sets, choose the smallest
        if (f && s) {
            num = (sticks[left] <= sticks[right]) ? sticks[left++] : sticks[right++];
        }   
        // If number is available from InputSet only
        else if (f) {
            num = sticks[right++];
        }   
        // If number is available from ResultSet only
        else if (s) {
            num = sticks[left++];
        }
        return f || s; // Returns result saying if we could find a number from any one of the Sets.
    }

    public int connectSticks(int[] sticks) {
        numSticks = sticks.length;
        Arrays.sort(sticks); // Initial sort
        int result = 0, first, second; // Result and place holders to get the smallest two numbers.
        // Continue till you can get two numbers every time from the Sets.
        while (true) {
            if (!getMin(sticks)) {
                break;
            }
            first = num;
            if (!getMin(sticks)) {
                break;
            }
            second = num;
            result += first + second; // Store the sum back in the ResultSet
            sticks[numResults++] = first + second;
        }
        return result;
    }
}