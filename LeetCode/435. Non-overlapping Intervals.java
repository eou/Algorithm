// 435. Non-overlapping Intervals
// This problem is the same as "Given a collection of intervals, find the maximum number of intervals that are non-overlapping." 
// The classic Greedy problem: Interval Scheduling
// Greedy, everytime choose earliest endtime interval, we can have most non-overlapping interval
// Proof by contradict. 
// If A is not the optimal (most non-overlapping), assume A' is better, but they have k-1 same intervals
// A[k] is different with A'[k], thus A[k]'s endtime is earlier than A'[k] (from greedy)
// thus A'' = A' - A'[k] + A[k] is also one of best answer
// but A'' have k same intervals with A, contradict with assumption: k-1 same intervals
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (int[] interval : intervals) {
            heap.offer(interval);
        }

        int res = 0;
        int[] pre = heap.poll();
        while (!heap.isEmpty()) {
            int[] cur = heap.poll();
            if (cur[0] < pre[1]) {
                res++;
            } else {
                pre = cur;
            }
        }

        return res;
    }
}

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return 0;
        }

        int res = 0;
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int pre = 0, cur = 1;
        while (cur < intervals.length) {
            if (intervals[cur][0] < intervals[pre][1]) {
                res++;
            } else {
                pre = cur;
            }
            cur++;
        }

        return res;
    }
}

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return 0;
        }

        // sort by endtime
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int end = intervals[0][1];
        int num = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= end) {
                end = intervals[i][1];
                num++;
            }
        }
        return intervals.length - num;
    }
}