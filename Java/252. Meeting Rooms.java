// 252. Meeting Rooms
class Solution {
    // 暴力两两比较也能AC，时间复杂度O(n^2)
    // 此版本先按起始时间排序，然后相邻两两比较，时间复杂度O(nlogn)
    public boolean canAttendMeetings(Interval[] intervals) {
        if(intervals.length < 2) {
            return true;
        }
        
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        
        for(int i = 1; i < intervals.length; i++) {
            if(intervals[i].start < intervals[i - 1].end) {
                return false;
            }
        }
        
        return true;
    }
}