// 715. Range Module
// Must use a data structure with sorting
// TreeMap
class RangeModule {
    // <left bound, right bound>
    TreeMap<Integer, Integer> intervals = new TreeMap<>(); 

    public void addRange(int left, int right) {
        // `floorKey` return key no larger than search key
        Integer start = intervals.floorKey(left);
        Integer end = intervals.floorKey(right);
        
        // left is already covered in map
        if(start != null && intervals.get(start) >= left){
            left = start;
        }
        
        // right is already covered in map
        if(end != null && intervals.get(end) >= right){
            right = intervals.get(end);
        }
 
        intervals.put(left, right);
        
        // clear duplicate range, left bound should not be cleared since just add [left, right)
        // subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
        intervals.subMap(left, false, right, true).clear();
    }

    public boolean queryRange(int left, int right) {
        Integer start = intervals.floorKey(left);
        return start != null && intervals.get(start) >= right;
    }
 
    public void removeRange(int left, int right) {
        Integer start = intervals.floorKey(left);
        Integer end = intervals.floorKey(right);

        // subtract [end, right) from [end, intervals.get(end))
        if(end != null && intervals.get(end) > right){
            intervals.put(right, intervals.get(end));
        }
        
        // subtract [left, intervals.get(start)) from [start, intervals.get(start))
        if(start != null && intervals.get(start) > left){
            intervals.put(start, left);
        }
        
        // left inclusive, right exclusive
        intervals.subMap(left, true, right, false).clear();
    }
}

// TreeSet
class RangeModule {
    TreeSet<Interval> intervals = new TreeSet();

    public void addRange(int left, int right) {
        Iterator<Interval> it = intervals.tailSet(new Interval(0, left - 1)).iterator();
        while (it.hasNext()) {
            Interval i = it.next();
            if (right < i.left) {
                break;
            }
            left = Math.min(left, i.left);
            right = Math.max(right, i.right);
            it.remove();
        }

        intervals.add(new Interval(left, right));
    }

    public boolean queryRange(int left, int right) {
        Interval i = intervals.higher(new Interval(0, left));
        return i != null && i.left <= left && right <= i.right;
    }

    public void removeRange(int left, int right) {
        Iterator<Interval> it = intervals.tailSet(new Interval(0, left)).iterator();
        List<Interval> todo = new ArrayList();
        while (it.hasNext()) {
            Interval i = it.next();
            if (right < i.left) break;
            if (i.left < left) todo.add(new Interval(i.left, left));
            if (right < i.right) todo.add(new Interval(right, i.right));
            it.remove();
        }

        for (Interval i: todo) {
            intervals.add(i);
        }
    }
}

class Interval implements Comparable<Interval> {
    int left;
    int right;

    public Interval(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int compareTo(Interval i) {
        if (this.right == i.right) return this.left - i.left;
        return this.right - i.right;
    }
}