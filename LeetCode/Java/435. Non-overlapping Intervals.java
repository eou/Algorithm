// 435. Non-overlapping Intervals
class Solution {
    public int eraseOverlapIntervals(Interval[] intervals) {
        if(intervals == null || intervals.length < 2) {
            return 0;
        }
        
        int overlap = 0;
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                if(a.start == b.start) {
                    return a.end - b.end;
                } else {
                    return a.start - b.start;
                }
            }
        });
        
        int last = 0;
        int curr = 1;
        while(curr < intervals.length) {
            if(intervals[curr].start < intervals[last].end) {
                overlap++;
                if(intervals[curr].end < intervals[last].end) {
                    last = curr;
                }
            } else {
                last = curr;
            }
            curr++;
        }
        
        return overlap;
    }
}

// This problem is the same as "Given a collection of intervals, find the maximum number of intervals that are non-overlapping." 
// The classic Greedy problem: Interval Scheduling
class Solution {
    public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals == null || intervals.length < 2) {
            return 0;
        }

        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {
                return a.end - b.end;
            }
        });

        int end = intervals[0].end;
        int count = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start >= end) {
                end = intervals[i].end;
                count++;
            }
        }
        return intervals.length - count;
    }
}