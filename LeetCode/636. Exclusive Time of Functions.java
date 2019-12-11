// 636. Exclusive Time of Functions
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[n];

        String[] s = logs.get(0).split(":");
        int id = Integer.parseInt(s[0]);
        int timestamp = Integer.parseInt(s[2]);

        stack.push(id);
        int preTime = timestamp;

        for(int i = 1; i < logs.size(); i++) {
            s = logs.get(i).split(":");
            id = Integer.parseInt(s[0]);
            timestamp = Integer.parseInt(s[2]);

            if(s[1].equals("start")) {
                if(!stack.isEmpty()) {
                    // start of timestamp
                    result[stack.peek()] += (timestamp - preTime);
                }
                stack.push(id);
                preTime = timestamp;
            } else {
                // end of timestamp
                result[stack.peek()] += (timestamp - preTime + 1);
                stack.pop();
                preTime = timestamp + 1;
            }
        }

        return result;
    }
}