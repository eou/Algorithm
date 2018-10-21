// 253. Meeting Rooms II
class Solution {
    // 常规思路版本，先按起始时间排序，然后用优先队列（堆）按照最早结束时间排序
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals.length == 0) {
            return 0;
        }
        
        // 注意Comparator的写法
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        
        PriorityQueue<Integer> pq = new PriorityQueue<>(intervals.length, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        
        pq.offer(intervals[0].end);
        
        for(int i = 1; i < intervals.length; i++) {
            if(pq.peek() <= intervals[i].start) {
                pq.poll();
            }
            pq.offer(intervals[i].end);
        }
        
        return pq.size();
    }
}

class Solution {
    // 数组版本，因为要排序，时间复杂度还是O(nlogn)
    public int minMeetingRooms(Interval[] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0;
        int endsItr = 0;
        for (int i = 0; i < starts.length; i++) {
            if (starts[i] < ends[endsItr]) {
                rooms++;
            } else {
                endsItr++;
            }
        }

        return rooms;
    }
}

class Solution {
    // TreeMap版本，思路与数组版本基本一致
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        Map<Integer, Integer> map = new TreeMap<>();
        for (Interval i : intervals) {
            map.put(i.start, map.getOrDefault(i.start, 0) + 1);
            map.put(i.end, map.getOrDefault(i.end, 0) - 1);
        }

        int maxRoom = 0, curRoom = 0;
        for (Integer v : map.values()) {
            curRoom += v;
            maxRoom = Math.max(maxRoom, curRoom);
        }
        return maxRoom;
    }
}