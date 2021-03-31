// 1146. Snapshot Array
// brute force, MLE
class SnapshotArray {
    private Map<Integer, List<Integer>> map;
    private int snap_id;
    public SnapshotArray(int length) {
        map = new HashMap<>();
        snap_id = 0;
        
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(0);
        }
        map.put(snap_id, list);
    }
    
    public void set(int index, int val) {
        List<Integer> list = map.get(snap_id);
        list.set(index, val);
    }
    
    public int snap() {
        List<Integer> list = map.get(snap_id);
        List<Integer> cur = new ArrayList<>(list);
        snap_id++;
        map.put(snap_id, cur);
        return snap_id - 1;
    }
    
    public int get(int index, int snap_id) {
        List<Integer> list = map.get(snap_id);
        return list.get(index);
    }
}

// TreeMap
class SnapshotArray {
    List<TreeMap<Integer, Integer>> list = new ArrayList<>();
    int snap_id = 0;
    public SnapshotArray(int length) {
        for (int i = 0; i < length; i++)
            list.add(new TreeMap<>());
    }
    
    public void set(int index, int val) {
        TreeMap<Integer, Integer> map = list.get(index);
        map.put(snap_id, val);
    }
    
    public int snap() {
        return snap_id++;
    }
    
    public int get(int index, int snap_id) {
        TreeMap<Integer, Integer> map = list.get(index);
        Integer key = map.floorKey(snap_id); // not larger than this snap_id
        return key == null ? 0 : map.get(key);
    }
}

// Binary Search
class SnapshotArray {
    class Pair {
        int id;
        int val;
        Pair(int id, int val) {
            this.id = id;
            this.val = val;
        }
    }

    // array => multiple pairs => (snap_id, val)
    List<List<Pair>> arr;
    int id;

    public SnapshotArray(int length) {
        arr = new ArrayList<>();
        id = 0;

        for (int i = 0; i < length; i++) {
            List<Pair> list = new ArrayList<>();
            list.add(new Pair(0, 0));
            arr.add(list);
        }
    }
    
    public void set(int index, int val) {
        // if current snap array exists
        boolean exists = arr.get(index).get(arr.get(index).size() - 1).id == id;
        if (exists) {
            arr.get(index).get(arr.get(index).size() - 1).val = val;
        } else {
            List<Pair> list = arr.get(index);
            list.add(new Pair(id, val));
            arr.set(index, list);
        } 
    }
    
    public int snap() {
        return id++;
    }
    
    public int get(int index, int snap_id) {
        int idx = binarySearch(snap_id, arr.get(index));
        
        return idx == -1 ? 0 : arr.get(index).get(idx).val;
    }
    
    private int binarySearch(int snap_id, List<Pair> list) {
        if (list.size() == 0) {
            return -1;
        }
        
        int left = 0, right = list.size() - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).id == snap_id) {
                return mid;
            } else if (list.get(mid).id > snap_id) {
                 right = mid;
            } else {
                left = mid;
            }
        }
        
        int left_id = list.get(left).id;
        int right_id = list.get(right).id;
        if (left_id == snap_id) {
            return left;
        }
        
        if (right_id == snap_id) {
            return right;
        }
        
        if (left_id > snap_id) {
            return -1;
        }
        
        if (right_id < snap_id) {
            return right;
        }
        
        return left;
    }
}