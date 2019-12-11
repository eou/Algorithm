// 253. Meeting Rooms II
// 此题有很多变形和相似版本：
// 飞机起飞降落：391. Number of Airplanes in the Sky：https://www.lintcode.com/problem/number-of-airplanes-in-the-sky/description
// 客人来了又走：Find the point where maximum intervals overlap：https://www.geeksforgeeks.org/find-the-point-where-maximum-intervals-overlap/
// 多个数字区间中找出第一个覆盖次数最多的数字
class Solution {
    // 常规思路版本，先按起始时间排序，然后用优先队列（堆）按照最早结束时间排序
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals.length == 0) {
            return 0;
        }
        
        // 注意Comparator的写法，升序为 a - b（最小堆），降序为 b - a
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
    class Point {
        // start flag = 1, end flag = 0
        int time;
        int flag;

        Point(int t, int f) {
            time = t;
            flag = f;
        }
    }

    public int minMeetingRooms(Interval[] intervals) {
        List<Point> list = new ArrayList<>();
        for (Interval i : intervals) {
            list.add(new Point(i.start, 1));
            list.add(new Point(i.end, 0));
        }
        Collections.sort(list, new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                if (p1.time == p2.time) {
                    return p2.flag - p1.flag;
                } else {
                    return p1.time - p2.time;
                }
            }
        });

        int rooms = 0;
        int count = 0;
        int time = 0;
        for (Point p : list) {
            if (p.flag == 1) {
                count++;
            } else {
                count--;
            }
            if (count > rooms) {
                rooms = count;
                // 这里还能记录达到最大房间数的时候的最早时间，也就是变形题目的多个数字区间中覆盖次数最多的第一个数字
                time = p.time;
            }
            // rooms = Math.max(rooms, count);
        }
        // System.out.println(time);
        return rooms;
    }
}

class Solution {
    // Comparable 接口不是必要
    public class Timestamp implements Comparable<Timestamp> {
        int time;
        boolean start;
        Timestamp(int time, boolean start) {
            this.time = time;
            this.start = start;
        }
        public int compareTo(Timestamp t) {
            if (this.time == t.time) {
                if (this.start) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return this.time - t.time;
            }
        }
    }
    public int minMeetingRooms(int[][] intervals) {
        List<Timestamp> timeline = new ArrayList<>();
        for (int[] interval : intervals) {
            Timestamp t1 = new Timestamp(interval[0], true);
            Timestamp t2 = new Timestamp(interval[1], false);
            timeline.add(t1);
            timeline.add(t2);
        }
        Collections.sort(timeline, (Timestamp a, Timestamp b) -> a.compareTo(b));
        // Collections.sort(timeline);  // 实现了 Comparable 接口后可以不用自定义比较器，直接 sort
        
        int room = 0, res = 0;
        for (Timestamp t : timeline) {
            if (t.start) {
                room++;
            } else {
                room--;
            }
            res = Math.max(res, room);
        }
        return res;
    }
}

class Solution {
    // 分别排序 start time 和 end time
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
        // int currentMeetings = 0;
        // while (i < intervals.length) {
        //     // 在第j个会议结束之前，第i个会议无法使用其房间，只能新开一个房间
        //     if (starts[i] < ends[j]) {
        //         currentMeetings++;
        //         i++;
        //     // 第j个会议在第i个会议开始之前就结束了，所以可以使用其房间，少开一个房间
        //     } else {
        //         currentMeetings--;
        //         j++;
        //     }
        //     rooms = Math.max(rooms, currentMeetings);
        // }

        // currentMeetings 这个变量可以去掉，因为需要过程中的最大值，只需记录所有增加操作：
        // 每当要开一个新的会议，判断这个时间点及以前有没有会议结束，有的话 start[i] >= end[j] ，就利用之前的房间，不增加房间，会议结束时间的指针指向下一个即将结束的会议；如果没有就增加房间，准备开下一个会议
        for (int i = 0; i < starts.length; i++) {
            // 这里每次循环都是 i++; 保证了 j 最多与 i 处于同一会议内，可能 j 还在很久之前的会议，i 已经要到头了
            if (starts[i] < ends[j]) {
                rooms++;
            } else {
                j++;
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

        // 如果用HashMap，无内部排序所以不行
        // timestamp => +1 / -1
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