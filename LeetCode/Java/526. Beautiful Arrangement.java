// 526. Beautiful Arrangement
class Solution {
    int count = 0;
    public int countArrangement(int n) {
        boolean[] visited = new boolean[n + 1];
        helper(n, 1, visited);
        return count;
    }

    private void helper(int n, int start, boolean[] visited) {
        if(start > n) {
            count++;
        }

        for(int i = 1; i <= n; i++) {
            if(!visited[i] && (start % i == 0 || i % start == 0)) {
                visited[i] = true;
                helper(n, start + 1, visited);
                visited[i] = false;
            }
        }
    }
}