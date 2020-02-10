// 352. Data Stream as Disjoint Intervals
// brute-force, list
class SummaryRanges {

    public List<int[]> list;
    /** Initialize your data structure here. */
    public SummaryRanges() {
        list = new ArrayList<>();
    }

    public void addNum(int val) {
        if (list.size() == 0) {
            list.add(new int[] { val, val });
            return;
        }

        int index = 0;
        // can use binary search
        while (index < list.size() && list.get(index)[0] <= val) {
            index++;
        }

        index--;
        if (index < 0) {
            int[] first = list.get(0);
            if (first[0] == val + 1) {
                // expand, case first interval = [4, 7], val = 3, update first interval to [3,
                // 7]
                first[0] = val;
            } else {
                // case first interval = [4, 7], val = 2, add new interval [2, 2] in front of
                // [4, 7]
                list.add(0, new int[] { val, val });
            }
        } else if (index == list.size() - 1) {
            int[] last = list.get(list.size() - 1);
            if (last[1] == val - 1) {
                // expand, case last interval = [3, 10], val = 11, update last interval to [3,
                // 11]
                last[1] = val;
            } else if (last[1] < val - 1) {
                // case last interval = [3, 10], val = 14, add new interva [14, 14] in the end
                list.add(new int[] { val, val });
            } else {
                // case last interval = [3, 10], val = 5 included in the last interval
                // do nothing
            }
        } else {
            int[] curr = list.get(index);
            int[] next = list.get(index + 1);

            // here we have curr.start <= val && next.start > val
            if (curr[1] >= val) {
                // case curr = [3, 5], next = [7, 10], val = 4 included in curr range
                // do nothing
            } else if (curr[1] == val - 1) {
                if (next[0] == curr[1] + 2) {
                    // merge, case curr = [3, 5], next = [7, 10], val = 6 connecting curr and next
                    curr[1] = next[1];
                    list.remove(next);
                } else {
                    // case curr = [3, 5], next = [8, 10], val = 6 new end for curr
                    curr[1] = val;
                }
            } else if (next[0] == val + 1) {
                // case curr = [3, 5], next = [8, 10], val = 7 new start for next
                next[0] = val;
            } else {
                // case curr = [3, 5], next = [9, 10], val = 7 new interval to be added
                list.add(index + 1, new int[] { val, val });
            }
        }
    }

    public int[][] getIntervals() {
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}

// TreeMap, Red-Black Tree implemetation, key-ordered
class SummaryRanges {
    TreeMap<Integer, int[]> tree;

    public SummaryRanges() {
        tree = new TreeMap<>();
    }

    public void addNum(int val) {
        if(tree.containsKey(val)) {
            return;
        }
        
        Integer l = tree.lowerKey(val);
        Integer h = tree.higherKey(val);
        
        if(l != null && h != null && tree.get(l)[1] + 1 == val && h == val + 1) {
            // merge with low and high
            tree.get(l)[1] = tree.get(h)[1];
            tree.remove(h);
        } else if(l != null && tree.get(l)[1] + 1 == val) {
            // merge with low
            tree.get(l)[1] = val;
        } else if(h != null && h == val + 1) {
            // merge with high
            tree.put(val, new int[]{val, tree.get(h)[1]});
            tree.remove(h);
        } else if(l != null && tree.get(l)[1] + 1 > val) {
            // already covered, then do nothing
        }
        else {
            // insert new
            tree.put(val, new int[]{val, val});
        }
    }

    public int[][] getIntervals() {
        List<int[]> intervals = new ArrayList<>();
        for (int[] val : tree.values()) {
            intervals.add(val);
        }
        
        int[][] res = new int[intervals.size()][2];
        for (int i = 0; i < intervals.size(); i++) {
            res[i] = intervals.get(i);
        }
        return res;
    }
}
