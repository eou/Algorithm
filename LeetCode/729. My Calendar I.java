// 729. My Calendar I
class MyCalendar {
    List<int[]> calendar;

    public MyCalendar() {
        calendar = new ArrayList();
    }

    public boolean book(int start, int end) {
        for (int[] i: calendar) {
            int left = i[0], right = i[1];
            if (left < end && right > start) {
                return false;
            }
        }

        calendar.add(new int[]{start, end});
        return true;
    }
}

class MyCalendar {
    TreeMap<Integer, Integer> calendar;

    public MyCalendar() {
        calendar = new TreeMap();
    }

    public boolean book(int start, int end) {
        Integer prev = calendar.floorKey(start),
                next = calendar.ceilingKey(start);
        if ((prev == null || calendar.get(prev) <= start) && (next == null || next >= end)) {
            calendar.put(start, end);
            return true;
        }
        return false;
    }
}