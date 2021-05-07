// 57. Insert Interval
class Solution {
    // 跟 56. Merge Intervals 一个思路
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> results = new ArrayList<>();
        if(intervals.size() == 0) {
            results.add(newInterval);
            return results;
        }
        
        intervals.add(newInterval);
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        for(Interval i : intervals) {
            if(i.start <= end) {
                if(i.end > end) {
                    end = i.end;
                }
            } else {
                results.add(new Interval(start, end));
                start = i.start;
                end = i.end;
            }
        }
        
        // 注意最后要 add 一次
        results.add(new Interval(start, end));
        
        return results;
    }
}

class Solution {
    // 只 merge newInterval 的版本
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        int i = 0;

        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            i++;
        }

        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            newInterval = new Interval(Math.min(intervals.get(i).start, newInterval.start),
                    Math.max(intervals.get(i).end, newInterval.end));
            intervals.remove(i);
        }

        intervals.add(i, newInterval);
        return intervals;
    }
}

// Binary Search, still O(n) !!!
class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<>();
        if (intervals == null || newInterval == null) {
            return result;
        }
            
        int iStart = findStartPos(intervals, newInterval.start);
        int iEnd = findEndPos(intervals, newInterval.end);
        if (iStart > 0 && intervals.get(iStart - 1).end >= newInterval.start) {
            iStart--;
        }
            
        if (iEnd == intervals.size() || intervals.get(iEnd).start > newInterval.end) {
            iEnd--;
        }
            

        // If not in the corner cases, this condition should apply.
        if (iStart <= iEnd) {
            newInterval = new Interval(Math.min(newInterval.start, intervals.get(iStart).start),
                    Math.max(newInterval.end, intervals.get(iEnd).end));
        }

        int i = 0;
        while (i < iStart) {
            result.add(intervals.get(i++));
        }
            
        result.add(newInterval);
        i = iEnd + 1;
        while (i < intervals.size()) {
            result.add(intervals.get(i++));
        }

        return result;
    }

    private int findStartPos(List<Interval> intervals, int value) {
        int l = 0, r = intervals.size() - 1;
        while (l <= r) {
            int m = (l + r) >> 1;
            if (intervals.get(m).start == value) {
                return m;
            } else if (intervals.get(m).start < value) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l;
    }

    private int findEndPos(List<Interval> intervals, int value) {
        int l = 0, r = intervals.size() - 1;
        while (l <= r) {
            int m = (l + r) >> 1;
            if (intervals.get(m).end == value) {
                return m;
            } else if (intervals.get(m).end < value) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l;
    }
}