// 56. Merge Intervals
class Solution {
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> results = new ArrayList<>();
        if(intervals.size() == 0) {
            return results;
        }
        
        // 注意这里是 List 排序，要用 Collections.sort
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        // 保证后面的循环能添加到最后一个区间
        intervals.add(new Interval(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        for(int i = 0; i < intervals.size(); i++) {
            // 注意一下区间包含区间的情况，[[1,4],[2,3]]
            if(intervals.get(i).start <= end) {
                end = Math.max(end, intervals.get(i).end);
            } else {
                Interval tmp = new Interval(start, end);
                results.add(tmp);
                start = intervals.get(i).start;
                end = intervals.get(i).end;
            }
        }
        // 在 intervals 最后添加一个伪区间就不用多余的添加
        // Interval tmp = new Interval(start, end);
        // results.add(tmp);
        
        return results;
    }
}

class Solution {
    // in-place merge
    public List<Interval> merge(List<Interval> intervals) {
        intervals.sort((i1, i2) -> i1.start - i2.start);
        
        int i = 0;
        while (i < intervals.size() - 1) {
            Interval curr = intervals.get(i);
            Interval next = intervals.get(i + 1);
            if (next.start <= curr.end) {
                curr.end = Math.max(curr.end, next.end);
                intervals.remove(i + 1);
            } else {
                i++;
            }
        }
        return intervals;
    }
}