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
    // line sweep 扫描线思想（类似图形学中的Scanline rendering）
    // 将所有时间区间在x轴上画出来，并用一条垂直于x轴的线作为扫描线从左至右扫描，在不同位置有不同数量的区间交点，其最大值即为所求
    // 1. 对所有点进行标记，区分起始点和终止点 
    // 2. 对所有点进行排序 
    // 3. 依次遍历每个点，遇到起始点+1，遇到终止点-1，并更新记录最大值
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
        int i = 0, j = 0;
        int currentMeetings = 0;
        while (i < intervals.length) {
            // 在第j个会议结束之前，第i个会议无法使用其房间，只能新开一个房间
            if (starts[i] < ends[j]) {
                currentMeetings++;
                i++;
            // 第j个会议在第i个会议开始之前就结束了，所以可以使用其房间，少开一个房间
            } else {
                currentMeetings--;
                j++;
            }
            rooms = Math.max(rooms, currentMeetings);
        }

        // currentMeetings 这个变量可以去掉，因为需要过程中的最大值，只需记录所有增加操作：
        // for (int i = 0; i < starts.length; i++) {
        //     if (starts[i] < ends[j]) {
        //         rooms++;
        //     } else {
        //         j++;
        //     }
        // }

        return rooms;
    }
}

class Solution {
    // TreeMap版本，思路与数组版本基本一致
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        // 如果用HashMap，无内部排序所以不行
        Map<Integer, Integer> map = new TreeMap<>();
        for (Interval i : intervals) {
            // 在Map中就根据起止点，完成相应加减操作
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