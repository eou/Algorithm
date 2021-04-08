// 56. Merge Intervals
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        List<int[]> list = new ArrayList<>();
        int start = intervals[0][0], end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > end) {
                list.add(new int[] { start, end });
                start = intervals[i][0];
                end = intervals[i][1];
            } else {
                end = Math.max(end, intervals[i][1]);
            }
        }

        list.add(new int[] { start, end });

        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i][0] = list.get(i)[0];
            res[i][1] = list.get(i)[1];
        }

        return res;
    }
}

class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> res = new ArrayList<>();
        res.add(new int[] { intervals[0][0], intervals[0][1] });

        for (int i = 1; i < intervals.length; ++i) {
            int start = intervals[i][0], end = intervals[i][1];
            if (res.get(res.size() - 1)[1] < start) {
                res.add(new int[] { start, end });
            } else {
                res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], end);
            }
        }
        return res.toArray(new int[res.size()][]);
    }
}