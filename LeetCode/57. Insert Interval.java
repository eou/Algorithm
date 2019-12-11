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