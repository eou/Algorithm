/**
 * 839. Merge Two Sorted Interval Lists
 * https://www.lintcode.com/problem/merge-two-sorted-interval-lists/description
 * 题意为：最少去掉几个重叠的区间使得原区间数组保持不重叠
 */
class Solution {
    public List<Interval> mergeTwoInterval(List<Interval> interval1, List<Interval> interval2) {
        List<Interval> results = new ArrayList<>();
        if(interval1 == null || interval2 == null) {
            return results;
        }

        Interval current = null;
        Interval last = null;
        int i = 0;
        int j = 0;
        while(i < interval1.size() && j < interval2.size()) {
            if(interval1.get(i).start < interval2.get(j).start) {
                current = interval1.get(i);
                i++;
            } else {
                current = interval2.get(j);
                j++;
            }

            last = mergeInterval(results, last, current);
        }

        while(i < interval1.size()) {
            current = interval1.get(i);
            i++;
            last = mergeInterval(results, last, current);
        }

        while(j < interval2.size()) {
            current = interval2.get(j);
            j++;
            last = mergeInterval(results, last, current);
        }
        
        if(last != null) {
            results.add(last);
        }
        return results;
    }
    
    private Interval mergeInterval(List<Interval> results, Interval last, Interval current) {
        if(last == null) {
            return current;
        }

        if(last.end < current.start) {
            // no overlapping, add the first interval into results
            results.add(last);
            return current;
        } else {
            // overlapping exists, merge two interval but not add it into results
            last.end = Math.max(last.end, current.end);
            return last;
        }
    }
}