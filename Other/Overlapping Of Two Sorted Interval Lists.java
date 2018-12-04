/**
 * Overlapping Of Two Sorted Interval Lists.java
 */
import java.util.*;
import java.io.*;

class Interval {
    int start;
    int end;

    public Interval(int s, int e) {
        start = s;
        end = e;
    }
}

public class Solution {
    public List<Interval> overlappingOfTowIntervalLists(List<Interval> interval1, List<Interval> interval2) {
        List<Interval> results = new ArrayList<>();
        if(interval1 == null || interval1.size() == 0 || interval2 == null || interval2.size() == 0) {
            return results;
        }
        
        int i = 0;
        int j = 0;
        while(i < interval1.size() && j < interval2.size()) {
            Interval a = interval1.get(i);
            Interval b = interval2.get(j);
            if(isOverlapping(a, b)) {
                if(a.start <= b.start) {
                    // [a.start, a.end]
                    //              [b.start, b.end]
                    if(a.end <= b.end) {
                        results.add(new Interval(b.start, a.end));
                        i++;
                    } else {
                        results.add(new Interval(b.start, b.end));
                        j++;
                    }
                } else {
                    //              [a.start, a.end]
                    // [b.start, b.end]
                    if (a.end <= b.end) {
                        results.add(new Interval(a.start, a.end));
                        i++;
                    } else {
                        results.add(new Interval(a.start, b.end));
                        j++;
                    }
                }
            } else {
                if(a.end < b.start) {
                    // [a.start, a.end]
                    //                      [b.start, b.end]
                    i++;
                } else {
                    //                      [a.start, a.end]
                    // [b.start, b.end]
                    j++;
                }
            }
        }

        return results;
    }

    private boolean isOverlapping(Interval a, Interval b) {
        if((a.start <= b.start && a.end >= b.start) || (b.start <= a.start && b.end >= a.start)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        List<Interval> a = new ArrayList<>();
        List<Interval> b = new ArrayList<>();
        a.add(new Interval(1, 2));
        a.add(new Interval(4, 5));
        a.add(new Interval(6, 8));

        b.add(new Interval(2, 3));
        b.add(new Interval(4, 7));

        List<Interval> c = s.overlappingOfTowIntervalLists(a, b);
        for(Interval i : c) {
            System.out.println(i.start + ", " + i.end);
        }
    }
}