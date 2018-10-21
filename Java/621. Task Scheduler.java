// 621. Task Scheduler
// 与 358 大同小异，双数组，时间复杂度O(n)
class Solution {
    public int leastInterval(char[] tasks, int n) {
        if(tasks.length == 0) {
            return 0;
        }
        
        int step = 0;
        int[] count = new int[26];
        int[] next = new int[26];
        for(Character c : tasks) {
            count[c - 'A']++;
        }
        
        int i = 0;
        while(i < tasks.length) {
            int index = findNextTask(count, next, step);
            // 注意这里与358不同，细节调整一下
            if(index != -1) {
                count[index]--;
                next[index] = step + n + 1;
                i++;
            }
            step++;
        }
        
        return step;
    }
    
    private int findNextTask(int[] count, int[] next, int index) {
        int result = -1;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < 26; i++) {
            if(count[i] > 0 && count[i] > max && index >= next[i]) {
                max = count[i];
                result = i;
            }
        }
        return result;
    }
}